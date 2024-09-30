package com.data.filtro.api.controller;

import com.data.filtro.model.Category;
import com.data.filtro.model.ErrorResponse;
import com.data.filtro.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryAPIController {

    private final CategoryService categoryService;

    @GetMapping("/find/{id}")
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
