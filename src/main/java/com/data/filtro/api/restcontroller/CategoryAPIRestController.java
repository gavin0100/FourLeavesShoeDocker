package com.data.filtro.api.restcontroller;

import com.data.filtro.model.Category;
import com.data.filtro.model.ErrorResponse;
import com.data.filtro.service.CartItemService;
import com.data.filtro.service.CartService;
import com.data.filtro.service.CategoryService;
import com.data.filtro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/v1/category")
@RequiredArgsConstructor
public class CategoryAPIRestController {
    private final CategoryService categoryService;

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_STAFF', 'ACCOUNTING_STAFF') and hasAnyAuthority('FULL_ACCESS_CATEGORY', 'VIEW_CATEGORY')")
    public ResponseEntity<?> find(@PathVariable long id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            String message = "No category found!";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }



}