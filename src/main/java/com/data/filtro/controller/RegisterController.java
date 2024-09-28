package com.data.filtro.controller;

import com.data.filtro.Util.Utility;
import com.data.filtro.exception.AccountNameExistException;
import com.data.filtro.exception.EmailExistException;
import com.data.filtro.exception.PasswordDoNotMatchException;
import com.data.filtro.exception.UserNotFoundException;
import com.data.filtro.model.User;
import com.data.filtro.service.MailSenderService;
import com.data.filtro.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

import static com.data.filtro.service.InputService.*;
import static com.data.filtro.service.InputService.isStringLengthLessThan50;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;

    private final MailSenderService mailSenderService;

    public RegisterController(UserService userService, MailSenderService mailSenderService) {
        this.userService = userService;
        this.mailSenderService = mailSenderService;
    }

    private String errorMessage = "";
    private String message="";

    @GetMapping
    public String showRegister(HttpServletRequest request, Model model) {
        if (request.getSession().getAttribute("user") != null){
            return "redirect:/";
        }
        if (!errorMessage.equals("")){
            model.addAttribute("errorMessage", errorMessage);
            errorMessage="";
        }
        if (!message.equals("")){
            model.addAttribute("message", message);
            message="";
        }
        return "user/boot1/register";
    }

//    @PostMapping
//    public String registerUser(@RequestParam("userName") String userName,
//                               @RequestParam("accountName") String accountName,
//                               @RequestParam("email") String email,
//                               @RequestParam("password") String password,
//                               @RequestParam("repeatPassword") String repeatPassword,
//                               Model model) {
//        if(containsUTF8(userName) && containsAllowedCharacters(accountName)
//                && containsAllowedCharacters(email) && isStringLengthLessThan50(userName)
//                && isStringLengthLessThan50(accountName) && isStringLengthLessThan50(password)){
//            if (password.length() >= 8 && password.matches(".*[A-Z].*")
//                    && password.matches(".*\\d.*") && password.matches("^(?=.*[@#$%^&+=]).*$")){
//                try {
//                    userService.registerUser(userName, accountName, email, password, repeatPassword);
//                } catch (AccountNameExistException ex) {
//                    model.addAttribute("errorMessage", ex.getMessage());
//                    return "user/boot1/register";
//                } catch (PasswordDoNotMatchException ex) {
//                    model.addAttribute("errorMessage", ex.getMessage());
//                    return "user/boot1/register";
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//                String message = "Đăng ký thành công. Hãy đăng nhập ngay!";
//                model.addAttribute("message", message);
//                return "user/boot1/register";
//            }
//            else{
//                String message = "Mật khẩu phải có độ dài tối thiểu là 8 ký tự, bao gồm ít nhất một chữ cái viết hoa, ít nhất một chữ số và ít nhất một ký tự đặc biệt.";
//                model.addAttribute("errorMessage", message);
//                return "user/boot1/register";
//            }
//        }
//        else {
//            String message = "Tên người dùng chỉ được chứa chữ cái viết thường; email, tên chỉ được chứa chữ cái viết thường và các ký tự '()', '@', độ dài phải nhiều hơn 8 và ít hơn 50 ký tự.";
//            model.addAttribute("errorMessage", message);
//            return "user/boot1/register";
//        }
//    }

    @PostMapping
    public String registerUser(@RequestParam("userName") String userName,
                               @RequestParam("accountName") String accountName,
                               @RequestParam("email") String email,
                               @RequestParam("phoneNumber") String phoneNumber,
                               Model model) {
        if(containsUTF8(userName) && containsAllowedCharacters(accountName)
                && containsAllowedCharacters(email) && isStringLengthLessThan50(userName) && Utility.checkPhoneNumber(phoneNumber)){
            try {
                String newPassword = Utility.getRandomString();
                userService.registerUser(userName, accountName, email, phoneNumber, newPassword, newPassword);
                String to = email;
                String from = "voduc0100@gmail.com";
                String host = "smtp.gmail.com";
                String subject = "SHOP BÁN GIÀY FOUR LEAVES SHOE - MẬT KHẨU CHO TÀI KHOẢN!";
                mailSenderService.sendEmailGetPassword(to, from, host, subject, newPassword );
            } catch (AccountNameExistException ex) {
                model.addAttribute("errorMessage", ex.getMessage());
                return "user/boot1/register";
            } catch (EmailExistException ex) {
                model.addAttribute("errorMessage", ex.getMessage());
                return "user/boot1/register";
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            String message = "Đăng ký thành công. Đã gửi mật khẩu mới qua Gmail!";
            model.addAttribute("message", message);
            return "user/boot1/register";
        }
        else {
            String message = "Tên người dùng chỉ được chứa chữ cái viết thường; email, tên chỉ được chứa chữ cái viết thường và các ký tự '()', '@'; số điện thoại chỉ chứa số!";
            model.addAttribute("errorMessage", message);
            return "user/boot1/register";
        }
    }

    public String generateRandomString() {
        return UUID.randomUUID().toString();
    }
}
