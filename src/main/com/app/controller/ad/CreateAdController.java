package com.app.controller.ad;

import com.app.dto.CreateEditAdForm;
import com.app.model.Ad;
import com.app.service.AdService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CreateAdController {

    private AdService adService;

    @Autowired
    public CreateAdController(AdService adService) {
        this.adService = adService;
    }

    public AdService getAdService() {
        return adService;
    }

    public void setAdService(AdService adService) {
        this.adService = adService;
    }

    @RequestMapping(value = "/ad/create.do", method = RequestMethod.GET)
    public String getCreateAdPage(@ModelAttribute("createAdForm") CreateEditAdForm form,
                                  Model model) {

        return "/ad/createEditAd";
    }

    @RequestMapping(value = "/ad/save.do", method = RequestMethod.POST)
    public String createAd(@ModelAttribute("createAdForm") CreateEditAdForm form,
                           Model model) {

        Ad ad = adService.createAd(form);

        return "redirect:/frontpage.do";
    }

}
