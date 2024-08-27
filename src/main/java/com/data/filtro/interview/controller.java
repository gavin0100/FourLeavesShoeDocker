package com.data.filtro.interview;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    Service service;

    @GetMapping
    public List<Model> getAllNameModel(){
        List<Model> models = service.getAllModelInterView();
        models.forEach(model -> System.out.println(model));
        return models;
    }

    @GetMapping("/getModel/{id}")
    public Model getModelById(@PathVariable int id){
        try{
            Model model = service.getModelById(id);
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
