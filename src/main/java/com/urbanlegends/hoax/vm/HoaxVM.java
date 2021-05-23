package com.urbanlegends.hoax.vm;

import com.urbanlegends.file.vm.FileAttachmentVM;
import com.urbanlegends.hoax.Hoax;
import com.urbanlegends.user.vm.UserVM;
import lombok.Data;

@Data
public class HoaxVM {

    private Long id;
    private String content;
    private Long timestamp;
    private UserVM user;
    private FileAttachmentVM fileAttachment;

    public HoaxVM(Hoax hoax){
        this.id = hoax.getId();
        this.content = hoax.getContent();
        this.timestamp = hoax.getTimestamp().getTime();
        this.user = new UserVM(hoax.getUser());
        if(hoax.getFileAttachment() !=null){
            this.fileAttachment = new FileAttachmentVM(hoax.getFileAttachment());
        }
    }
}
