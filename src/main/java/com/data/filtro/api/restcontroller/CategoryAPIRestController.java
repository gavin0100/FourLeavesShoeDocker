package com.data.filtro.api.restcontroller;

import com.data.filtro.exception.api.category.CantGetProductListPageException;
import com.data.filtro.model.*;
import com.data.filtro.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/api/v1/category")
@RequiredArgsConstructor
public class CategoryAPIRestController {
    private final CategoryService categoryService;

    private final ProductService productService;

    public Pageable sortPage(int currentPage, int pageSize, String sortType) {
        Pageable pageable;
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
        return pageable;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> showProductsByCategory(@PathVariable String id,
                                                                      @RequestParam(defaultValue = "0") String lowPrice,
                                                                          @RequestParam(defaultValue = "10000000") String highPrice,
                                                                      @RequestParam(defaultValue = "best_selling") String sortType,
                                                                      @RequestParam(defaultValue = "0") String materialId,
                                                                      @RequestParam(defaultValue = "1") String currentPage) {
        try {
            List<Material> materialList = categoryService.getListMaterials();
            int dataLowPrice = Integer.parseInt(lowPrice);
            int dataHighPrice = Integer.parseInt(highPrice);
            long dataMaterialId = Long.parseLong(materialId);
            int dataCurrentPage = Integer.parseInt(currentPage);

            int pageSize = 6;
            long currentId = 0;
            Pageable pageable;
            Page<Product> productPage;
            Category category = null;
            String currentIdAll = "";

            pageable = sortPage(dataCurrentPage, pageSize, sortType);

            if (id.equals("all")) {
                if (dataHighPrice != 1000 && dataMaterialId != 0) {
                    productPage = productService.getProductByPriceAndMaterial(dataLowPrice, dataHighPrice, dataMaterialId, pageable);
                } else if (dataHighPrice != 1000) {
                    productPage = productService.getProductByPrice(dataLowPrice, dataHighPrice, pageable);
                } else if (dataMaterialId != 0) {
                    productPage = productService.getProductByMaterial(dataMaterialId, pageable);
                } else {
                    productPage = productService.getAll(pageable);
                }
                currentIdAll = "all";
            } else {
                if (dataHighPrice != 1000 && dataMaterialId != 0) {
                    productPage = productService.getProductByCategoryAndPriceAndMaterial(Long.parseLong(id), dataLowPrice, dataHighPrice, dataMaterialId, pageable);
                } else if (dataHighPrice != 1000) {
                    productPage = productService.getProductByCategoryAndPrice(Long.parseLong(id), dataLowPrice, dataHighPrice, pageable);
                } else if (dataMaterialId != 0) {
                    productPage = productService.getProductByCategoryAndMaterial(Long.parseLong(id), dataMaterialId, pageable);
                } else {
                    productPage = productService.getProductByCategory(Long.parseLong(id), pageable);
                }
                category = categoryService.getCategoryById(Long.parseLong(id));
                currentId = Long.parseLong(id);
            }

            List<Product> productListModel = productPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("materialList", materialList);
            response.put("dataMaterialId", dataMaterialId);
            response.put("category", category);
            response.put("totalPages", productPage.getTotalPages());
            response.put("sortType", sortType);
            response.put("currentId", currentId);
            response.put("currentIdAll", currentIdAll);
            response.put("products", productListModel);
            response.put("dataLowPrice", dataLowPrice);
            response.put("dataHighPrice", dataHighPrice);
            response.put("currentPage", dataCurrentPage);

            return ResponseEntity.ok(response);
        } catch (Exception ex){
            throw  new CantGetProductListPageException();
        }
    }
}