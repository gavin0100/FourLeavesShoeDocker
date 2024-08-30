package com.data.filtro.interview;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@org.springframework.stereotype.Service
@Slf4j
public class Service {
    @Autowired
    ModelRepository modelRepository;

    public List<MyModel> getAllModelInterView(){
        return modelRepository.findAll();
    }

    public MyModel getModelById(int id){
        return modelRepository.findById(id).orElse(null);
    }

    @Value("${spring.data.minio.bucketName}")
    private String bucketName;

    @Value("${spring.data.minio.url}")
    private String url;

    @Value("${spring.data.minio.url_host_image}")
    private String urlHostImage;

    @Autowired
    private MinioClient minioClient;

    public void updateService(MyModel myModel, MultipartFile avatarFile){
        try {
            if (!avatarFile.isEmpty()){
                updateAvatarToMinIO(avatarFile);
                String avatarLink = urlHostImage + bucketName+ "/" + avatarFile.getOriginalFilename();
                myModel.setAvatar(avatarLink);
            }
            else {
                MyModel oldModel = modelRepository.findById(myModel.getId()).orElse(null);
                myModel.setAvatar(oldModel.getAvatar());
            }
            modelRepository.save(myModel);
        } catch (Exception ex){
            log.error("Can't upload image {} to model has id {}", avatarFile.getOriginalFilename(), myModel.getId());
        }
    }
    public void updateAvatarToMinIO(MultipartFile avatarFile){
        try {
            InputStream inputStream = avatarFile.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(avatarFile.getOriginalFilename())
                    .stream(inputStream, inputStream.available(), -1)
                    .build());
            log.info("Successfully to upload file.");
        } catch (Exception e) {
            log.error("Failed to upload file: " + e.getMessage());
        }
    }

}
