package com.data.filtro.controller.admin;

import com.data.filtro.model.Material;
import com.data.filtro.model.User;
import com.data.filtro.model.UserPermission;
import com.data.filtro.service.MaterialService;
import com.data.filtro.service.UserPermissionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/user-permission")
public class UserPermissionCRUDController {

    @Autowired
    UserPermissionService userPermissionService;

    private String errorMessage = "";
    private String message="";

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String show( Model model, HttpSession session) {
        if (!errorMessage.equals("")){
            model.addAttribute("errorMessage", errorMessage);
            errorMessage="";
        }
        if (!message.equals("")){
            model.addAttribute("message", message);
            message="";
        }
        List<UserPermission> userPermissions = userPermissionService.getAll();
        model.addAttribute("userPermissions", userPermissions);
        return "admin/boot1/user-permission";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String update(@ModelAttribute UserPermission userPermission, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            errorMessage = "Nhập sai định dạng dữ liệu";
            return "redirect:/user-permission";
        }
        userPermissionService.update(userPermission);
        message="Cập nhật quyền thành công";
        return "redirect:/admin/user-permission";
    }


}
