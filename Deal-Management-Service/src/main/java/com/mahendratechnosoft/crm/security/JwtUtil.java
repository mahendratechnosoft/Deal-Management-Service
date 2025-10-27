package com.mahendratechnosoft.crm.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
@Component
public class JwtUtil {
	@Value("${jwt.expiration}")
	public  long JWT_TOKEN_VALIDITY ;

    @Value("${jwt.Secret}")
    private String secretKey;
    
    public static SecretKey generateHS512Key() {
        byte[] keyBytes = new byte[64];
        Key key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
        return (SecretKey) key;
    }

    SecretKey hs512Key = generateHS512Key();
    
    String base64EncodedKey = Base64.getEncoder().encodeToString(hs512Key.getEncoded());
    
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
    	
        return Jwts.parser().setSigningKey(base64EncodedKey).setAllowedClockSkewSeconds(60).parseClaimsJws(token).getBody();
    }


    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }
    
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        Date expirationDate = new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY); // Set expiration time to 30 minutes from now
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512,  base64EncodedKey).compact();
    }


    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    
    
    public Long extractUserId(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            return claims.get("userId", Long.class); // Extract userId from JWT payload
        } catch (Exception e) {
            return null; // Return null if extraction fails
        }
    }

}
