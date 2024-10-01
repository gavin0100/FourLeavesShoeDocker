package com.data.filtro.controller.admin;

import com.data.filtro.model.Product;
import com.data.filtro.service.CategoryService;
import com.data.filtro.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class AdminProductController {
    private final ProductService productService;

    private final CategoryService categoryService;

    public AdminProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }


    @GetMapping("/addProduct")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_STAFF', 'ACCOUNTING_STAFF') and hasAnyAuthority('FULL_ACCESS_PRODUCT', 'VIEW_PRODUCT')")
    public String showProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "admin/product/addProduct";
    }

    @PostMapping("/addProduct")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_STAFF', 'ACCOUNTING_STAFF') and hasAnyAuthority('FULL_ACCESS_PRODUCT')")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult,
                             Model model) throws Exception {
        if(bindingResult.hasErrors()){
            return "Một hoặc nhiều trường truyền vào không hợp lệ!";
        }
        productService.addProduct(product);

        model.addAttribute("product", product);
        return "admin/product/list";
    }

}
