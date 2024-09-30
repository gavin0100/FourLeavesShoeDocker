package com.data.filtro.handler;

import com.data.filtro.exception.api.home.CantGetInformationHomePageException;
import com.data.filtro.exception.api.invoice.CantExportFilePDFInvoiceException;
import com.data.filtro.exception.api.invoice.CantFindInvoiceWithOrderIdException;
import com.data.filtro.exception.api.order.CantFindOrderFromCartException;
import com.data.filtro.exception.api.order.ErrorCreateOrderException;
import com.data.filtro.exception.api.order.ErrorPlaceOrderWithPaymentMethodException;
import com.data.filtro.exception.api.order.MissInformationUserException;
import com.data.filtro.exception.api.product.CantCommentException;
import com.data.filtro.exception.api.product.CantFindProductByProductIdException;
import com.data.filtro.exception.api.product.CantSearchProductByNameException;
import com.data.filtro.exception.api.product.NotValidCommentException;
import com.data.filtro.exception.api.user.*;
import com.data.filtro.exception.controller.EmptyException;
import com.data.filtro.exception.controller.NotFoundException;
import com.data.filtro.exception.controller.ApiResponse;
import com.data.filtro.exception.api.cart.*;
import com.data.filtro.exception.api.category.CantGetProductListPageException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${spring.data.payment.serveo_link}")
    private String serveoLink;

    //============================= Rest controller ====================================
    //============================= cart ====================================
    @ExceptionHandler(CartNotFoundException.class)
    ProblemDetail CartNotFoundException(CartNotFoundException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Cart Not Found");
        String content = URLEncoder.encode("Không thể tìm thấy", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("giỏ hàng", StandardCharsets.UTF_8.toString());
        String url = serveoLink + "/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 404;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(CartGetException.class)
    ProblemDetail CartGetException(CartGetException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Can't get cart");
        String content = URLEncoder.encode("Không thể truy cập", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("giỏ hàng", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 500;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(CartCantAddProductException.class)
    ProblemDetail CartCantAddProductException(CartCantAddProductException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Cart can't add product");
        String content = URLEncoder.encode("Không thể thêm", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("sản phẩm", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 400;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(CartCantRemoveProductException.class)
    ProblemDetail CartCantRemoveProductException(CartCantRemoveProductException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Cart can't remove product");
        String content = URLEncoder.encode("Không thể thêm", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("sản phẩm", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 400;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(CartCantBeUpdateException.class)
    ProblemDetail CartCantBeUpdateException(CartCantBeUpdateException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Cart can't be updated");
        String content = URLEncoder.encode("Không thể cập nhật", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("giỏ hàng", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 400;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(ProductsNotMatchQuantitiesException.class)
    ProblemDetail ProductsNotMatchQuantitiesException(ProductsNotMatchQuantitiesException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("The length product's array not match with quantity's array");
        String content = URLEncoder.encode("Không thể cập nhật", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("giỏ hàng", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 400;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    // ============================= home ====================================
    @ExceptionHandler(CantGetInformationHomePageException.class)
    ProblemDetail CantGetInformationHomePageException(CantGetInformationHomePageException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Can't get information from home page");
        String content = URLEncoder.encode("Không thể truy cập", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("thông tin", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 500;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    // ============================= category - shop ====================================
    @ExceptionHandler(CantGetProductListPageException.class)
    ProblemDetail CantGetProductListPageException(CantGetProductListPageException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Can't access product's list page");
        String content = URLEncoder.encode("Không thể truy cập", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("shop page", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 500;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }


    // ============================= invoice ====================================
    @ExceptionHandler(CantFindInvoiceWithOrderIdException.class)
    ProblemDetail CantFindInvoiceWithOrderId(CantFindInvoiceWithOrderIdException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Can't find invoice with order id");
        String content = URLEncoder.encode("Không thể truy cập", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("hóa đơn", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 404;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(CantExportFilePDFInvoiceException.class)
    ProblemDetail CantExportFilePDFInvoiceException(CantExportFilePDFInvoiceException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Can't export file pdf invoice with order id");
        String content = URLEncoder.encode("Không thể xuất file", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("hóa đơn", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 500;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    // ============================= order ====================================
    @ExceptionHandler(CantFindOrderFromCartException.class)
    ProblemDetail CantFindOrderFromCartException(CantFindOrderFromCartException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Can't find order from cart");
        String content = URLEncoder.encode("Không thể tạo", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("đơn hàng", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 404;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(MissInformationUserException.class)
    ProblemDetail MissInformationUserException(MissInformationUserException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Miss user's information with user id");
        String content = URLEncoder.encode("Thiếu thông tin của", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("khách hàng", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 400;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(ErrorCreateOrderException.class)
    ProblemDetail ErrorCreateOrderException(ErrorCreateOrderException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Can't create order from cart with user id");
        String content = URLEncoder.encode("Không thể tạo", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("đơn hàng", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 500;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(ErrorPlaceOrderWithPaymentMethodException.class)
    ProblemDetail ErrorPlaceOrderWithPaymentMethodException(ErrorPlaceOrderWithPaymentMethodException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Can't place order");
        String content = URLEncoder.encode("Không thể tạo", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("đơn hàng", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 500;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }


    // ============================= product - search ====================================
    @ExceptionHandler(CantSearchProductByNameException.class)
    ProblemDetail CantSearchProductByNameException(CantSearchProductByNameException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Can't search product by name");
        String content = URLEncoder.encode("Không thể tìm kiếm", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("sản phẩm", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 500;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(CantFindProductByProductIdException.class)
    ProblemDetail CantFindProductByProductIdException(CantFindProductByProductIdException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Can't find product by id");
        String content = URLEncoder.encode("Không thể tìm kiếm", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("sản phẩm", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 404;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(CantCommentException.class)
    ProblemDetail CantCommentException(CantCommentException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Can't comment for product");
        String content = URLEncoder.encode("Không thể bình luận", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("sản phẩm", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 400;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(NotValidCommentException.class)
    ProblemDetail NotValidCommentException(NotValidCommentException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Can't comment for product");
        String content = URLEncoder.encode("Bình luận không", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("hợp lệ", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 400;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    // ============================= user ====================================
    @ExceptionHandler(CantGetProfileException.class)
    ProblemDetail CantGetProfileException(CantGetProfileException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Can't get profile");
        String content = URLEncoder.encode("Không thể lấy thông tin", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("khách hàng", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 500;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(UserNotFoundOrAuthorizeException.class)
    ProblemDetail UserNotFoundOrAuthorizeException(UserNotFoundOrAuthorizeException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setTitle("User Not Found or Authorized");
        String content = URLEncoder.encode("Không thể truy cập", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("tài khoản", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 401;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(CantUpdateProfileException.class)
    ProblemDetail CantUpdateProfileException(CantUpdateProfileException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Can't update profile");
        String content = URLEncoder.encode("Không thể cập nhật", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("thông tin", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 500;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(CantGetBillingException.class)
    ProblemDetail CantGetBillingException(CantGetBillingException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Can't get billing");
        String content = URLEncoder.encode("Không thể xem", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("đơn hàng", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 500;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(CantResetPasswordException.class)
    ProblemDetail CantResetPasswordException(CantResetPasswordException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Can't reset password");
        String content = URLEncoder.encode("Không thể thay đổi", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("mật khẩu", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 500;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(PasswordNotValidException.class)
    ProblemDetail PasswordNotValidException(PasswordNotValidException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Can't reset password");
        String content = URLEncoder.encode("Mật khẩu không đúng", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("định dạng", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 400;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(ParamNotValidException.class)
    ProblemDetail ParamNotValidException(ParamNotValidException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Can't reset password");
        String content = URLEncoder.encode("Params không đúng", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("định dạng", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 400;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(ParamNotMatchException.class)
    ProblemDetail ParamNotMatchException(ParamNotMatchException e) throws UnsupportedEncodingException {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("2 password not match");
        String content = URLEncoder.encode("Mật khẩu không khớp", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("security", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 400;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(CantReloginAfterCheckoutException.class)
    ProblemDetail CantReloginAfterCheckoutException(CantReloginAfterCheckoutException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("2 password not match");
        String content = URLEncoder.encode("Không thể đăng nhập", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("tài khoản", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object + "&status=" + 500;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }









    // ================================= controller====================================================

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex) {
        return ex.getMessage();
    }

    @ExceptionHandler({NotFoundException.class, EmptyException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<?> handleNotFoundException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("statusCode", "404");
        error.put("message", ex.getMessage());
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.error(error);
        return apiResponse;
    }

    @ExceptionHandler({AccessDeniedException.class})
    public void handleAccessDeniedException(Exception ex, HttpServletResponse response) throws IOException {
        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (!authority.getAuthority().equals("ROLE_USER")) {
                response.sendRedirect("/admin/dashboard");
            } else {
                response.sendRedirect("/");
            }
            return;
        }
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletResponse response) throws IOException {
        response.sendRedirect("/404");
    }

//    @ExceptionHandler(NoHandlerFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public void handleNotFound(NoHandlerFoundException ex, HttpServletResponse response) throws IOException {
//        response.sendRedirect("/404");
//    }
}
