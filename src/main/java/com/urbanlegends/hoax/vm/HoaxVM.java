package com.urbanlegends.hoax.vm;

import com.urbanlegends.hoax.Hoax;
import com.urbanlegends.user.User;
import com.urbanlegends.user.vm.UserVM;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
public class HoaxVM {

    private Long id;
    private String content;
    private Long timestamp;
    private UserVM user;

    public HoaxVM(Hoax hoax){
        this.setId(hoax.getId());
        this.setContent(hoax.getContent());
        this.setTimestamp(hoax.getTimestamp().getTime());
        this.setUser(new UserVM(hoax.getUser()));
    }
}
