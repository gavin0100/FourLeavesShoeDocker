package com.data.filtro.api.restcontroller;

import com.data.filtro.exception.api.user.*;
import com.data.filtro.exception.controller.AuthenticationAccountException;
import com.data.filtro.exception.controller.NotFoundException;
import com.data.filtro.exception.controller.PasswordDoNotMatchException;
import com.data.filtro.model.AuthenticateResponse;
import com.data.filtro.model.Order;
import com.data.filtro.model.User;
import com.data.filtro.model.payment.OrderStatus;
import com.data.filtro.service.AuthenticationService;
import com.data.filtro.service.OrderService;
import com.data.filtro.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/rest/api/v1/user")
@RequiredArgsConstructor
public class UserAPIRestController {
    private final UserService userService;


    private final OrderService orderService;

    private final AuthenticationService authenticationService;
    public List<OrderStatus> returnListOrderStatus(){
        List<OrderStatus> danhSachOrderStatus = new ArrayList<>();
        danhSachOrderStatus.add(OrderStatus.PENDING);
        danhSachOrderStatus.add(OrderStatus.PAID_MOMO);
        danhSachOrderStatus.add(OrderStatus.PAID_VNPAY);
        danhSachOrderStatus.add(OrderStatus.CONFIRMED);
        danhSachOrderStatus.add(OrderStatus.SHIPPING);
        danhSachOrderStatus.add(OrderStatus.DELIVERED);
        danhSachOrderStatus.add(OrderStatus.CANCELED);
        danhSachOrderStatus.add(OrderStatus.FAILED);
        return danhSachOrderStatus;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }

    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> showProfile() {
        User temp = getCurrentUser();
        Map<String, Object> response = new HashMap<>();

        if (temp == null) {
            throw new UserNotFoundOrAuthorizeException();
        }
        try {
            User user = userService.getByUserId(temp.getId());
            response.put("user", user);
            return ResponseEntity.ok(response);
        } catch (Exception ex){
            throw new CantGetProfileException(temp.getId());
        }
    }



    @PostMapping("/profile/{id}")
    public ResponseEntity<String> processProfile(@PathVariable("id") long id, @ModelAttribute("user") @Valid  User updatedUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ParamNotValidException();
        }
        try {
            userService.updateUser(updatedUser);
            String message = "Cập nhật thông tin thành công!";
            return ResponseEntity.ok(message);
        } catch (Exception ex) {
            throw new CantUpdateProfileException(id);
        }
    }

    @GetMapping("/billing")
    public ResponseEntity<?> showBilling() {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            user = (User) authentication.getPrincipal();
        }

        if (user == null) {
            throw new UserNotFoundOrAuthorizeException();
        }

        try {
            List<Order> orderList;
            try {
                orderList = orderService.getOrderByUserId(user.getId());
                Collections.reverse(orderList);
            } catch (Exception e) {
                orderList = new ArrayList<>();
            }

            List<OrderStatus> orderStatusList = returnListOrderStatus();

            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("orderList", orderList);
            response.put("orderStatusList", orderStatusList);

            return ResponseEntity.ok(response);
        } catch (Exception ex){
            throw new CantGetBillingException(user.getId());
        }

    }

    @PostMapping("/security")
    public ResponseEntity<?> processSecurity(@RequestParam("currentPassword") String currentPassword,
                                             @RequestParam("newPassword") String newPassword,
                                             @RequestParam("repeatNewPassword") String repeatNewPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new UserNotFoundOrAuthorizeException();
        }
        long userId = 0;
        try {
            User user = (User) authentication.getPrincipal();
            userId = user.getId();
            userService.changePassword(user, currentPassword, newPassword, repeatNewPassword);
            return ResponseEntity.ok("Password changed successfully!");
        } catch (NotFoundException e) {
            throw new UserNotFoundOrAuthorizeException();
        } catch (AuthenticationAccountException ex) {
            throw new PasswordNotValidException(currentPassword);
        } catch (PasswordDoNotMatchException pe) {
            throw new ParamNotMatchException(newPassword, repeatNewPassword);
        } catch (Exception ex) {
            throw new CantResetPasswordException(userId);
        }
    }

    @GetMapping("/billing/reset_login")
    public ResponseEntity<String> resetLogin(@RequestParam Map<String, String> params, HttpServletResponse response) {
        try{
            String accountName = params.get("username");
            AuthenticateResponse authenticateResponse = authenticationService.authenticateWithOnlyAccountName(accountName);
            Cookie cookie = new Cookie("fourleavesshoestoken", authenticateResponse.getAccessToken());
            cookie.setHttpOnly(true);
            cookie.setPath("/"); // This makes the cookie valid for all routes on your domain
            response.addCookie(cookie);
            return ResponseEntity.ok("Login thành công");
        } catch (Exception ex){
            throw new CantReloginAfterCheckoutException(params.get("username"));
        }

    }


}
