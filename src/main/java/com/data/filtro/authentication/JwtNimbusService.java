package com.data.filtro.authentication;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtNimbusService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long expirationTime;
    // 1 giờ = 3600000 mili giây

    public String extractUsername(String token){
        try{
            SignedJWT signedJWT = SignedJWT.parse(token);
            return String.valueOf(signedJWT.getJWTClaimsSet().getClaim("sub"));
        } catch (Exception ex){
            try {
                throw new Exception("Invalid token!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    public boolean isTokenExpired(String token){
        try{
            SignedJWT signedJWT = SignedJWT.parse(token);
            Instant expirationTime = Instant.ofEpochSecond((Long) signedJWT.getJWTClaimsSet().getClaim("exp"));
            return expirationTime.isBefore(Instant.now());
        } catch (Exception ex){
            try {
                throw new Exception("Invalid token!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
//
//    public boolean isValidToken(String token, UserDetails userDetails){
//
//    }
//
//    public String generateToken(UserDetails userDetails){
//
//    }


}
