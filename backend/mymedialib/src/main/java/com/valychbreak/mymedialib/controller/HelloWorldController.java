package com.valychbreak.mymedialib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by valych on 2/21/17.
 */
@Controller
public class HelloWorldController {
    /*@GetMapping("/")
    public String showIndexPage(Model model) {
        model.addAttribute("name", "John Doe");

        return "welcome";
    }*/

    @GetMapping("/hello")
    public String showHelloPage(Model model) {
        model.addAttribute("message", "Hello message");

        return "hellopage";
    }
}
