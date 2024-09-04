package com.data.filtro.service;

import com.data.filtro.model.*;
import com.data.filtro.repository.CartItemRepository;
import com.data.filtro.repository.CartRepository;
import com.data.filtro.repository.GuestCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@WebMvcTest(CartService.class)
public class CartServiceTest {
    @MockBean
    CartItemService cartItemService;
    @MockBean
    ProductService productService;

    @MockBean
    UserService userService;

    @MockBean
    CartRepository cartRepository;

    @MockBean
    GuestCartRepository guestCartRepository;

    @MockBean
    GuestCartService guestCartService;

    @MockBean
    CartItemRepository cartItemRepository;

    @Autowired
    CartService cartService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(
                1001,
                "vo duc",
                Date.valueOf("2002-11-02"),
                "male",
                "Lam Dong",
                670000,
                "Da Oai",
                "voduc0100@gmail.com",
                "0869990187",
                1,
                new UserPermission(1, Role.USER, Permission.VIEW, Permission.VIEW, Permission.VIEW, Permission.VIEW, Permission.VIEW, Permission.VIEW, Permission.VIEW, new ArrayList<>()),
                "voduc0100",
                "Duc2112002@",
                Date.valueOf("2024-02-14"),
                "",
                new Cart(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
    @Test
    public void testCreateCart() {
        Cart cart = cartService.createCart(user);
        assertNotNull(cart);
        assertEquals(user, cart.getUser());
        assertEquals(1, cart.getStatus());
        assertNotNull(cart.getCreatedDate());
        assertTrue(cart.getCartItemList().isEmpty());

        verify(cartRepository, times(1)).save(cart);
    }

}
