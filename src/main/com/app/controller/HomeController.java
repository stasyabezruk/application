package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
    @RequestMapping(value = "frontpage.do", method = RequestMethod.GET)
    public String getFrontPage(Model model) {
        model.addAttribute("title", "Title");
        return "/home";
    }
}
