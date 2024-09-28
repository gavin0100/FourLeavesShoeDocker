package com.data.filtro.authentication;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.io.Decoders;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

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
            Date expDate = (Date) signedJWT.getJWTClaimsSet().getClaim("exp");
            return expDate.toInstant().isBefore(Instant.now());
        } catch (Exception ex){
            try {
                throw new Exception("Invalid token!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isValidToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String generateToken(UserDetails userDetails){
        String name = userDetails.getUsername();
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(expirationTime);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .claim("role", userDetails.getAuthorities().stream()
                        .map(Object::toString)
                        .toList())
                .subject(name)
                .issuer("https://fourleavesshoes.com")
                .issueTime(Date.from(now))
                .expirationTime(Date.from(expiration)) // 1 phút
                .build();
        SignedJWT signedJWT1 = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        JWSSigner signer = null;
        try {
            signer = new MACSigner(bytes);
            signedJWT1.sign(signer);
            return signedJWT1.serialize();
        } catch (KeyLengthException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }


}
