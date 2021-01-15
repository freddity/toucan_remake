package com.example.toucan_remake.entry;

import com.example.toucan_remake.dto.DtoUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author Jakub Iwanicki
 */
@Controller
public class ControllerEntry {

    private final ServiceEntry serviceEntry;

    public ControllerEntry(ServiceEntry serviceEntry) {
        this.serviceEntry = serviceEntry;
    }

    /**
     * Decides which page will be returned, landing_page or dashboard.
     * @param jwt token passed through request header
     * @return landing_page when user isn't trusted or dashboard when is trusted
     */
    @GetMapping
    public String getLandingPage(@RequestHeader(value = "jwt", required = false) String jwt) {
        return serviceEntry.chooseLandingPage(jwt);
    }

    /**
     * Returns login page.
     * @param model model
     * @return login_form with form
     */
    @GetMapping("/login")
    public String sendLoginPage(Model model) {
        model.addAttribute("user", new DtoUser());
        return "login_form";
    }

    /**
     * Returns JWT when given credentials are correct.
     * @param dtoUser submitted credentials
     * @param response response object used to add header
     */
    @PostMapping("/login")
    public void getLoginData(@ModelAttribute() DtoUser dtoUser, HttpServletResponse response) {

        String token = serviceEntry.loginUserAndReturnToken(
                dtoUser.getEmail(), dtoUser.getPassword());

        if (Objects.nonNull(token)) {
            response.addHeader("jwt", token);
        }
    }

    /**
     * Returns registration page.
     * @param model model
     * @return join_form with form
     */
    @GetMapping("/join")
    public String sendJoinPage(Model model) {
        model.addAttribute("user", new DtoUser());
        return "join_form";
    }


    @PostMapping("/join")
    public void getJoinData(@ModelAttribute() DtoUser dtoUser, HttpServletResponse response) {

        String token = serviceEntry.registersUserAndReturnToken(
                dtoUser.getEmail(), dtoUser.getPassword());

        if (Objects.nonNull(token)) {
            response.addHeader("jwt", token);
        }
    }
}
