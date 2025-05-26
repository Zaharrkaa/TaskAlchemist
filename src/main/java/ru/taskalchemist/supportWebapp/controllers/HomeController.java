package ru.taskalchemist.supportWebapp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.taskalchemist.supportWebapp.models.User;
import ru.taskalchemist.supportWebapp.security.MyUserDetails;
import ru.taskalchemist.supportWebapp.services.UserService;
import ru.taskalchemist.supportWebapp.util.UserValidator;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public HomeController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String home(Model model, HttpServletRequest request) {
        model.addAttribute("user", request.getUserPrincipal());
        return "home/home";
    }

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
        model.addAttribute("user", userDetails);
        model.addAttribute("isAdmin", userDetails.getAuthorities().stream().iterator().next().getAuthority().equals("ROLE_ADMIN"));
        return "home/profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
        model.addAttribute("user", userDetails);
        return "home/editProfile";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @AuthenticationPrincipal MyUserDetails userDetails){
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "home/editProfile";
        }
        userService.update(userDetails.getId(), user);
        return "redirect:/profile";
    }
}
