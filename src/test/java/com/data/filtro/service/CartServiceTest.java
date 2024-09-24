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

//import java.util.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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
                ZonedDateTime.of(2002, 12, 2, 0, 0, 0, 0, ZoneId.systemDefault()).toInstant(),
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
                ZonedDateTime.of(2024, 3, 12, 0, 0, 0, 0, ZoneId.systemDefault()).toInstant(),
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

    @Test
    public void testCreateGuestCart() {
        GuestCart guestCart = new GuestCart();
        guestCart.setCreatedDate(Instant.now());
        guestCart.setCartItemList(new ArrayList<>());

        when(guestCartRepository.save(any(GuestCart.class))).thenReturn(guestCart);

        GuestCart createdCart = cartService.createGuestCart();

        assertNotNull(createdCart);
        assertNotNull(createdCart.getCreatedDate());
        assertNotNull(createdCart.getCartItemList());
        verify(guestCartRepository, times(1)).save(any(GuestCart.class));
    }

    @Test
    public void testGetCartByUserId() {
        int userId = 1;
        Cart cart = new Cart();
        when(cartRepository.findCartByUserId(userId)).thenReturn(cart);

        Cart foundCart = cartService.getCartByUserId(userId);

        assertNotNull(foundCart);
        verify(cartRepository, times(1)).findCartByUserId(userId);
    }

    @Test
    public void testGetCurrentCartByUserId() {
        int userId = 1;
        Cart cart = new Cart();
        when(cartRepository.findCurrentCartByUserId(userId)).thenReturn(cart);

        Cart foundCart = cartService.getCurrentCartByUserId(userId);

        assertNotNull(foundCart);
        verify(cartRepository, times(1)).findCurrentCartByUserId(userId);
    }

    @Test
    public void testAddProductToCart_ProductExistsInCart() {
        int productId = 1;
        int quantity = 2;
        Product product = new Product();
        product.setId(productId);
        product.setPrice(100);

        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setTotal(100);
        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItem);
        cart.setCartItemList(cartItemList);

        when(productService.getProductById(productId)).thenReturn(product);

        cartService.addProductToCart(cart, productId, quantity);

        assertEquals(quantity, cartItem.getQuantity());
        assertEquals(product.getPrice() * quantity, cartItem.getTotal());
        verify(cartItemRepository, times(1)).save(cartItem);
    }

    @Test
    public void testAddProductToCart_ProductDoesNotExistInCart() {
        int productId = 1;
        int quantity = 2;
        Product product = new Product();
        product.setId(productId);
        product.setPrice(100);

        Cart cart = new Cart();
        cart.setCartItemList(new ArrayList<>());

        when(productService.getProductById(productId)).thenReturn(product);

        cartService.addProductToCart(cart, productId, quantity);

        assertEquals(1, cart.getCartItemList().size());
        CartItem newCartItem = cart.getCartItemList().get(0);
        assertSame(product, newCartItem.getProduct());
        assertEquals(quantity, newCartItem.getQuantity());
        assertEquals(product.getPrice() * quantity, newCartItem.getTotal());
        assertNotNull(newCartItem.getPurchasedDate());
        assertEquals(cart, newCartItem.getCart());
        verify(cartItemRepository, times(0)).save(newCartItem);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    public void testAddProductToCart_ProductNotFound() {
        int productId = 1;
        int quantity = 2;

        Cart cart = new Cart();
        cart.setCartItemList(new ArrayList<>());

        when(productService.getProductById(productId)).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            cartService.addProductToCart(cart, productId, quantity);
        });

        assertEquals("Không tìm thấy sản phẩm!", exception.getMessage());
    }
    @Test
    void testAddProductToGuestCart_ProductExists() {
        // Arrange
        int productId = 1;
        int quantity = 2;

        Product product = new Product();
        product.setId(productId);
        product.setPrice(100);

        CartItem existingCartItem = new CartItem();
        existingCartItem.setProduct(product);
        existingCartItem.setQuantity(1);
        existingCartItem.setTotal(100);

        GuestCart guestCart = new GuestCart();
        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(existingCartItem);
        guestCart.setCartItemList(cartItemList);

        when(productService.getProductById(productId)).thenReturn(product);

        // Act
        cartService.addProductToGuestCart(guestCart, productId, quantity);

        // Assert
        assertEquals(quantity, existingCartItem.getQuantity());
        assertEquals(200, existingCartItem.getTotal());
        verify(cartItemRepository).save(existingCartItem);
        verify(guestCartRepository, never()).save(guestCart);
    }

    @Test
    void testAddProductToGuestCart_ProductDoesNotExist() {
        // Arrange
        int productId = 1;
        int quantity = 2;

        Product product = new Product();
        product.setId(productId);
        product.setPrice(100);

        GuestCart guestCart = new GuestCart();
        guestCart.setCartItemList(new ArrayList<>());

        when(productService.getProductById(productId)).thenReturn(product);

        // Act
        cartService.addProductToGuestCart(guestCart, productId, quantity);

        // Assert
        assertEquals(1, guestCart.getCartItemList().size());
        CartItem cartItem = guestCart.getCartItemList().get(0);
        assertEquals(product, cartItem.getProduct());
        assertEquals(quantity, cartItem.getQuantity());
        assertEquals(200, cartItem.getTotal());
        assertNotNull(cartItem.getPurchasedDate());
        verify(guestCartRepository).save(guestCart);
    }

    @Test
    public void testAddProductToGuestCart_ExistingProduct() {
        GuestCart guestCart = new GuestCart();
        List<CartItem> cartItemList = new ArrayList<>();
        CartItem existingCartItem = new CartItem();
        existingCartItem.setProduct(new Product());
        existingCartItem.getProduct().setId(1);
        existingCartItem.setQuantity(1);
        existingCartItem.setTotal(100);
        cartItemList.add(existingCartItem);
        guestCart.setCartItemList(cartItemList);

        Product product = new Product();
        product.setId(1);
        product.setPrice(100);

        when(productService.getProductById(1)).thenReturn(product);

        cartService.addProductToGuestCart(guestCart, 1, 3);

        assertEquals(1, guestCart.getCartItemList().size());
        CartItem cartItem = guestCart.getCartItemList().get(0);
        assertEquals(3, cartItem.getQuantity());
        assertEquals(300, cartItem.getTotal());
        verify(cartItemRepository, times(1)).save(cartItem);
        verify(guestCartRepository, times(0)).save(guestCart);
    }

    @Test
    public void testConvertGuestCartToCart() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setName("Test User");

        GuestCart guestCart = new GuestCart();
        List<CartItem> guestCartItems = new ArrayList<>();
        CartItem guestCartItem = new CartItem();
        guestCartItem.setProduct(new Product());
        guestCartItem.setQuantity(2);
        guestCartItem.setPrice(100);
        guestCartItem.setTotal(200);
        guestCartItem.setPurchasedDate(Instant.now());
        guestCartItems.add(guestCartItem);
        guestCart.setCartItemList(guestCartItems);

        Cart existingCart = null; // Simulate no existing cart

        when(cartRepository.findCartByUserId(user.getId())).thenReturn(existingCart);
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Cart result = cartService.convertGuestCartToCart(guestCart, user);

        // Assert
        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(1, result.getStatus());
        assertEquals(1, result.getCartItemList().size());
        assertEquals(guestCartItem.getProduct(), result.getCartItemList().get(0).getProduct());
        assertEquals(guestCartItem.getQuantity(), result.getCartItemList().get(0).getQuantity());
        assertEquals(guestCartItem.getPrice(), result.getCartItemList().get(0).getPrice());
        assertEquals(guestCartItem.getTotal(), result.getCartItemList().get(0).getTotal());
        assertEquals(guestCartItem.getPurchasedDate(), result.getCartItemList().get(0).getPurchasedDate());

        verify(cartRepository, times(1)).findCartByUserId(user.getId());
        verify(cartRepository, times(2)).save(any(Cart.class));
    }

    @Test
    public void testRemoveAllProductInCar() {
        // Arrange
        Cart cart = new Cart();
        cart.setId(1);
        List<CartItem> cartItemList = new ArrayList<>();
        CartItem cartItem1 = new CartItem();
        cartItem1.setProduct(new Product());
        cartItem1.getProduct().setId(1);
        CartItem cartItem2 = new CartItem();
        cartItem2.setProduct(new Product());
        cartItem2.getProduct().setId(2);
        cartItemList.add(cartItem1);
        cartItemList.add(cartItem2);
        cart.setCartItemList(cartItemList);

        // Act
        cartService.removeAllProductInCar(cart);

        // Assert
        verify(cartItemService, times(1)).removeCartItemByCartIdAndProductId(cart.getId(), cartItem1.getProduct().getId());
        verify(cartItemService, times(1)).removeCartItemByCartIdAndProductId(cart.getId(), cartItem2.getProduct().getId());
    }

    @Test
    public void testTotalOfCartItem() {
        // Arrange
        User user = new User();
        user.setId(1);

        Cart cart = new Cart();
        List<CartItem> cartItemList = new ArrayList<>();
        CartItem cartItem1 = new CartItem();
        cartItem1.setQuantity(2);
        cartItem1.setPrice(100);
        CartItem cartItem2 = new CartItem();
        cartItem2.setQuantity(3);
        cartItem2.setPrice(200);
        cartItemList.add(cartItem1);
        cartItemList.add(cartItem2);
        cart.setCartItemList(cartItemList);

        when(cartRepository.findCurrentCartByUserId(user.getId())).thenReturn(cart);

        // Act
        int total = cartService.totalOfCartItem(user);

        // Assert
        assertEquals(800, total);
    }

    @Test
    public void testTotalOfCartItemTemp() {
        // Arrange
        GuestCart guestCart = new GuestCart();
        List<CartItem> cartItemList = new ArrayList<>();
        CartItem cartItem1 = new CartItem();
        cartItem1.setQuantity(2);
        cartItem1.setPrice(100);
        CartItem cartItem2 = new CartItem();
        cartItem2.setQuantity(3);
        cartItem2.setPrice(200);
        cartItemList.add(cartItem1);
        cartItemList.add(cartItem2);
        guestCart.setCartItemList(cartItemList);

        when(guestCartService.getGuestCartById(1)).thenReturn(guestCart);

        // Act
        int total = cartService.totalOfCartItemTemp(1);

        // Assert
        assertEquals(800, total);
    }

}
