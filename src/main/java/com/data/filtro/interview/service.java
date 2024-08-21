package com.data.filtro.interview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class service {
    @Autowired
    repository repository;

    public List<model> getAllModelInterView(){
        return repository.findAll();
    }

    public model getModelById(int id){
        return repository.findById(id).orElse(null);
    }

}
