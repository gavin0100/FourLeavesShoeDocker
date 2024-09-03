package com.data.filtro.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccessDeniedController {
    @GetMapping("/access-denied")
    public String getAccessDenied(HttpServletResponse response, Model model){
        model.addAttribute("message1", "Yêu cầu của bạn");
        model.addAttribute("message2", "không được thực hiện");
        model.addAttribute("HTTPstatus", "500");
        model.addAttribute("contentStatus", "Internal Server Error");
        System.out.println("truy cap vao acc-denied" );
        return "error/accessDenied";
    }



}
