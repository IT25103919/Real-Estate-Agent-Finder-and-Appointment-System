package com.example.realestate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PropertyViewController {

    @GetMapping("/")
    public String homePage() {
        return "index";
    }
}
