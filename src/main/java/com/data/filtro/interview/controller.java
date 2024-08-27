package com.data.filtro.interview;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
@Slf4j
//@RequiredArgsConstructor
public class controller {
    @Autowired
    Service service;

//    private final BaseRedisService baseRedisService;
    @Autowired
    private BaseRedisService baseRedisService;

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

    @PostMapping
    public void set(){
        baseRedisService.set("hihi3", "haha3");
        System.out.println("hihi3");
    }

    @PostMapping("/hihi4")
    public void set4(){
        baseRedisService.set("hihi4", "haha4");
        baseRedisService.setTimeToLived("hihi4", 30);
    }

    @PostMapping("/hihi5")
    public void set5(){
        baseRedisService.set("hihi5", "haha5");
        baseRedisService.setTimeToLived("hihi5", 30); // after this time, key are deleted
    }

    @PostMapping("/hihi6")
    public void set6(){
        baseRedisService.hashSet("hihi6", "name", "Duc");
    }
    @PostMapping("/hihi7")
    public void set7(){
        baseRedisService.hashSet("hihi7", "name", "Anh");
        baseRedisService.hashSet("hihi7", "name1", "Anh1");
        baseRedisService.hashSet("hihi7", "name2", "Anh2");
        baseRedisService.hashSet("hihi7", "name3", "Anh3");
        baseRedisService.hashSet("hihi7", "name4", "Anh4");
    }

    @GetMapping("/hihi7")
    public void get7(){
        System.out.println(baseRedisService.hashExists("hihi7", "name"));
    }

    @GetMapping("/valuehihi2")
    public void getValue2(){
        System.out.println(baseRedisService.get("hihi2"));
    }

    @GetMapping("/fieldhihi7")
    public void getField7(){
        System.out.println(baseRedisService.getField("hihi7"));
    }
    // kq: {name=Anh}

    @GetMapping("/valuehihi7")
    public void getValue7(){
        System.out.println(baseRedisService.hashGet("hihi7", "name"));
    }
    // kq: Anh

    @GetMapping("/hashGetByFieldPrefixhihi7")
    public void hashGetByFieldPrefix7(){
        System.out.println(baseRedisService.hashGetByFieldPrefix("hihi7", "name"));
    }
    // [Anh2, Anh4, Anh, Anh1, Anh3]

    @GetMapping("/delete")
    public void delete(){
        System.out.println(baseRedisService.getFieldPrefixes("hihi7"));
    }
    // [name2, name3, name1, name, name4]

    @GetMapping("/getFieldPrefixes7")
    public void getFieldPrefixes(){
        System.out.println(baseRedisService.getFieldPrefixes("hihi7"));
    }

    @DeleteMapping("/delete2")
    public void delete2(){
        baseRedisService.delete("hihi2");
    }

    @DeleteMapping("/deleteField7")
    public void deleteField7(){
        baseRedisService.delete("hihi7", "name");
    }

    @DeleteMapping("/deleteFieldList7")
    public void deleteFieldList7(){
        List<String> fields = new ArrayList<>();
        fields.add("name1");
        fields.add("name2");
        baseRedisService.delete("hihi7", fields);
    }

}
