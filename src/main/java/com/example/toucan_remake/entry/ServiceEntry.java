package com.example.toucan_remake.entry;

import com.example.toucan_remake.user.EntityUser;
import com.example.toucan_remake.user.RepositoryUser;
import com.example.toucan_remake.util.UtilJWT;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

/**
 * Service layer used in {@link ControllerEntry}
 * @author Jakub Iwanicki
 */
@Service
public class ServiceEntry {

    private final RepositoryUser repositoryUser;
    private final UtilJWT utilJWT;

    public ServiceEntry(RepositoryUser repositoryUser, UtilJWT utilJWT) {
        this.repositoryUser = repositoryUser;
        this.utilJWT = utilJWT;
    }

    /**
     * Checks does user exists, token isn't null and jwt correctness.
     * Method for {@link ControllerEntry#getLandingPage(String)}
     * !!! Here I only check which of two pages will be returned. Password and correctness of all credentials
     * will be checking in filter. !!!
     * @param jwt token
     * @return landing_page when user isn't trusted or dashboard when is trusted
     */
    public String chooseLandingPage(String jwt) {

        try {
            if (Objects.isNull(jwt) || !repositoryUser.existsByEmail(utilJWT.extractEmail(jwt))) {
                return "landing_page";
            }

            if (!utilJWT.isJWTValid(jwt, repositoryUser.findByEmail(utilJWT.extractEmail(jwt)))) {
                return "landing_page";
            } else if (utilJWT.isJWTValid(jwt, repositoryUser.findByEmail(utilJWT.extractEmail(jwt)))) {
                return "dashboard";
            }
        } catch (ExpiredJwtException e) {
            return "landing_page";
        }

        return "landing_page";
    }

    /**
     * Generates JWT when credentials are correct or throws http error statuses when they aren't.
     * Null will be never returned.
     * @param email email
     * @param password password
     * @return JWT
     */
    public String loginUserAndReturnToken(String email, String password) {

        EntityUser user = repositoryUser.findByEmail(email);

        if (Objects.isNull(user)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist.");
        }

        if (user.getPassword().equals(password)) {
            return utilJWT.generateToken(user);
        } else if (!user.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password.");
        }

        return null;
    }

    /**
     * Creates user if all conditions met and returns JWT or throws http error statuses when they aren't.
     * Checks if the email isn't already taken.
     * Null will be never returned.
     * @param email email
     * @param password password
     * @return JWT
     */
    public String registersUserAndReturnToken(String email, String password) {

        if (!repositoryUser.existsByEmail(email)) {
            repositoryUser.save(new EntityUser(email, password));
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already used.");
        }

        EntityUser user = repositoryUser.findByEmail(email);

        if (user.getPassword().equals(password)) {
            return utilJWT.generateToken(user);
        }

        return null;
    }

    /**
     * Provides JWT checking using {@link UtilJWT#isJWTValid(String, EntityUser)}.
     * @param jwt token
     * @return true when correct or false when isn't
     */
    public boolean isTokenCorrect(String jwt) {

        try {
            return utilJWT.isJWTValid(jwt, repositoryUser.findByEmail(utilJWT.extractEmail(jwt)));
        } catch (NullPointerException e) {
            return false;
        }
    }
}

