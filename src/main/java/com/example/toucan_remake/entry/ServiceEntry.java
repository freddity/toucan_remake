package com.example.toucan_remake.entry;

import com.example.toucan_remake.user.EntityUser;
import com.example.toucan_remake.user.RepositoryUser;
import com.example.toucan_remake.util.UtilJWT;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

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
    protected String chooseLandingPage(String jwt) {
        if (Objects.isNull(jwt) || !repositoryUser.existsByEmail(utilJWT.extractEmail(jwt))) {
            return "landing_page";
        }

        if (!utilJWT.isJWTValid(jwt, repositoryUser.findByEmail(utilJWT.extractEmail(jwt)))) {
            return "landing_page";
        } else if (utilJWT.isJWTValid(jwt, repositoryUser.findByEmail(utilJWT.extractEmail(jwt)))) {
            return "dashboard";
        }

        return "landing_page";
    }

    //todo -----------
    protected String loginUserAndReturnToken(String email, String password) {

        EntityUser user = repositoryUser.findByEmail(email);

        if (Objects.isNull(user)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist.");
        }

        if (user.getPassword().equals(password)) {
            return utilJWT.generateToken(user);
        }

        return null;
    }

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
}

