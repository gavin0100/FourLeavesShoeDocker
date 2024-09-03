package com.data.filtro.config;

import com.data.filtro.authentication.JwtService;
import com.data.filtro.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    private final UserDetailsService userDetailsService;
    private  final JwtService jwtService;
    private int temp = 0;
    private boolean tempbool = false;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.startsWith("/css/") || path.startsWith("/javascript/") || path.startsWith("/image/") || path.startsWith("/login") || path.startsWith("/img/") || path.startsWith("/access-denied") || path.startsWith("/product/img")) {
            // Nếu đúng là tài nguyên tĩnh, cho phép yêu cầu đi qua mà không xử lý thêm
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = "";
        String accountName = "";
        if (request.getCookies() != null){
            for (int i =0; i < request.getCookies().length; i++){
                if (request.getCookies()[i].getName().equals("fourleavesshoestoken")){
                    try{
                        jwt = request.getCookies()[i].getValue();
                        if(jwt.equals("")){
//                        filterChain.doFilter(request, response);
                        }
                        accountName = jwtService.extractUsername(jwt);
                        break;
                    } catch (Exception ex){
                        throw new MyServletException("JWT is empty", null, false, false);
                    }

                }
            }
        }



        if(accountName!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(accountName);
            if(jwtService.isValidToken(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // lưu những thông tin chi tiết vào SecurityContextHolder như thông tin địa chỉ IP, session ID của request gửi yêu cầu có token mà context holder không chứa thông tin
                SecurityContextHolder.getContext().setAuthentication(authToken);
//                System.out.println("SecurityContextHolder chứa thông tin: " + temp +
//                        SecurityContextHolder.getContext().getAuthentication().getCredentials() +
//                        SecurityContextHolder.getContext().getAuthentication().getPrincipal() +
//                        SecurityContextHolder.getContext().getAuthentication().getAuthorities() );
            }
        }
        System.out.println("SecurityContextHolder chứa thông tin: " +
                SecurityContextHolder.getContext().getAuthentication().getCredentials() +
                SecurityContextHolder.getContext().getAuthentication().getPrincipal() +
                SecurityContextHolder.getContext().getAuthentication().getAuthorities() );


        temp = temp + 1;
        filterChain.doFilter(request, response);
    }
}
