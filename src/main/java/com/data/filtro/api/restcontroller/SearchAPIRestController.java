package com.data.filtro.api.restcontroller;


import com.data.filtro.exception.api.product.CantSearchProductByNameException;
import com.data.filtro.model.Category;
import com.data.filtro.model.Material;
import com.data.filtro.model.Product;
import com.data.filtro.model.User;
import com.data.filtro.service.CategoryService;
import com.data.filtro.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/rest/api/v1/search")
@RequiredArgsConstructor
public class SearchAPIRestController {
    private final ProductService productService;

    private final CategoryService categoryService;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> showSearchPage(
            @RequestParam String name,
            @RequestParam(defaultValue = "best_selling") String sortType,
            @RequestParam(name = "page") Optional<Integer> page
    ) {
        User user = getCurrentUser();
        try{
            int currentPage = page.orElse(1);
            int pageSize = 6;
            Pageable pageable;
            Page<Product> productPage;

            switch (sortType) {
                case "product_name_asc":
                    pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("productName").ascending());
                    break;
                case "product_name_desc":
                    pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("productName").descending());
                    break;
                case "price_asc":
                    pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("price").ascending());
                    break;
                case "price_desc":
                    pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("price").descending());
                    break;
                case "newest":
                    pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("createdDate").ascending());
                    break;
                case "oldest":
                    pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("createdDate").descending());
                    break;
                default:
                    pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("sold").descending());
                    break;
            }

            productPage = productService.getProductsByName(name, pageable);
            List<Material> materialList = categoryService.getListMaterials();
            Category category = categoryService.getCategoryById(1);

            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("materialList", materialList);
            response.put("products", productPage.getContent());
            response.put("totalPages", productPage.getTotalPages());
            response.put("currentPage", currentPage);
            response.put("sortType", sortType);
            response.put("name", name);
            response.put("category", category);
            response.put("currentId", 1);
            response.put("currentIdAll", "all");
            response.put("dataLowPrice", 0);
            response.put("dataHighPrice", 10000000);
            response.put("dataMaterialId", 0);
            response.put("dataCurrentPage", 1);

            return ResponseEntity.ok(response);
        } catch (Exception ex){
            throw new CantSearchProductByNameException(name);
        }
    }

}
