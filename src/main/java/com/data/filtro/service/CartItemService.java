package com.data.filtro.service;

import com.data.filtro.model.CartItem;
import com.data.filtro.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    final CartItemRepository cartItemRepository;


    public List<CartItem> getCartItemByCartId(int id) {
        List<CartItem> cartItemList = cartItemRepository.findCartItemByCart(id);
        return cartItemList;
    }

    public List<CartItem> getCartItemByGuestCartId(int guestCartId) {
        List<CartItem> cartItemList = cartItemRepository.findCartItemByGuestCartId(guestCartId);
        return cartItemList;
    }

    @Transactional
    public void removeCartItemByCartIdAndProductId(int cartId, int productId) {
        cartItemRepository.removeCartItemByCartIdAndProductId(cartId, productId);
    }


    public List<CartItem> findAllByCartIdAndProduct(int cartId, int productId) {
        return cartItemRepository.getByCartAndProduct(cartId, productId);
    }

    @Transactional
    public void removeCartItemByGuestCartIdAndProductId(int guestCartId, int productId) {
        cartItemRepository.removeGuestCartItemByCartIdAndProductId(guestCartId, productId);
    }

    @Transactional
    public void updateQuantityByProductId(int cartId, int productId, int quantity, int totalPriceItem, int latestPrice){
        cartItemRepository.updateQuantityByProductId(cartId, productId, quantity, totalPriceItem, latestPrice);
    }

    @Transactional
    public void deleteCartItemFromCartItemIdAndCartId(int cartItemId, int cartId){
        System.out.println("cartItemId va cartID: " + cartItemId + " " + cartId);
        cartItemRepository.deleteCartItemByCartItemIdAndCartId(cartItemId, cartId);
    }

}
