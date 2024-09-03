package com.data.filtro.controller.user;

import com.data.filtro.interview.BaseRedisService;
import com.data.filtro.model.Category;
import com.data.filtro.model.Product;
import com.data.filtro.service.CategoryService;
import com.data.filtro.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    ProductService productService;

    @Autowired
    BaseRedisService baseRedisService;

    @GetMapping
    public String home(HttpServletRequest request, Model model) {

        List<Product> productTopSellingList = productService.getTopSellingProducts();
        List<Product> product6thList = productService.getSixthProducts();
        List<Product> productTop4Discount = productService.getTopDiscountProducts();

        model.addAttribute("productTopSellingList", productTopSellingList);
        model.addAttribute("product6thList", product6thList);
        model.addAttribute("productTop4DiscountList", productTop4Discount);
//        System.out.println("Chuan bi chuyen den trang html");
        return "user/boot1/index";
    }


}
