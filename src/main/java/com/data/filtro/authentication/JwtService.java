package com.data.filtro.authentication;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
//import java.util.Date;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long expirationTime;
    // 1 giờ = 3600000 mili giây

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Instant extractExpiration(String token){
//        return extractClaim(token, Claims::getExpiration);
        return extractClaim(token, Claims::getExpiration).toInstant();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }


    public Claims extractAllClaims(String token){
      return Jwts
              .parserBuilder()
              .setSigningKey(getSigninKey())
              .build()
              .parseClaimsJws(token)
              .getBody();
    }


    public String buildToken(Map<String, Object> extraInfo, UserDetails userDetails, long expirationTime){
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(expirationTime);
        return Jwts.builder()
                .setClaims(extraInfo)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenExpired(String token){
//        return extractExpiration(token).before(new Date());
        return extractExpiration(token).isBefore(Instant.now());
    }

    public boolean isValidToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private String create(Map<String, Object> extra, UserDetails userDetails){
         return buildToken(extra, userDetails, expirationTime);
    }

    public String generateToken(UserDetails userDetails){
        return create(Map.of("role",
                userDetails.getAuthorities().stream()
                        .map(Object::toString)
                        .toList()), userDetails);
    }


    private Key getSigninKey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String generatePasswordResetToken(String email){
        long expiration = System.currentTimeMillis() + 1000 * 60 * 60;
        Date expirationDate = new Date(expiration);
        return Jwts.builder()
                .setExpiration(expirationDate)
                .setSubject(email)
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isValidPasswordResetToken(String token){
        return !isTokenExpired(token);
    }

    public void parseJWT( UserDetails userDetails) throws Exception {
        System.out.println("=======================================");
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(expirationTime);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .claim("role", userDetails.getAuthorities().stream()
                        .map(Object::toString)
                        .toList())
                .subject("alice")
                .issuer("https://fourleavesshoes.com")
                .issueTime(Date.from(now))
                .expirationTime(Date.from(expiration)) // 1 phút
                .build();
        SignedJWT signedJWT1 = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        JWSSigner signer = new MACSigner(bytes);
        signedJWT1.sign(signer);

        System.out.println("signedJWT1: " + signedJWT1.serialize());


        SignedJWT signedJWT;
        try {
            signedJWT = SignedJWT.parse(signedJWT1.serialize());
            System.out.println("signedJWT.getJWTClaimsSet(): " + signedJWT.getJWTClaimsSet());
            System.out.println("signedJWT.getHeader(): " + signedJWT.getHeader());
            System.out.println("======");
            System.out.println("signedJWT.getPayload(): " + signedJWT.getPayload());
            System.out.println("role: " + signedJWT.getJWTClaimsSet().getClaim("role"));
            System.out.println("sub: " + signedJWT.getJWTClaimsSet().getClaim("sub"));
            String nameaa = String.valueOf(signedJWT.getJWTClaimsSet().getClaim("sub"));
            System.out.println(nameaa);
            Date expDate = (Date) signedJWT.getJWTClaimsSet().getClaim("exp");
            Instant expirationTime = expDate.toInstant();
            System.out.println(expirationTime);
            System.out.println("exp: " + signedJWT.getJWTClaimsSet().getClaim("exp"));
            System.out.println("iat: " + signedJWT.getJWTClaimsSet().getClaim("iat"));
            System.out.println("======");
            System.out.println("signedJWT.getSignature(): " + signedJWT.getSignature());
        } catch (java.text.ParseException e) {
            throw new Exception("Invalid token!");
        }
        JWSVerifier verifier = new MACVerifier(Decoders.BASE64.decode(secretKey));
        boolean isVerified = signedJWT.verify(verifier);
        if (isVerified) {
            System.out.println("JWT signature is valid.");
        } else {
            System.out.println("JWT signature is invalid.");
        }
        System.out.println("=======================================");
    }
}
