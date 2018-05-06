package com.valychbreak.mymedialib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by valych on 4/9/17.
 */
@Controller
public class ViewController {
    @RequestMapping(value = {"/signin", "/movie"})
    public String viewResolver() {
        return "forward:/index.html";
    }
}
