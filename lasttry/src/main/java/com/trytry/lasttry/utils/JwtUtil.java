package com.trytry.lasttry.utils;

import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET = "tourism";
    private static final long EXPIRATION_TIME = 3600000; // 1小时

    // 生成 Token
    public static String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET));
    }

    // 解析 Token
    public static String verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject(); // 获取用户名
        } catch (Exception e) {
            return null; // 解析失败
        }
    }


}
