package com.sekhmet.api.sekhmetapi.security.jwt;

import com.sekhmet.api.sekhmetapi.config.ApplicationProperties;
import com.sekhmet.api.sekhmetapi.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class TokenProvider {

    private final JwtParser jwtParser;

    public TokenProvider(UserService userService, ApplicationProperties applicationProperties) {
        var twilioProperties = applicationProperties.getTwilio();
        var key = Keys.hmacShaKeyFor(twilioProperties.getApiSecret().getBytes(StandardCharsets.UTF_8));
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }


    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        var identity = (String) claims.get("grants", Map.class).get("identity");
        User principal = new User(identity, "", List.of());
        return new UsernamePasswordAuthenticationToken(principal, token, List.of());
    }

    public boolean validateToken(String authToken) {
        try {
            // Parse the token.
            jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnsupportedJwtException e) {
            log.trace("Invalid JWT token.", e);
        } catch (IllegalArgumentException e) {
            log.error("Token validation error {}", e.getMessage());
        }

        return false;
    }
}
