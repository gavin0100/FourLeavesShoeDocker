package com.data.filtro.interview;

import com.data.filtro.interview.impl.BaseRedisService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/test")
public class ControllerMVC {
    @Autowired
    Service service;

    private BaseRedisService baseRedisService;
    @Value(value = "6379")
    private String redisPort;

    @Value(value = "1111")
    private int customAnnotaion;

    @GetMapping("/mvc")
    public String getAllNameModel(HttpSession session, org.springframework.ui.Model model){
        System.out.println("a real annotation: " +  redisPort);
        System.out.println("a customed annotaion: " + customAnnotaion);
        List<MyModel> modelList = service.getAllModelInterView();
        model.addAttribute("models", modelList);
//        MyModelInterface myModelInterface = new MyModel();
//        System.out.println(myModelInterface.getUserName());
//        System.out.println(myModelInterface.getPassword());
//        MyModelInterface myModelInterface =  service.testInterface();
//        System.out.println(myModelInterface);
//        MyModel myModel2 = (MyModel) myModelInterface;
//        System.out.println(myModel2);
        System.out.println("SecurityContextHolder chứa thông tin: " +
                SecurityContextHolder.getContext().getAuthentication().getCredentials() +
                SecurityContextHolder.getContext().getAuthentication().getPrincipal() +
                SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return "test/template/test_default_page";
    }
    @PostMapping("/mvc/editModel")
    public String editModel(
            @ModelAttribute("MyModel") MyModel model,
            @RequestParam("avatarFile") MultipartFile avatarFile,
            HttpSession session) {

//        if (!avatarFile.isEmpty()) {
//            System.out.println(avatarFile.getOriginalFilename());
//        }
        service.updateService(model, avatarFile);
        return "redirect:/test/mvc";
    }





}
