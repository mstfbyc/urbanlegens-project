package com.urbanlegends.hoax;

import com.urbanlegends.file.FileAttachment;
import com.urbanlegends.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Hoax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @ManyToOne
    private User user;

    @OneToOne(mappedBy = "hoax", cascade = CascadeType.REMOVE)
    private FileAttachment fileAttachment;
}
