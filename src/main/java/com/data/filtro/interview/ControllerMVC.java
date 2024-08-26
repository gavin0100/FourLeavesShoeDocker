package com.data.filtro.interview;

import com.data.filtro.model.CartItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class ControllerMVC {
    @Autowired
    service service;

    @GetMapping("/mvc")
    public String getAllNameModel(HttpSession session, Model model){
        List<model> modelList = service.getAllModelInterView();
        model.addAttribute("models", modelList);
        return "test/template/test_default_page";
    }
}
