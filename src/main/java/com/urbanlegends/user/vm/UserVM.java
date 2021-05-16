package com.urbanlegends.user.vm;

import com.urbanlegends.user.User;
import lombok.Data;

@Data
public class UserVM {
    private String username;
    private String displayName;
    private String image;

    public UserVM(User user){
        this.username = user.getUsername();
        this.displayName = user.getDisplayName();
        this.image = user.getImage();
    }
}
