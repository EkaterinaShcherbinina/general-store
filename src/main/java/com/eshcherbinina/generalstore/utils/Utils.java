package com.eshcherbinina.generalstore.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class Utils {
    public static String generatePasswordResetToken(String userName) {
        String token = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + Constants.RESET_PASSWORD_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, Constants.SECRET_TOKEN.getBytes())
                .compact();
        return token;
    }

    public static boolean hasTokenExpired(String token) {
        Claims claims = Jwts.parser().setSigningKey(Constants.SECRET_TOKEN.getBytes())
                .parseClaimsJws(token).getBody();
        Date tokenExpirationDate = claims.getExpiration();
        Date todayDate = new Date();
        return tokenExpirationDate.before(todayDate);
    }
}
