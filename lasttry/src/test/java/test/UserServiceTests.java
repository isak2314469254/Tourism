package test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.trytry.lasttry.pojo.LoginInfo;
import com.trytry.lasttry.pojo.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@DisplayName("测试-用户操作")
public class UserServiceTests {
    @DisplayName("测试-用户服务")
    @Test
    public void logintest() {
        User user = new User(1, "admin", "123456");
        if(user != null){
            LoginInfo loginInfo = new LoginInfo(user.getId(), null);
            System.out.println(loginInfo);
        }

    }

    @Test
    public void testGenJwt() {
        // 创建 payload 数据
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 10);
        claims.put("username", "itheima");

        // 生成 JWT
        String secret = "aXRjYXN0"; // 密钥
        String jwt = JWT.create()
                .withClaim("id", (Integer) claims.get("id"))
                .withClaim("username", (String) claims.get("username"))
                .withExpiresAt(new Date(System.currentTimeMillis() + 12 * 3600 * 1000)) // 12小时过期
                .sign(Algorithm.HMAC256(secret));

        System.out.println(jwt);
    }

    @Test
    public  void generateToken() {
        String username = "ababa";
        System.out.println(
                JWT.create()
                        .withSubject(username)
                        .withIssuedAt(new Date())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                        .sign(Algorithm.HMAC256("tourism"))
        );
    }

    // 解析 Token
    @Test
    public void verifyToken() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhYmFiYSIsImlhdCI6MTc0Mzk5NzA2MywiZXhwIjoxNzQ0MDAwNjYzfQ.aQkI6MnA2ijqNMV6TXXfiWKVIbUxZNLbsaNRbl1Eo3Q";
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256("tourism")).build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println(jwt.getSubject());// 获取用户名
        } catch (Exception e) {
            System.out.println("Fail"); // 解析失败
        }
    }
}
