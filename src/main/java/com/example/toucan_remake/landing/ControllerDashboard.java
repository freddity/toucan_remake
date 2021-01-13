package com.example.toucan_remake.landing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerDashboard {

    @GetMapping
    public String getLandingPage() {


        if () {
            return "landing_page";
        } else if () {
            return "dashboard";
        }
    }
}
