package com.urbanlegends.hoax;

import com.urbanlegends.hoax.vm.HoaxVM;
import com.urbanlegends.user.User;
import com.urbanlegends.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HoaxService {

    @Autowired
    UserService userService;

    @Autowired
    HoaxRepository hoaxRepository;

    public void save(Hoax hoax) {
        hoax.setTimestamp(new Date());
        hoaxRepository.save(hoax);
    }

    public Page<Hoax> getHoaxes(Pageable page) {
        return hoaxRepository.findAll(page);
    }

    public Page<Hoax> getHoaxesOfUser(String username, Pageable page) {
        User user = userService.getUser(username);
        return hoaxRepository.findByUser(user,page);
    }

    public Long getNewHoaxesCount(Long id, String username) {
        Specification<Hoax> specification = idGreaterThan(id);
        if(username != null){
            User user = userService.getUser(username);
            specification = specification.and(userIs(user));
        }
        return hoaxRepository.count(specification);
    }

    public List<Hoax> getNewHoaxes(Long id, String username, Sort sort) {
        Specification<Hoax> specification =idGreaterThan(id);
        if(username != null){
            User user = userService.getUser(username);
            specification = specification.and(userIs(user));
        }
        return hoaxRepository.findAll(specification,sort);
    }

    public Page<Hoax> getOldHoaxes(Long id, String username, Pageable page) {
        Specification<Hoax> specification = idlessThan(id);
        if(username != null){
            User user = userService.getUser(username);
            specification = specification.and(userIs(user));
        }
        return hoaxRepository.findAll(specification,page);
    }

    Specification<Hoax> idlessThan(Long id){
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("id"),id);
    }
    Specification<Hoax> userIs(User user){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"),user);
    }

    Specification<Hoax> idGreaterThan(Long id){
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("id"),id);
    }



}
