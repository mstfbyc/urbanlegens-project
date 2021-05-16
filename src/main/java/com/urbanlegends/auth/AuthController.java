package com.urbanlegends.auth;
import com.urbanlegends.shared.CurrentUser;
import com.urbanlegends.user.User;
import com.urbanlegends.user.UserRepository;
import com.urbanlegends.user.vm.UserVM;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AuthController {

    UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/api/1.0/auth")
    public ResponseEntity<?> handleAıthentication(@CurrentUser User user){
        return ResponseEntity.ok(new UserVM(user));
    }

}
