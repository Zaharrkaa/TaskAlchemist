package ru.taskalchemist.supportWebapp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String adminPage(HttpServletRequest request, Model model) {
        model.addAttribute("user", request.getUserPrincipal());
        return "admin/admin";
    }
}
