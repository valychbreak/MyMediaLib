package com.valychbreak.mymedialib.controller.api;

import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/api")
public abstract class APIController {

    @Autowired
    protected UserRepository userRepository;

    protected User getLoggedUser() {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();
        return userRepository.findFirstByUsername(username);
    }

    protected User getUserFromPrincipal(Principal principal) {
        Assert.notNull(principal, "Principal must be provided");

        User loggedUser = userRepository.findFirstByUsername(principal.getName());
        Assert.notNull(loggedUser, "Principal must have valid credentials");

        return loggedUser;
    }
}
