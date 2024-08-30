package com.data.filtro.controller.admin;

import com.data.filtro.model.*;
import com.data.filtro.service.CategoryService;
import com.data.filtro.service.MaterialService;
import com.data.filtro.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/product")
public class ProductCURDController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    MaterialService materialService;


    public Pageable sortProduct(int currentPage, int pageSize, int sortType) {
        Pageable pageable;
        switch (sortType) {
            case 5, 10, 25, 50 -> pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id"));
            default -> {
                pageSize = 5;
                pageable = PageRequest.of(currentPage - 1, pageSize);
            }
        }
        return pageable;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_STAFF', 'ACCOUNTING_STAFF') and hasAnyAuthority('FULL_ACCESS_PRODUCT', 'VIEW_PRODUCT')")
    public String show(@RequestParam(defaultValue = "5") int sortType, @RequestParam("currentPage") Optional<Integer> page, Model model, HttpSession session) {
        User admin = (User) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin/login";
        }
        List<Product> availableProducts = productService.getAvailableProducts(1);
        int numberAvailableProduct = availableProducts.size();
        List<Product> discountProducts = productService.getDiscountProducts();
        int numberDiscountProduct = discountProducts.size();
        int currentPage = page.orElse(1);
        int pageSize = sortType;
        Page<Product> productPage;
        List<Category> categories = categoryService.getAll();
        List<Material> materials = materialService.getAll();
        Pageable pageable;
        pageable = sortProduct(currentPage, pageSize, sortType);
        productPage = productService.getAll(pageable);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalElements", productPage.getTotalElements());
        model.addAttribute("sortType", sortType);
        model.addAttribute("categories", categories);
        model.addAttribute("materials", materials);
        model.addAttribute("numberAvailableProduct", numberAvailableProduct);
        model.addAttribute("numberDiscountProduct", numberDiscountProduct);
        return "admin/boot1/product";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_STAFF', 'ACCOUNTING_STAFF') and hasAnyAuthority('FULL_ACCESS_PRODUCT')")
    public String create(@ModelAttribute("product") Product product,
                         @RequestParam("avatarFile") MultipartFile avatarFile) throws Exception {
        productService.addProductWithImage(product, avatarFile);
        return "redirect:/admin/product";
    }


    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_STAFF', 'ACCOUNTING_STAFF') and hasAnyAuthority('FULL_ACCESS_PRODUCT')")
    public String update(@ModelAttribute("product") Product product,
                         @RequestParam("avatarFile") MultipartFile avatarFile) throws Exception {
        productService.update(product, avatarFile);
        return "redirect:/admin/product";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        productService.deleteById(id);
        return "redirect:/admin/product";
    }


}
