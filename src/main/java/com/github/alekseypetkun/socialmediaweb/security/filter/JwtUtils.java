package com.github.alekseypetkun.socialmediaweb.security.filter;

import com.github.alekseypetkun.socialmediaweb.security.jwt.JwtAuthentication;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {

        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(claims.getSubject());
        return jwtInfoToken;
    }
}
