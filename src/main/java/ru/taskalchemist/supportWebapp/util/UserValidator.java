package ru.taskalchemist.supportWebapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.taskalchemist.supportWebapp.models.User;
import ru.taskalchemist.supportWebapp.services.UserService;

import java.util.Optional;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        Optional<User> userToValidate = userService.findByUsername(user.getUsername());
        if (userToValidate.isPresent()) {
            errors.rejectValue("username", null, "Пользователь с таким именем уже существует");
        }
    }
}
