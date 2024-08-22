package com.data.filtro.interview;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
@Slf4j
public class controller {
    @Autowired
    service service;

    @GetMapping
    public List<model> getAllNameModel(){
        List<model> models = service.getAllModelInterView();
        models.forEach(model -> System.out.println(model));
        return models;
    }

    @GetMapping("/getModel/{id}")
    public model getModelById(@PathVariable int id){
        try{
            model model = service.getModelById(id);
            if (model == null){
                log.error("can't find model with id {}", id);
            }
            return model;
        } catch (Exception exception){
            log.error("can't find model with id {}", id);
        }

        return null;

    }
}
