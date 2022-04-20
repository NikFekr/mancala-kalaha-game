package com.example.mancalagame.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "home";//TODO: Write UI Home Page
    }
}
