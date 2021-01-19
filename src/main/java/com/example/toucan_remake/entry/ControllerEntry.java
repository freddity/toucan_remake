package com.example.toucan_remake.entry;

import com.example.toucan_remake.dto.DtoUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
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
    public String getLandingPage(@CookieValue(value = "jwt", required = false) String jwt) {
        return serviceEntry.chooseLandingPage(jwt);
    }

    /**
     * Returns login page.
     * @param model model
     * @return login_form with form
     */
    @GetMapping("/login")
    public String sendLoginPage(@CookieValue(value = "jwt", required = false) String jwt, Model model) {

        if (Objects.isNull(jwt)) {
            model.addAttribute("user", new DtoUser());
            return "login_form";
        }

        if (serviceEntry.isTokenCorrect(jwt)) {
            return "redirect:/";
        }

        model.addAttribute("user", new DtoUser());
        return "login_form";
    }

    /**
     * Returns registration page.
     * @param model model
     * @return join_form with form
     */
    @GetMapping("/join")
    public String sendJoinPage(@CookieValue(value = "jwt", required = false) String jwt, Model model) {

        if (Objects.isNull(jwt)) {
            model.addAttribute("user", new DtoUser());
            return "join_form";
        }

        if (serviceEntry.isTokenCorrect(jwt)) {
            return "redirect:/";
        }

        model.addAttribute("user", new DtoUser());
        return "join_form";
    }

    /**
     * Returns JWT when given credentials are correct.
     * @param dtoUser submitted credentials
     * @param response response object used to add header
     * @return dashboard when credentials are correct or landing_page when aren't
     */
    @PostMapping("/login")
    public String getLoginData(@ModelAttribute() DtoUser dtoUser, HttpServletResponse response) {

        String token = serviceEntry.loginUserAndReturnToken(
                dtoUser.getEmail(), dtoUser.getPassword());

        if (Objects.nonNull(token)) {
            Cookie cookie = new Cookie("jwt", token);
            response.addCookie(cookie);
            return "rseredirect:/";
        }

        return null; //always before an error will be thrown in loginUserAndReturnToken()
    }

    /**
     *
     * @param dtoUser
     * @param response
     * @return
     */
    @PostMapping("/join")
    public String getJoinData(@ModelAttribute() DtoUser dtoUser, HttpServletResponse response) {

        String token = serviceEntry.registersUserAndReturnToken(
                dtoUser.getEmail(), dtoUser.getPassword());

        if (Objects.nonNull(token)) {
            Cookie cookie = new Cookie("jwt", token);
            response.addCookie(cookie);
            return "redirect:/";
        }

        return null; //always before an error will be thrown in registersUserAndReturnToken()
    }
}
