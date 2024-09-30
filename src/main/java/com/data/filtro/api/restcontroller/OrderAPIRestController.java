package com.data.filtro.api.restcontroller;

import com.data.filtro.exception.api.order.*;
import com.data.filtro.exception.api.user.UserNotFoundOrAuthorizeException;
import com.data.filtro.model.Cart;
import com.data.filtro.model.CartItem;
import com.data.filtro.model.Order;
import com.data.filtro.model.User;
import com.data.filtro.model.payment.OrderStatus;
import com.data.filtro.model.payment.PaymentMethod;
import com.data.filtro.model.payment.momo.MomoResponse;
import com.data.filtro.model.payment.vnpay.VNPResponse;
import com.data.filtro.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/v1/order")
public class OrderAPIRestController {
    private final OrderService orderService;

    private final CartItemService cartItemService;

    private final CartService cartService;

    private final PaymentMethodService paymentMethodService;

    private final MomoService momoService;
    private final VNPayService vnpayService;

    private final MailSenderService mailSender;

    @GetMapping
    public ResponseEntity<?> show() {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();
        }

        if (user != null) {
            try{
                Cart cart = cartService.getCurrentCartByUserId(user.getId());
                if (cart != null) {
                    List<CartItem> cartItemList = cart.getCartItemList();
                    if (user.getAddress() != null && user.getCity() != null && user.getZip() != null && user.getPhoneNumber() != null) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("user", user);
                        response.put("address", user.getAddress());
                        response.put("city", user.getCity());
                        response.put("zip", user.getZip());
                        response.put("phone", user.getPhoneNumber());
                        response.put("cartItemList", cartItemList);
                        return ResponseEntity.ok(response);
                    } else {
                        throw new MissInformationUserException(user.getId());
                    }
                } else {
                    throw new CantFindOrderFromCartException(user.getId());
                }
            } catch (Exception ex){
                throw new ErrorCreateOrderException(user.getId());
            }
        } else {
            throw new UserNotFoundOrAuthorizeException();
        }

    }

    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("zip") Integer zip,
            @RequestParam("paymentMethod") String paymentMethod,
            HttpServletRequest request
    ) {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();
        }

        if (user != null) {
            try{
                Cart cart = cartService.getCurrentCartByUserId(user.getId());
                List<CartItem> cartItemList = cart.getCartItemList();
                com.data.filtro.model.payment.PaymentMethod paymentMethod1 = com.data.filtro.model.payment.PaymentMethod.COD;
                paymentMethod1 = PaymentMethod.COD;
                Order order = orderService.placeOrder(user, phone, email, address, city, zip, paymentMethod1, cartItemList);

                List<CartItem> cartItems = cart.getCartItemList();
                for (CartItem cartItem : cartItems) {
                    cartItemService.deleteCartItemFromCartItemIdAndCartId(cartItem.getId(), cartItem.getCart().getId());
                }

                orderService.updateStatusOrder(OrderStatus.PENDING, order);
                sendMail(user, order);
                long orderId = order.getId();

                return ResponseEntity.ok("Order placed successfully. Order ID: " + orderId);
            } catch (Exception ex){
                throw new ErrorPlaceOrderWithPaymentMethodException(user.getId(), String.valueOf(PaymentMethod.COD));
            }
        } else {
            throw new UserNotFoundOrAuthorizeException();
        }
    }

    @PostMapping("/placeOrderMomo")
    public ResponseEntity<?> placeOrderMomo(
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("zip") Integer zip,
            @RequestParam("paymentMethod") String paymentMethod,
            HttpServletRequest request
    ) throws IOException {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();
        }

        if (user != null) {
            try{
                Cart cart = cartService.getCurrentCartByUserId(user.getId());
                List<CartItem> cartItemList = cart.getCartItemList();
                com.data.filtro.model.payment.PaymentMethod paymentMethod1 = com.data.filtro.model.payment.PaymentMethod.MOMO;
                Order order = orderService.placeOrder(user, phone, email, address, city, zip, paymentMethod1, cartItemList);
                long orderId = order.getId();
                MomoResponse momoResponse = placeMomoOrder(orderId);
                Map<String, Object> response = new HashMap<>();
                response.put("urlPayment", momoResponse.getPayUrl());
                return ResponseEntity.ok(response);
            } catch (Exception ex){
                throw new ErrorPlaceOrderWithPaymentMethodException(user.getId(), String.valueOf(PaymentMethod.MOMO));
            }
        } else {
            throw new UserNotFoundOrAuthorizeException();
        }
    }
    @PostMapping("/placeOrderVnpay")
    public ResponseEntity<?> placeOrderVnpay(
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("zip") Integer zip,
            @RequestParam("paymentMethod") String paymentMethod,
            HttpServletRequest request
    ) throws IOException {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();
        }

        if (user != null) {
            try{
                Cart cart = cartService.getCurrentCartByUserId(user.getId());
                List<CartItem> cartItemList = cart.getCartItemList();
                com.data.filtro.model.payment.PaymentMethod paymentMethod1 = com.data.filtro.model.payment.PaymentMethod.VNPAY;
                Order order = orderService.placeOrder(user, phone, email, address, city, zip, paymentMethod1, cartItemList);
                long orderId = order.getId();
                VNPResponse vnpResponse = placeVNPayOrder(orderId, request);
                Map<String, Object> response = new HashMap<>();
                response.put("urlPayment", vnpResponse.getPaymentUrl());
                return ResponseEntity.ok(response);
            } catch (Exception ex){
                throw new ErrorPlaceOrderWithPaymentMethodException(user.getId(), String.valueOf(PaymentMethod.VNPAY));
            }
        } else {
            throw new UserNotFoundOrAuthorizeException();
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestParam long orderId) {
        try {
            orderService.updateCancelOrder(orderId);
            return ResponseEntity.ok("Order cancelled successfully.");
        } catch (Exception e) {
            throw new ErrorCancelOrderException(orderId);
        }
    }

    private void sendMail(User user, Order order){
        String to = user.getEmail();

        // Sender's email ID needs to be mentioned
        String from = "voduc0100@gmail.com";

        // Assuming you are sending email from localhost
        String host = "smtp.gmail.com";

        // Subject
        String subject = "SHOP BÁN GIÀY FOUR LEAVES SHOE - HÓA ĐƠN MUA HÀNG!";

        mailSender.sendHoaDon(to, from, host, subject, order, order.getOrderDetails());
    }

    public MomoResponse placeMomoOrder(long orderId){
        Order order = orderService.getOrderById(orderId);
        return momoService.createMomoOrder(order);
    }

    public VNPResponse placeVNPayOrder(long orderId, HttpServletRequest request){
        Order order = orderService.getOrderById(orderId);
        return vnpayService.createVNPayOrder(order, request);
    }

}
