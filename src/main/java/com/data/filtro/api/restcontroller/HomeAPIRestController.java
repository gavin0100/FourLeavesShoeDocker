package com.data.filtro.api.restcontroller;

import com.data.filtro.exception.api.home.CantGetInformationHomePageException;
import com.data.filtro.model.Product;
import com.data.filtro.model.User;
import com.data.filtro.service.ProductService;
import jakarta.persistence.Entity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/v1/home")
public class HomeAPIRestController {
    private final ProductService productService;
    @GetMapping
    public ResponseEntity<?> home() {
        try{
            List<Product> productTopSellingList = productService.getTopSellingProducts();
            List<Product> product6thList = productService.getSixthProducts();
            List<Product> productTop4Discount = productService.getTopDiscountProducts();
            Map<String, Object> response = new HashMap<>();
            response.put("productTopSellingList", productTopSellingList);
            response.put("product6thList", product6thList);
            response.put("productTop4Discount", productTop4Discount);
            User userSession = null;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
                userSession = (User) authentication.getPrincipal();
            }
            response.put("userSession", userSession);
            return ResponseEntity.ok(response);
        } catch (Exception ex){
            throw new CantGetInformationHomePageException();
        }

    }
}
