package com.example.toucan_remake.util;

import com.example.toucan_remake.user.EntityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT utilities
 * @author Jakub Iwanicki
 */
@Service
public class UtilJWT {

    private final String KEY =
            "4sm$0dJHEJDX!EIedl4PfPvr6pRa5gj4Gdl)ySmX%T1f$yJdiMipe0x0txa%X(H^8B585NcKB6ZUX7F0l99bV&Yvf$0puL&874Fm";

    private final int EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    /**
     * Extracts username from received JWT.
     * @param jwt token
     * @return username from token
     */
    public String extractEmail(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    /**
     * Extracts claims from JWT.
     * @param jwt token
     * @param claimsResolver allow to specify which claim is required
     * @return required claim
     */
    private <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    /**
     * Decodes JWT. Auxiliary method for {@link UtilJWT#extractClaim(String, Function)}
     * @param jwt token
     * @return decoded claims
     */
    private Claims extractAllClaims(String jwt) {
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwt).getBody();
    }

    /**
     * Generates JWT.
     * @param entityUser the user for whom will be created token
     * @return JWT
     */
    public String generateToken(EntityUser entityUser) throws NullPointerException {
        Map<String, Object> claims = new HashMap<>();
        claims.put("uuid", entityUser.getUuid());
        return createToken(claims, entityUser.getEmail());
    }

    /**
     * Generates JWT. Auxiliary method for {@link UtilJWT#generateToken(EntityUser)}
     * @param claims needed Map with claims
     * @param subject username for which the JWT will be created
     * @return final JWT
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, KEY).compact();
    }

    /**
     * Checks JWT correctness.
     * @param jwt token
     * @param entityUser jwt owner
     * @return true when username from token is equal to username from {@link EntityUser} and token isn't expired
     */
    public Boolean isJWTValid(String jwt, EntityUser entityUser) {
        final String username = extractEmail(jwt);
        return (username.equals(entityUser.getEmail())) && !isJWTExpired(jwt);
    }

    /**
     * Checks JWT expirity.
     * @param jwt token
     * @return true when token isn't expired yet
     */
    private Boolean isJWTExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    /**
     * Extracts expiration date from JWT.
     * @param jwt token
     * @return expiration {@link Date}
     */
    private Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }
}