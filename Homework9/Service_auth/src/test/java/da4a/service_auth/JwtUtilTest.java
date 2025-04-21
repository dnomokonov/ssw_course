package da4a.service_auth;

import da4a.service_auth.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtUtilTest {
    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void testGenerateAndValidateToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        assertTrue(jwtUtil.validateToken(token));
        assertEquals(username, jwtUtil.getUsernameFromToken(token));
    }

    @Test
    void testInvalidToken() {
        assertFalse(jwtUtil.validateToken("invalid_token"));
    }
}
