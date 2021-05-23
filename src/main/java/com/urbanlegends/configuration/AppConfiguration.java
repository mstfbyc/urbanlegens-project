package com.urbanlegends.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "hoaxify")
public class AppConfiguration {
    private String uploadPath;
    private String profileStorage ="profile";
    private String attachmentStorage = "attachments";

    public String getProfileStorage() {
        return uploadPath+"/"+profileStorage;
    }

    public String getAttachmentStorage() {
        return uploadPath+"/"+attachmentStorage;
    }
}
