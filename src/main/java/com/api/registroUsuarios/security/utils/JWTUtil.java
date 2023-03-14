package com.api.registroUsuarios.security.utils;

import com.api.registroUsuarios.entities.dto.UserRequestDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

//Clase que genera el Token
@Component
public class JWTUtil {
    private static  final String KEY = "t3st";

    public String generateToken(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2))
                .signWith(SignatureAlgorithm.HS256,KEY).compact();
    }

    public String generateToken(UserRequestDTO userRequestDTO){
        return Jwts.builder().setSubject(userRequestDTO.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2))
                .signWith(SignatureAlgorithm.HS256,KEY).compact();
    }
}
