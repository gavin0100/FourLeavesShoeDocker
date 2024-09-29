package com.data.filtro.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping("/404")
    public String getNotFoundPage(HttpServletResponse response, Model model) {
        model.addAttribute("message1", "Trang cần tìm");
        model.addAttribute("message2", "không tồn tại");
        model.addAttribute("HTTPstatus", "404");
        model.addAttribute("contentStatus", "Not Found");
        model.addAttribute("errorNotFound", "404");
        return "error/accessDenied";
    }

    @GetMapping("/error_problem_detail")
    public String getNotFoundProblemDetail(
            @RequestParam("content") String content,
            @RequestParam("object") String object,
            HttpServletResponse response, Model model) {
        model.addAttribute("message1", content);   // 4 chữ
        model.addAttribute("message2", object);    // 3 chữ
        model.addAttribute("HTTPstatus", "404");
        model.addAttribute("contentStatus", "Vui lòng liên hệ voduc0100@gmail.com");
        return "error/problemDetailPage";
    }
}
