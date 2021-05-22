package com.urbanlegends.hoax;

import com.urbanlegends.hoax.vm.HoaxVM;
import com.urbanlegends.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController("/api/1.0")
public class HoaxController {

    @Autowired
    HoaxService hoaxService;

    @PostMapping("/hoaxes")
    ResponseEntity<GenericResponse> saveHoax(@RequestBody Hoax hoax){
        hoaxService.save(hoax);
        return ResponseEntity.ok(new GenericResponse("Hoax başarı ile kaydedildi."));
    }

    @GetMapping("/hoaxes")
    ResponseEntity<Page<HoaxVM>> getHoaxes(@PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable page){
        return ResponseEntity.ok(hoaxService.getHoaxes(page).map(HoaxVM::new));
    }

    @GetMapping("/users/{username}/hoaxes")
    ResponseEntity<Page<HoaxVM>> getUserHoaxes(@PathVariable String username, @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable page){
        return ResponseEntity.ok(hoaxService.getHoaxesOfUser(username,page).map(HoaxVM::new));
    }

    @GetMapping({"/hoaxes/{id:[0-9]+}","/users/{username}/hoaxes/{id:[0-9]+}"})
    ResponseEntity<?> getHoaxesRelative(@PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable page,
                                        @PathVariable Long id,
                                        @PathVariable(required = false) String username,
                                        @RequestParam(name="count", required = false, defaultValue = "false") boolean count,
                                        @RequestParam(name="direction", defaultValue = "before") String direction)
    {
        if(count){
            Long newHoaxCount = hoaxService.getNewHoaxesCount(id,username);
            Map<String,Long> response = new HashMap<>();
            response.put("count",newHoaxCount);
            return ResponseEntity.ok(response);
        }
        if(direction.equals("after")){
            List<HoaxVM> newHoaxes = hoaxService.getNewHoaxes(id,username,page.getSort())
                    .stream().map(HoaxVM::new).collect(Collectors.toList());
            return ResponseEntity.ok(newHoaxes);
        }
        return ResponseEntity.ok(hoaxService.getOldHoaxes(id,username,page).map(HoaxVM::new));
    }

}
