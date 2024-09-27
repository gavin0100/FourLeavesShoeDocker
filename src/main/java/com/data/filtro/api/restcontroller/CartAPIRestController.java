package com.data.filtro.api.restcontroller;

import com.data.filtro.model.Cart;
import com.data.filtro.model.CartItem;
import com.data.filtro.model.ErrorResponse;
import com.data.filtro.model.User;
import com.data.filtro.service.CartItemService;
import com.data.filtro.service.CartService;
import com.data.filtro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/api/v1/cart")
@RequiredArgsConstructor
public class CartAPIRestController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;

//    @GetMapping("/getUserCart/{id}")
//    public ResponseEntity<?> showCart(@PathVariable long id) {
//        User userSession = null;
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
//            userSession = (User) authentication.getPrincipal();
//        }
//        model.addAttribute("userSession", userSession);
//        if (userSession != null) {
//            Cart cart = cartService.getCurrentCartByUserId(userSession.getId());
//            if (cart != null) {
//                List<CartItem> cartItemList = cart.getCartItemList();
//                model.addAttribute("cartItemList", cartItemList);
//                model.addAttribute("cart", cart);
//            }
//        } else {
//            model.addAttribute("cartItemList", new ArrayList<CartItem>());
//            model.addAttribute("message", "Bạn cần đăng nhập để sử dụng giỏ hàng!");
//        }
//
//        return new ResponseEntity<>(cart, HttpStatus.OK);
//    }



}
