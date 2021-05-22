package com.urbanlegends.hoax;

import com.urbanlegends.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HoaxRepository extends JpaRepository<Hoax,Long>, JpaSpecificationExecutor<Hoax> {
    Page<Hoax> findByUser(User user, Pageable page);
}
