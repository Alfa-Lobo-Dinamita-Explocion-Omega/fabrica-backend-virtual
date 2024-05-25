package com.udea.edu.co.busquedadevuelos.backendvirtual.config.jwt;

import com.udea.edu.co.busquedadevuelos.backendvirtual.config.JwtProperties;
import com.udea.edu.co.busquedadevuelos.backendvirtual.domain.Credentials;
import com.udea.edu.co.busquedadevuelos.backendvirtual.domain.Role;
import com.udea.edu.co.busquedadevuelos.backendvirtual.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;

@Log
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class JwtProvider {

    private final JwtProperties jwtProperties;

    public Credentials generateUserCredentials(User user) {
        Map<String, Object> claims = new HashMap<>();
        List<Map<String, String>> roles = this.getUserRolesFromAuthorities(user);

        claims.put("uuid", user.getId());
        claims.put("name", user.getName());
        claims.put("roles", roles);
        claims.put("created_at", user.getCreatedAt().toString());
        claims.put("identification", user.getIdentification());
        String idToken = this.buildToken(user.getEmail(), this.jwtProperties.getIdToken().getExpiration(), claims);
        String accessToken = this.buildToken(user.getEmail(), this.jwtProperties.getAccessToken().getExpiration(), Collections.emptyMap());

        return Credentials.builder()
                .accessToken(accessToken)
                .idToken(idToken)
                .build();
    }

    private List<Map<String, String>> getUserRolesFromAuthorities(User user) {
        return user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("role", role);
                    map.put("label", Role.valueOf(role).getLabel());
                    return map;
                })
                .toList();
    }

    public Credentials refreshAuthentication(User user) {
        return this.generateUserCredentials(user);
    }

    private String buildToken(String subject, long expiration, Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject(subject)
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractSubject(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenExpired(String token) {
        return this.extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e1) {
            log.log(Level.SEVERE, "Token is invalid");
        } catch (UnsupportedJwtException e2) {
            log.log(Level.SEVERE, "Token not supported");
        } catch (ExpiredJwtException e3) {
            log.log(Level.SEVERE, "Token has expired");
        } catch (IllegalArgumentException e4) {
            log.log(Level.SEVERE, "Token is empty");
        } catch (SignatureException e5) {
            log.log(Level.SEVERE, "Signature is invalid");
        }
        return false;
    }
}
