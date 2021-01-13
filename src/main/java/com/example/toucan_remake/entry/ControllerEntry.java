package com.example.toucan_remake.entry;

import com.example.toucan_remake.dto.DtoUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ControllerEntry {

    @GetMapping("/login")
    public String sendLoginPage(Model model) {
        DtoUser dtoUser = new DtoUser();
        model.addAttribute("user", dtoUser);
        return "login_form";
    }

    @PostMapping("/login")
    public String getLoginData(@ModelAttribute() DtoUser dtoUser) {
        System.out.println(dtoUser.toString());
        return "redirect:/";
    }

    @GetMapping("/join")
    public String sendJoinPage(Model model) {
        DtoUser dtoUser = new DtoUser();
        model.addAttribute("user", dtoUser);
        return "join_form";
    }

    @PostMapping("/join")
    public String getJoinData(@ModelAttribute() DtoUser dtoUser) {
        System.out.println(dtoUser.toString());
        return "redirect:/";
    }
}
