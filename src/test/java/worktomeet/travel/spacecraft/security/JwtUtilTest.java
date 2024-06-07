package worktomeet.travel.spacecraft.security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtil = new JwtUtil();
    }

    @Test
    public void testGenerateToken() {
        String username = "testUser";
        when(userDetails.getUsername()).thenReturn(username);

        String token = jwtUtil.generateToken(username);

        assertNotNull(token);
        assertTrue(token.startsWith("eyJ"));
    }

    @Test
    public void testExtractUsername() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        String extractedUsername = jwtUtil.extractUsername(token);

        assertEquals(username, extractedUsername);
    }

    @Test
    public void testValidateToken() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);
        when(userDetails.getUsername()).thenReturn(username);

        boolean isValid = jwtUtil.validateToken(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    public void testExtractExpiration() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        Date expiration = jwtUtil.extractExpiration(token);

        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    public void testExtractClaim() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        String claim = jwtUtil.extractClaim(token, Claims::getSubject);

        assertEquals(username, claim);
    }
}

