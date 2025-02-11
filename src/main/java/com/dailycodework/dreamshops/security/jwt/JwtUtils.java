package com.dailycodework.dreamshops.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.dailycodework.dreamshops.security.user.ShopUserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtUtils {

    private String jwtSecret;
    private int expirationTime;

    public String generateTokenForUser(Authentication authentication) {
        ShopUserDetails userPrincipal = (ShopUserDetails) authentication.getPrincipal();

        List<String> roles = userPrincipal.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority).toList();

        //Información que queremos codificar dentro del token del usuario
        //Así, lo que se quiera codificar para el usuario se pasa como un .claim
        //Y se tendría que incluir en el ShopUserDetails
        return Jwts.builder()
            .setSubject(userPrincipal.getEmail())
            .claim("id", userPrincipal.getId())
            .claim("roles", roles)
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + expirationTime))
            .signWith(key(), SignatureAlgorithm.HS256).compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

}
