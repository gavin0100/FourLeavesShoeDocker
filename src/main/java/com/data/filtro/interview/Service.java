package com.data.filtro.interview;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

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

    public void updateService(MyModel myModel, MultipartFile avatarFile){
        try {
            updateAvatarToMinIO(avatarFile);
            modelRepository.save(myModel);
        } catch (Exception ex){
            log.error("Can't upload image {} to model has id {}", avatarFile.getOriginalFilename(), myModel.getId());
        }
    }
    public void updateAvatarToMinIO(MultipartFile avatarFile){

    }

}
