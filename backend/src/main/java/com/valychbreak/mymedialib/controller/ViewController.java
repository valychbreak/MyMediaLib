package com.valychbreak.mymedialib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping(value = {"/signin", "/movie"})
    public String viewResolver() {
        return "forward:/index.html";
    }
}
