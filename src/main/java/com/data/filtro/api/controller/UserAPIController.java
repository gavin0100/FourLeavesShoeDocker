package com.data.filtro.api.controller;

import com.data.filtro.model.Account;
import com.data.filtro.model.DTO.UserJsDTO;
import com.data.filtro.model.ErrorResponse;
import com.data.filtro.model.User;
import com.data.filtro.service.CartService;
import com.data.filtro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserAPIController {

    private final UserService userService;

    private final CartService cartService;



    @GetMapping("/find/{id}")
    public ResponseEntity<?> findUser(@PathVariable("id") long id) {
        try {
            User user1 = userService.getByUserId(id);
            UserJsDTO user = user1.convertToApiJsDTO();
            if (user == null) {
                String message = "No user found!";
                ErrorResponse err = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
