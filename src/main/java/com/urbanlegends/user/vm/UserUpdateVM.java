package com.urbanlegends.user.vm;

import com.urbanlegends.shared.FileType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserUpdateVM {
    @NotNull(message = "{urbanlegend.constraints.displayName.NotNull.message}")
    @Size(min = 4, max=30)
    private String displayName;

    @FileType(types = {"png","jpg"})
    private String image;
}
