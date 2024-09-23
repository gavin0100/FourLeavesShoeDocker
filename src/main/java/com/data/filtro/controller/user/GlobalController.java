package com.data.filtro.controller.user;

import com.data.filtro.model.*;
import com.data.filtro.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@ControllerAdvice
@RequestMapping({"/", "/product", "/category", "/search"})
public class GlobalController {

    @Autowired
    ProductService productService;
    @Lazy
    @Autowired
    CategoryService categoryService;

    @Lazy
    @Autowired
    MaterialService flavorService;

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        List<Category> categories = categoryService.getAll();
        return categories;
    }

    @ModelAttribute("products")
    public List<Product> getProducts() {
        List<Product> productList = productService.getAll();
        return productList;
    }

    @ModelAttribute("flavors")
    public List<Material> getFlavors() {
        List<Material> flavors = flavorService.getAll();
        return flavors;
    }

    @ModelAttribute("cartItemList")
    public List<CartItem> cartItemList() {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();
        }
        if (user != null) {
            Cart cart = cartService.getCurrentCartByUserId(user.getId());
            if (cart != null) {
                List<CartItem> cartItemList = cart.getCartItemList();
                return cartItemList;
            }
        }
        return null;
    }

}
