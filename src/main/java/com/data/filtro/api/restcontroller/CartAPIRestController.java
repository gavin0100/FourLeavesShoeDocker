package com.data.filtro.api.restcontroller;

import com.data.filtro.exception.UserNotFoundException;
import com.data.filtro.exception.api.cart.CartCantAddProductException;
import com.data.filtro.exception.api.cart.CartCantRemoveProductException;
import com.data.filtro.exception.api.cart.CartGetException;
import com.data.filtro.exception.api.cart.CartNotFoundException;
import com.data.filtro.exception.api.user.UserNotFoundOrAuthorizeException;
import com.data.filtro.model.Cart;
import com.data.filtro.model.CartItem;
import com.data.filtro.model.User;
import com.data.filtro.service.CartItemService;
import com.data.filtro.service.CartService;
import com.data.filtro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public ResponseEntity<String> addCart(@RequestParam("productId") long productId, @RequestParam("quantity") int quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            User userSession = (User) authentication.getPrincipal();
            if (userSession != null) {
                try {
                    Cart cart = cartService.getCurrentCartByUserId(userSession.getId());
                    cartService.addProductToCart(cart, productId, quantity);
                    return ResponseEntity.ok("Product added to cart successfully");
                } catch (Exception exception){
                    throw new CartCantAddProductException(userSession.getId(), productId);
                }
            }
        } else {
            throw new UserNotFoundOrAuthorizeException();
        }
        throw new UserNotFoundOrAuthorizeException();
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeCartItem(@PathVariable("productId") long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            User userSession = (User) authentication.getPrincipal();
            if (userSession != null) {
                try{
                    Cart cart = cartService.getCurrentCartByUserId(userSession.getId());
                    cartItemService.removeCartItemByCartIdAndProductId(cart.getId(), productId);
                    return ResponseEntity.ok("Product removed from cart successfully");
                } catch (Exception ex){
                    throw new CartCantRemoveProductException(userSession.getId(), productId);
                }
            } else {
                throw new UserNotFoundOrAuthorizeException();
            }
        }
        throw new UserNotFoundOrAuthorizeException();
    }


}