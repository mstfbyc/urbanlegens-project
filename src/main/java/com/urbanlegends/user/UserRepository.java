package com.urbanlegends.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    Page<User> findAll(Pageable pageable);

/*    @Query(value = "Select u from User u")
    Page<UserProjection> getAllUsersProjection(Pageable pageable);*/
    Page<User> findByUsernameNot(String username,Pageable pageable);



}
