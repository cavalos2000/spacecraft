package worktomeet.travel.spacecraft.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JwtRequestFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_validJwtToken() throws ServletException, IOException {
        String jwt = "valid-jwt-token";
        String username = "user";
        UserDetails userDetails = mock(UserDetails.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtUtil.extractUsername(jwt)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtil.validateToken(jwt, userDetails)).thenReturn(true);

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(userDetailsService).loadUserByUsername(username);
    }

    @Test
    void testDoFilterInternal_expiredJwtTokenWithRefresh() throws ServletException, IOException {
        String jwt = "expired-jwt-token";
        String requestURL = "https://example.com/refresh-token";
        ExpiredJwtException expiredJwtException = mock(ExpiredJwtException.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtUtil.extractUsername(jwt)).thenThrow(expiredJwtException);
        when(request.getHeader("isRefreshToken")).thenReturn("true");
        when(request.getRequestURL()).thenReturn(new StringBuffer(requestURL));
        when(expiredJwtException.getClaims()).thenReturn(null);

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        assert SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken;
    }

    @Test
    void testDoFilterInternal_invalidJwtToken() throws ServletException, IOException {
        String jwt = "invalid-jwt-token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtUtil.extractUsername(jwt)).thenThrow(new RuntimeException("Invalid token"));

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_noAuthorizationHeader() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtRequestFilter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
    }
}
