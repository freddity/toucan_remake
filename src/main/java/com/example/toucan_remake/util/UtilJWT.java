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
 * Utility class which help to manage JWT in application.
 * This is the only place which use <b>io.jsonwebtoken.*</b> directly.
 */
@Service
public class UtilJWT {

    /**
     * Secret key for decode jwt.
     */
    private final String SECRET_KEY = "4sm$0dJHEJDX!EIedl4PfPvr6pRa5gj4Gdl)ySmX%T1f$yJdiMipe0x0txa%X(H^8B585NcKB6ZUX7F0l99bV&Yvf$0puL&874Fm";
    private final int EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    /**
     * This method extracts username from received JWT.
     * @param token received JWT
     * @return @see claim specified in second argument of {@link #extractClaim(String, Function)}
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * This method extracts claims from JWT using passed by {@code claimsResolver} method from {@link Claims}
     * @param token received JWT
     * @param claimsResolver parameter, which take value of returned by passed through {@code claimsResolver} method from {@link Claims} class
     * @param <T> used method from {@link Claims}
     * @return work effect of method passed in {@code claimsResolver}
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * This is the lower method in the class. This method decode JWT and returns decoded claims as {@link String}
     * @param token received JWT
     * @return decoded claims
     */
    private Claims extractAllClaims(String token) {
        System.out.println(token);
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * This method provides token using {@link #createToken(Map, String)} and add claims to {@link Map}.
     * @param entityUser user, for which token will be generated
     * @return JWT in {@link String}
     */
    //todo use UUID instead of email for identify user in database
    public String generateToken(EntityUser entityUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("uuid", entityUser.getUuid());
        return createToken(claims, entityUser.getEmail());
    }

    /**
     * This method generates token using taken claims map nad principal username.
     * @param claims required by {@link io.jsonwebtoken.JwtBuilder#setClaims(Map)}
     * @param subject {@code subject} is username of user, for which is the token created
     * @return JWT created as {@link String}
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /**
     * This method compares username from token and username from passed {@link } instance.
     * @param token received JWT
     * @param entityUser {@link EntityUser} with data about our user
     * @return {@code true} when username from token is equal to username from {@link EntityUser}
     */
    public Boolean validateToken(String token, EntityUser entityUser) {
        final String username = extractUsername(token);
        return (username.equals(entityUser.getEmail())) && !isTokenExpired(token);
    }

    /**
     * This method takes expiration date from token and compares with actually date.
     * @param token received JWT
     * @return {@code true} when token is not expired yet
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * This method extracts expiration date from passed token.
     * @param token received JWT
     * @return expiration {@link Date}
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}