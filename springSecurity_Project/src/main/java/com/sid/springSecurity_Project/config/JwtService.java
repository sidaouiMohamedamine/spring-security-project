package com.sid.springSecurity_Project.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {

    private static  final String SECRET_KEY = "6B5970337336763979244226452948404D6351665468576D5A7134743777217A";
    public String extractUsername(String token) {
        return null;
    }
    /**
     * This method will help us to extract all claims
     * from the payload in the jwt
     * **/
    private Claims extractAllClaims(String token){
        /**
         * The getSignInKey will create the signature part of the jwt
         * and to ensure that the message wasn't changed along the way
         * **/
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
