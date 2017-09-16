package com.valychbreak.mymedialib.controller.api;

import com.uwetrottmann.tmdb2.Tmdb;
import com.valychbreak.mymedialib.controller.api.media.MediaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by valych on 9/16/17.
 */
@Controller
@RequestMapping("/api")
public abstract class APIController {
    public static final Tmdb TMDB_INSTANCE = new Tmdb("01e924145da414b33cdc651619bb694b");
    public static final Logger LOG = LoggerFactory.getLogger(MediaController.class);
}
