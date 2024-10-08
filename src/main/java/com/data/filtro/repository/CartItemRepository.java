package com.data.filtro.repository;

import com.data.filtro.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("select ci from CartItem ci where ci.cart.id = :id")
    List<CartItem> findCartItemByCart(@Param("id") long id);

    @Modifying
    @Query("update CartItem c set c.quantity = :quantity1, c.total =:totalPriceItem, c.price = :latestPrice where c.cart.id = :cartId and c.product.id = :productId")
    void updateQuantityByProductId(@Param("cartId") long cartId,
                                   @Param("productId") long productId,
                                   @Param("quantity1") int quantity1,
                                   @Param("totalPriceItem") int totalPriceItem,
                                   @Param("latestPrice") int latestPrice);

    @Query("select ci from CartItem ci where ci.guestCart.id = :guestCartId")
    List<CartItem> findCartItemByGuestCartId(@Param("guestCartId") int guestCartId);

    @Modifying
    @Query("delete from CartItem  ci where ci.cart.id = :cartId and ci.product.id = :productId ")
    void removeCartItemByCartIdAndProductId(@Param("cartId") long cartId, @Param("productId") long productId);

    @Modifying
    @Query("delete from CartItem  ci where ci.guestCart.id = :guestCartId and ci.product.id = :productId ")
    void removeGuestCartItemByCartIdAndProductId(@Param("guestCartId") int guestCartId, @Param("productId") int productId);

    @Query("select ci  from CartItem  ci where ci.cart.id = :cartId and ci.product.id = :productId ")
    List<CartItem> getByCartAndProduct(@Param("cartId") long cartId, @Param("productId") long productId);

    @Modifying
    @Query("delete from CartItem  ci where ci.id = :cartItemId and ci.cart.id = :cartId ")
    void deleteCartItemByCartItemIdAndCartId(@Param("cartItemId") long cartItemId, @Param("cartId") long cartId);

}
