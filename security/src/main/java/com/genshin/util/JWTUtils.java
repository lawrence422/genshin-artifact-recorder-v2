package com.genshin.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JWTUtils {
    public static final long EXPIRATION_TIME=3600*1000;
    public static final String key=RandomUtils.createRandomString();

    public static String generateToken(String username, List<String> authority){
        Map<String, Object> claims=new HashMap<>();
        claims.put("username",username);
        claims.put("expiration",new Date(Instant.now().toEpochMilli()+EXPIRATION_TIME));
        claims.put("authority",authority);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512,key)
                .compact();
    }

    public static boolean validateToken(String token){
        Claims claims=getClaimsFromToken(token);
        if (claims.get("expiration")!=null){
            Date date=new Date((Long)claims.get("expiration"));
            return date.after(new Date());
        }
        return false;
    }

    public static Claims getClaimsFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getUsername(String token){
        Claims claims=getClaimsFromToken(token);
        return String.valueOf(claims.get("username"));
    }




}
