package ru.taskalchemist.supportWebapp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.taskalchemist.supportWebapp.models.User;
import ru.taskalchemist.supportWebapp.services.UserService;
import ru.taskalchemist.supportWebapp.util.UserValidator;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public UsersController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String users(Model model, HttpServletRequest request) {
        model.addAttribute("currentUser", request.getUserPrincipal());
        model.addAttribute("users", userService.findAll());
        return "users/users";
    }

    @GetMapping("/{id}")
    public String showUser(Model model, @PathVariable int id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        if(user.getRole().equals("ROLE_ADMIN")) {
            model.addAttribute("role", "Админ");
        }
        else{
            model.addAttribute("role", "Пользователь");
        }
        return "users/showUser";
    }

    @PatchMapping("/{id}/makeAdmin")
    public String makeAdmin(@PathVariable int id) {
        userService.makeUserAdmin(id);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.delete(id);
        return "redirect:/users";
    }

}
