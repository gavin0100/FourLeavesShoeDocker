package com.data.filtro.handler;

import com.data.filtro.exception.EmptyException;
import com.data.filtro.exception.NotFoundException;
import com.data.filtro.exception.ApiResponse;
import com.data.filtro.exception.api.CartGetException;
import com.data.filtro.exception.api.CartNotFoundException;
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
import org.springframework.web.servlet.NoHandlerFoundException;
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
    @ExceptionHandler(CartNotFoundException.class)
    ProblemDetail CartNotFoundException(CartNotFoundException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Cart Not Found");
        String content = URLEncoder.encode("Không thể tìm thấy", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("giỏ hàng", StandardCharsets.UTF_8.toString());
        String url = serveoLink + "/error_problem_detail?content=" + content + "&object=" + object;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(CartGetException.class)
    ProblemDetail CartGetException(CartGetException e) throws UnsupportedEncodingException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Cart Not Found");
        String content = URLEncoder.encode("Không thể truy cập", StandardCharsets.UTF_8.toString());
        String object = URLEncoder.encode("giỏ hàng", StandardCharsets.UTF_8.toString());
        String url = "http://localhost:8080/error_problem_detail?content=" + content + "&object=" + object;
        problemDetail.setType(URI.create(url));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }


    // =====================================================================================

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
