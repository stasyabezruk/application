package com.app.controller.ad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CreateAdController {
    @RequestMapping(value = "/createAd.do", method = RequestMethod.GET)
    public String getFrontPage(Model model) {

        return "/ad/createAd";
    }
}
