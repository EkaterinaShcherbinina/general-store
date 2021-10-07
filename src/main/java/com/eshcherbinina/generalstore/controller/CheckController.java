package com.eshcherbinina.generalstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CheckController {

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public String displayHello(Model model) {
        System.out.println("Hello");
        return "greeting";
    }
}
