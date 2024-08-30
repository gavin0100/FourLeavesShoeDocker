package com.data.filtro.controller.admin;

import com.data.filtro.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")

public class DashboardController {

//    @GetMapping
//    public String show(HttpSession session) {
//        User admin = (User) session.getAttribute("admin");
//        if (admin == null) {
//            return "redirect:/admin/login";
//        }
//
//        return "redirect:/admin/dashboard";
//    }


    @GetMapping({"/dashboard", ""})
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_STAFF', 'ACCOUNTING_STAFF')")
    public String showDashboard(){
        return "/admin/boot1/dashboard";
    }


}
