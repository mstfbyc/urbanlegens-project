package com.urbanlegends.file;

import com.urbanlegends.hoax.Hoax;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class FileAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private  String fileType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @OneToOne
    private Hoax hoax;
}
