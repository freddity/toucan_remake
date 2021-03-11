package com.example.toucan_remake.dashboard;

import com.example.toucan_remake.util.UtilJWT;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
public class ControllerDashboard {

    @GetMapping("/dashboard")
    public String sendDashboard() {
        return "dashboard";
    }
}
