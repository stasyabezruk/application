package com.app.controller;

import com.app.model.Ad;
import com.app.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private AdService adService;

    @RequestMapping(value = "frontpage.do", method = RequestMethod.GET)
    public String getFrontPage(Model model) {
        model.addAttribute("title", "Title");

        List<Ad> ads = adService.getAds();
        model.addAttribute("ads", ads);
        return "/home";
    }
}
