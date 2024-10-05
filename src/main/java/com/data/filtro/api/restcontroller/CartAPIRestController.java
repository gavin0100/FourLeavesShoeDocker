package com.data.filtro.api.restcontroller;

import com.data.filtro.exception.api.cart.*;
import com.data.filtro.exception.api.user.UserNotFoundOrAuthorizeException;
import com.data.filtro.model.Cart;
import com.data.filtro.model.CartItem;
import com.data.filtro.model.Product;
import com.data.filtro.model.User;
import com.data.filtro.service.CartItemService;
import com.data.filtro.service.CartService;
import com.data.filtro.service.ProductService;
import com.data.filtro.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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

    private final ProductService productService;

    private String[] productIdArray;
    private String[] quantityArray;

    @GetMapping()
    public ResponseEntity<?> showCart(HttpServletRequest request) {
        // 0:0:0:0:0:0:0:1
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
                } else {
                    throw new CartNotFoundException(userSession.getId());
                }
            } else {
                throw new UserNotFoundOrAuthorizeException();
            }
        } catch (Exception ex){
            throw ex;
        }
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


    @PutMapping("/update")
    public ResponseEntity<String> updateCartBeforePlaceOrder(@RequestParam("productIds") String productIds,
                                                             @RequestParam("quantities") String quantities) {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();
            if (user != null) {
                try {
                    if (productIds != null && quantities != null) {
                        productIdArray = productIds.split(",");
                        quantityArray = quantities.split(",");
                    }
                    if (productIdArray.length != productIdArray.length){
                        throw new ProductsNotMatchQuantitiesException();
                    }
                    Cart cart = cartService.getCurrentCartByUserId(user.getId());

                    int totalPriceItem = 0;
                    int latestPrice = 0;
                    for (int i = 0; i < productIdArray.length; i++) {
                        String productId = productIdArray[i].trim();
                        String quantity = quantityArray[i].trim();
                        Product tempProduct = productService.getProductById(Long.parseLong(productId));
                        totalPriceItem = (tempProduct.getPrice() - tempProduct.getPrice() * tempProduct.getDiscount() / 100) * Integer.parseInt(quantity);
                        latestPrice = tempProduct.getPrice() - tempProduct.getPrice() * tempProduct.getDiscount() / 100;
                        cartItemService.updateQuantityByProductId(cart.getId(), Long.parseLong(productId), Integer.parseInt(quantity), totalPriceItem, latestPrice);
                    }
                    return ResponseEntity.ok("Cart updated successfully");
                } catch (Exception ex){
                    throw new CartCantBeUpdateException(user.getId());
                }
            } else {
                throw new UserNotFoundOrAuthorizeException();
            }
        }
        throw new UserNotFoundOrAuthorizeException();
    }
}