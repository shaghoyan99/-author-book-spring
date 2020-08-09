package am.itspace.controller;

import am.itspace.model.User;
import am.itspace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.Optional;

@ControllerAdvice
@RequiredArgsConstructor
public class  HeaderController {

    private final UserService userService;


    @ModelAttribute("username")
    public String username (@AuthenticationPrincipal Principal principal) {
        String userEmail = null;
        String username = null;
        if (principal != null) {
            userEmail = principal.getName();
            Optional<User> byEmail = userService.findByEmail(userEmail);
            User user = byEmail.get();
            username = user.getName();
        }
        return username;
    }
}
