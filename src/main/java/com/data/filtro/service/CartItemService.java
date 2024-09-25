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


    public List<CartItem> getCartItemByCartId(long id) {
        List<CartItem> cartItemList = cartItemRepository.findCartItemByCart(id);
        return cartItemList;
    }

    public List<CartItem> getCartItemByGuestCartId(int guestCartId) {
        List<CartItem> cartItemList = cartItemRepository.findCartItemByGuestCartId(guestCartId);
        return cartItemList;
    }

    @Transactional
    public void removeCartItemByCartIdAndProductId(long cartId, long productId) {
        cartItemRepository.removeCartItemByCartIdAndProductId(cartId, productId);
    }


    public List<CartItem> findAllByCartIdAndProduct(long cartId, long productId) {
        return cartItemRepository.getByCartAndProduct(cartId, productId);
    }

    @Transactional
    public void removeCartItemByGuestCartIdAndProductId(int guestCartId, int productId) {
        cartItemRepository.removeGuestCartItemByCartIdAndProductId(guestCartId, productId);
    }

    @Transactional
    public void updateQuantityByProductId(long cartId, long productId, int quantity, int totalPriceItem, int latestPrice){
        cartItemRepository.updateQuantityByProductId(cartId, productId, quantity, totalPriceItem, latestPrice);
    }

    @Transactional
    public void deleteCartItemFromCartItemIdAndCartId(long cartItemId, long cartId){
        cartItemRepository.deleteCartItemByCartItemIdAndCartId(cartItemId, cartId);
    }

}
