package com.data.filtro.api.controller;

import com.data.filtro.model.DTO.ProductJsDTO;
import com.data.filtro.model.ErrorResponse;
import com.data.filtro.model.Product;
import com.data.filtro.service.CategoryService;
import com.data.filtro.service.MaterialService;
import com.data.filtro.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import java.util.Date;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@Slf4j
@RequiredArgsConstructor
public class ProductAPIController {


    private final ProductService productService;
    private final CategoryService categoryService;
    private final MaterialService flavorService;

    @GetMapping("find/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        ProductJsDTO product = productService.getProductById(id).convertToApiJsDTO();
        if (product == null) {
            String message = "No product found!";
            ErrorResponse errorResponse = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
