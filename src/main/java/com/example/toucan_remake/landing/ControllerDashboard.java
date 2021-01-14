package com.example.toucan_remake.landing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerDashboard {

    @GetMapping
    public String getLandingPage(@CookieValue(value = "jwt") String jwt) {
        //checking mechanism

        if (//if isn't) {
            return "landing_page";
        } else if (//if is) {
            return "dashboard";
        }
    }
}
