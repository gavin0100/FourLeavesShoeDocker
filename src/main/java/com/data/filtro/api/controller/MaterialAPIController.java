package com.data.filtro.api.controller;

import com.data.filtro.model.DTO.MaterialJsDTO;
import com.data.filtro.model.ErrorResponse;
import com.data.filtro.model.Material;
import com.data.filtro.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/material")
@RequiredArgsConstructor
public class MaterialAPIController {
    private final MaterialService flavorService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> find(@PathVariable long id) {
        MaterialJsDTO flavor = flavorService.getMaterialById(id).convertToApiJsDTO();
        if (flavor == null) {
            String message = "No flavor found!";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(flavor, HttpStatus.OK);
    }

}
