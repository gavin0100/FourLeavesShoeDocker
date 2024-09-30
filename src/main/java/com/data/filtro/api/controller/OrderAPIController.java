package com.data.filtro.api.controller;

import com.data.filtro.model.*;
import com.data.filtro.model.payment.ApiOrderDTO;
import com.data.filtro.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderAPIController {

    private final OrderService orderService;
    @GetMapping("/find/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        ApiOrderDTO order = orderService.getApiOrderById(id);
        if (order == null) {
            String message = "Order not found!";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);

    }
}


