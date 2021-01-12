package com.example.toucan_remake.landing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerLanding {

    @GetMapping
    public String getLandingPage() {
        return "landing_page";
    }
}
