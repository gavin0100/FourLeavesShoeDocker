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
        model.addAttribute("message1", "Thông tin xác thực");
        model.addAttribute("message2", "đã bị thay đổi");
        model.addAttribute("HTTPstatus", "401");
        model.addAttribute("contentStatus", "Unauthorized");
        return "error/accessDenied";
    }



}
