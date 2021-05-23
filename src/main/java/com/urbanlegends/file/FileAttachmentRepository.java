package com.urbanlegends.file;

import com.urbanlegends.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface FileAttachmentRepository extends JpaRepository<FileAttachment,Long> {
    List<FileAttachment> findByDateBeforeAndHoaxIsNull(Date date);
    List<FileAttachment> findByHoaxUser(User user);
}
