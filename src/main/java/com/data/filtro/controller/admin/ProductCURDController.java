package com.data.filtro.controller.admin;

import com.data.filtro.interview.impl.BaseRedisService;
import com.data.filtro.model.*;
import com.data.filtro.service.CategoryService;
import com.data.filtro.service.MaterialService;
import com.data.filtro.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/product")
public class ProductCURDController {

    private final ProductService productService;

    private final CategoryService categoryService;

    private final MaterialService materialService;

    private final String PREFIX_DETAILED_PRODUCT = "detailed_product:";

    private final BaseRedisService baseRedisService;

    private String errorMessage = "";
    private String message="";

    public ProductCURDController(ProductService productService, CategoryService categoryService, MaterialService materialService, BaseRedisService baseRedisService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.materialService = materialService;
        this.baseRedisService = baseRedisService;
    }

    public Pageable sortProduct(int currentPage, int pageSize, int sortType) {
        Pageable pageable;
        switch (sortType) {
            case 5, 10, 25, 50 -> pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id").descending());
            default -> {
                pageSize = 5;
                pageable = PageRequest.of(currentPage - 1, pageSize);
            }
        }
        return pageable;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_STAFF', 'ACCOUNTING_STAFF') and hasAnyAuthority('FULL_ACCESS_PRODUCT', 'VIEW_PRODUCT')")
    public String show(@RequestParam(defaultValue = "5") int sortType, @RequestParam("currentPage") Optional<Integer> page, Model model) {
        if (!errorMessage.equals("")){
            model.addAttribute("errorMessage", errorMessage);
            errorMessage="";
        }
        if (!message.equals("")){
            model.addAttribute("message", message);
            message="";
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
    public String create(@ModelAttribute("product") Product product,BindingResult bindingResult,
                         @RequestParam("avatarFile") MultipartFile avatarFile) throws Exception {
        if (bindingResult.hasErrors()) {
            errorMessage = "Nhập sai định dạng dữ liệu";
            return "redirect:/admin/product";
        }

        productService.addProductWithImage(product, avatarFile);
        message = "Thêm sản phẩm thành công";
        return "redirect:/admin/product";
    }


    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_STAFF', 'ACCOUNTING_STAFF') and hasAnyAuthority('FULL_ACCESS_PRODUCT')")
    public String update(@ModelAttribute("product") Product product, BindingResult bindingResult,
                         @RequestParam("avatarFile") MultipartFile avatarFile) throws Exception {
        if (bindingResult.hasErrors()) {
            errorMessage = "Nhập sai định dạng dữ liệu";
            return "redirect:/admin/product";
        }
        productService.update(product, avatarFile);
        message="Cập nhật thông tin thành công";
        return "redirect:/admin/product";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam long id) {
        productService.deleteById(id);
        message="Cập nhật thông tin thành công";
        return "redirect:/admin/product";
    }

    public boolean isNumeric(String str) {
        return str != null && str.matches("-?\\d+(\\.\\d+)?");
    }
}
