package com.urbanlegends.file;

import com.urbanlegends.configuration.AppConfiguration;
import com.urbanlegends.user.User;
import org.apache.tika.Tika;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@EnableScheduling
public class FileService {

    private static final long TWENTY_FOUR_HOURS = 24*60*60*1000;
    AppConfiguration appConfiguration;
    FileAttachmentRepository fileAttachmentRepository;
    Tika tika;

    public FileService(AppConfiguration appConfiguration,FileAttachmentRepository fileAttachmentRepository) {
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.appConfiguration = appConfiguration;
        tika = new Tika();
    }

    public String writeBase64EncodedStringToFile(String image) throws IOException {
        String fileName = generateRandomName();
        File target = new File(appConfiguration.getProfileStorage()+"/"+fileName);
        OutputStream outputStream = new FileOutputStream(target);
        byte[] base64encoded = Base64.getDecoder().decode(image);
        outputStream.write(base64encoded);
        outputStream.close();
        return fileName;
    }

    private String generateRandomName() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public void deleteProfileImage(String oldImage){
        if(oldImage == null){
            return;
        }
        deleteFile(Paths.get(appConfiguration.getProfileStorage(),oldImage));
    }

    public void deleteAttachmentFile(String attachment) {
        if(attachment == null){
            return;
        }
        deleteFile(Paths.get(appConfiguration.getAttachmentStorage(),attachment));
    }

    private void deleteFile(Path path){
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String detectType(String image) {
        byte[] base64encoded = Base64.getDecoder().decode(image);
         return tika.detect(base64encoded);
    }

    public FileAttachment saveHoaxAttachment(MultipartFile multipartFile) {
        String fileName = generateRandomName();
        File target = new File(appConfiguration.getAttachmentStorage()+"/"+fileName);
        String fileType = null;
        try {
            byte[] arr = multipartFile.getBytes();
            OutputStream outputStream = new FileOutputStream(target);
            outputStream.write(arr);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileAttachment attachment = new FileAttachment();
        attachment.setName(fileName);
        attachment.setDate(new Date());
        attachment.setFileType(fileType);
        return fileAttachmentRepository.save(attachment);
    }

    @Scheduled(fixedRate = TWENTY_FOUR_HOURS)
    public void cleanupStorage(){
        Date twentyFourHoursAgo = new Date(System.currentTimeMillis()-TWENTY_FOUR_HOURS);
        List<FileAttachment> fileToBeDeleted = fileAttachmentRepository.findByDateBeforeAndHoaxIsNull(twentyFourHoursAgo);
        for(FileAttachment file:fileToBeDeleted){
            deleteAttachmentFile(file.getName());
            fileAttachmentRepository.deleteById(file.getId());
        }

    }

    public void deleteAllStoredFilesForUser(User user) {
        deleteProfileImage(user.getImage());
        List<FileAttachment> fileToBeRemoved = fileAttachmentRepository.findByHoaxUser(user);
        for (FileAttachment file:fileToBeRemoved) {
            deleteAttachmentFile(file.getName());
        }
    }
}
