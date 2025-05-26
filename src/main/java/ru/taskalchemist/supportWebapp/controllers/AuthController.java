package ru.taskalchemist.supportWebapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.taskalchemist.supportWebapp.models.User;
import ru.taskalchemist.supportWebapp.services.UserService;
import ru.taskalchemist.supportWebapp.util.UserValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public AuthController(final UserService userService, final UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/registration";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }


    @PostMapping("/registration")
    public String registrationProcess(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }
        userService.save(user);
        return "redirect:/login";
    }
}
