package com.example.toucan_remake.entry;

import com.example.toucan_remake.dto.DtoUserLog;
import com.example.toucan_remake.dto.DtoUserReg;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Controller services signin/signup requests.
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
            model.addAttribute("user", new DtoUserLog());
            model.addAttribute("errorMessage", "");
            return "login_form";
        }

        if (serviceEntry.isTokenCorrect(jwt)) {
            return "redirect:/dashboard"; //or "redirect:/" but "dashboard" allow to test easier
        }

        model.addAttribute("user", new DtoUserLog());
        model.addAttribute("errorMessage", "");
        return "login_form";
    }

    /**
     * Returns registration page.
     * @param model model
     * @return join_form with form
     */
    @GetMapping("/join")
    public String sendJoinPage(@CookieValue(value = "jwt", required = false) String jwt, Model model,
                               HttpServletResponse response) {

        if (Objects.isNull(jwt)) {
            model.addAttribute("user", new DtoUserReg());
            response.setStatus(200);
            return "join_form";
        }

        if (serviceEntry.isTokenCorrect(jwt)) {
            response.setStatus(200);
            return "redirect:/dashboard"; //or "redirect:/" but "dashboard" allow to test easier
        }

        model.addAttribute("user", new DtoUserReg());
        response.setStatus(200);
        return "join_form";
    }

    /**
     * Returns JWT when given credentials are correct.
     * @param dtoUser submitted credentials
     * @param response response object used to add a cookie
     * @return calls "/" endpoint
     */
    @PostMapping("/login")
    public String getLoginData(@ModelAttribute("data") @Valid DtoUserLog dtoUser, BindingResult bindingResult, HttpServletResponse response, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("user", new DtoUserLog());

            List<String> errors = new ArrayList<>();

            for (Object object : bindingResult.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;

                    errors.add(fieldError.getDefaultMessage());
                }
            }

            model.addAttribute("errorMessage", errors.toString());

            return "login_form";
        }


        String token = serviceEntry.loginUserAndReturnToken(
                dtoUser.getEmail(), dtoUser.getPassword());

        if (Objects.nonNull(token)) {
            Cookie cookie = new Cookie("jwt", token);
            response.addCookie(cookie);
            return "redirect:/dashboard"; //or "redirect:/" but "dashboard" allow to test
        }

        return null; //always before an error will be thrown in loginUserAndReturnToken()
    }


    /*Muszę ogarnąć ja zrobić aby ustawić priorytet dla
    konkretnych błędów żeby nie pojawiał się wielki behemot
    na 5 linijek*/


    /**
     * Creates user and returns JWT.
     * @param dtoUser submitted credentials
     * @param response response object used to add a cookie
     * @return calls "/" endpoint
     */
    @PostMapping("/join")
    public String getJoinData(@ModelAttribute("data") @Valid DtoUserReg dtoUser, BindingResult bindingResult, HttpServletResponse response, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("user", new DtoUserReg());

            List<String> errors = new ArrayList<>();

            for (Object object : bindingResult.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;

                    errors.add(fieldError.getDefaultMessage());
                }
            }

            model.addAttribute("errorMessage", errors.toString());

            return "join_form";
        }

        String token = serviceEntry.registersUserAndReturnToken(
                dtoUser.getEmail(), dtoUser.getPassword());

        if (Objects.nonNull(token)) {
            Cookie cookie = new Cookie("jwt", token);
            response.addCookie(cookie);
            return "redirect:/dashboard"; //or "redirect:/" but "dashboard" allow to test easier
        }

        return null; //always before an error will be thrown in registersUserAndReturnToken()
    }
}
