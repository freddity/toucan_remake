package com.example.toucan_remake.entry;

import com.example.toucan_remake.dto.DtoUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class ControllerEntry {

    @GetMapping("/login")
    public String sendLoginPage(Model model) {
        DtoUser dtoUser = new DtoUser();
        model.addAttribute("user", dtoUser);
        return "login_form";
    }

    @PostMapping("/login/submit")
    public String getLoginData(@ModelAttribute() DtoUser dtoUser) {

    }

    @GetMapping("/join")
    public String sendJoinPage(Model model) {
        DtoUser dtoUser = new DtoUser();
        model.addAttribute("user", dtoUser);
        return "join_form";
    }

    @PostMapping("/join/submit")
    public String getJoinData(@ModelAttribute() DtoUser dtoUser) {

    }
}
