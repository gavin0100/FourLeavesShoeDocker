package com.data.filtro.api.restcontroller;

import com.data.filtro.exception.api.CartGetException;
import com.data.filtro.exception.api.CartNotFoundException;
import com.data.filtro.model.Cart;
import com.data.filtro.model.CartItem;
import com.data.filtro.model.ErrorResponse;
import com.data.filtro.model.User;
import com.data.filtro.service.CartItemService;
import com.data.filtro.service.CartService;
import com.data.filtro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/api/v1/cart")
@RequiredArgsConstructor
public class CartAPIRestController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<?> showCart() {
        User userSession = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            userSession = (User) authentication.getPrincipal();
        }
        try{
            if (userSession != null) {
                Cart cart = cartService.getCurrentCartByUserId(userSession.getId());
                if (cart != null) {
                    List<CartItem> cartItemList = cart.getCartItemList();
                    Map<String, Object> response = new HashMap<>();
                    response.put("userSession", userSession);
                    response.put("cartItemList", cartItemList);
                    response.put("cart", cart);
                    return ResponseEntity.ok(response);
                }
            } else {
                throw new CartNotFoundException(userSession.getId());
            }
        } catch (Exception ex){
            throw new CartGetException(userSession.getId());
        }
        throw new CartGetException(userSession.getId());
    }


}
