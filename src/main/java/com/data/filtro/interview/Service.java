package com.data.filtro.interview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    ModelRepository modelRepository;

    public List<Model> getAllModelInterView(){
        return modelRepository.findAll();
    }

    public Model getModelById(int id){
        return modelRepository.findById(id).orElse(null);
    }


}
