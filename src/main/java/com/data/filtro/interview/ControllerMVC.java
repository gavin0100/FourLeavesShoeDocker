package com.data.filtro.interview;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/test")
public class ControllerMVC {
    @Autowired
    Service service;
    @Value(value = "6379")
    private String redisPort;

    @Value(value = "1111")
    private int customAnnotaion;

    @GetMapping("/mvc")
    public String getAllNameModel(HttpSession session, org.springframework.ui.Model model){
        System.out.println("a real annotation: " +  redisPort);
        System.out.println("a customed annotaion: " + customAnnotaion);
        List<Model> modelList = service.getAllModelInterView();
        model.addAttribute("models", modelList);
        return "test/template/test_default_page";
    }

}
