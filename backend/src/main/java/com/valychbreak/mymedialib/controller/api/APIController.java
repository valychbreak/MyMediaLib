package com.valychbreak.mymedialib.controller.api;

import com.uwetrottmann.tmdb2.Tmdb;
import com.valychbreak.mymedialib.controller.api.media.MediaController;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * Created by valych on 9/16/17.
 */
@Controller
@RequestMapping("/api")
public abstract class APIController {
    public static final Tmdb TMDB_INSTANCE = new Tmdb("01e924145da414b33cdc651619bb694b");
    public static final Logger LOG = LoggerFactory.getLogger(MediaController.class);

    @Autowired
    protected UserRepository userRepository;

    protected User getLoggedUser() {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();
        return userRepository.findFirstByUsername(username);
    }

    protected User getUserFromPrincipal(Principal principal) {
        Assert.notNull(principal);

        User loggedUser = userRepository.findFirstByUsername(principal.getName());
        Assert.notNull(loggedUser);

        return loggedUser;
    }
}
