package com.example.toucan_remake.entry;

import com.example.toucan_remake.dto.DtoUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ControllerEntry {

    private final ServiceEntry serviceEntry;

    public ControllerEntry(ServiceEntry serviceEntry) {
        this.serviceEntry = serviceEntry;
    }

    @GetMapping
    public String getLandingPage(@CookieValue(value = "jwt") String jwt) {
        return serviceEntry.chooseLandingPage(jwt);
    }

    @GetMapping("/login")
    public String sendLoginPage(Model model) {
        model.addAttribute("user", new DtoUser());
        return "login_form";
    }

    @PostMapping("/login")
    public String getLoginData(@ModelAttribute() DtoUser dtoUser) {

        return "redirect:/";
    }

    @GetMapping("/join")
    public String sendJoinPage(Model model) {
        model.addAttribute("user", new DtoUser());
        return "join_form";
    }

    @PostMapping("/join")
    public String getJoinData(@ModelAttribute() DtoUser dtoUser) {

        return "redirect:/";
    }
}
