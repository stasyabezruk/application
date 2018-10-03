package com.app.service;

import com.app.dto.CreateEditAdForm;
import com.app.model.Ad;

import java.util.List;

public interface AdService {

    List<Ad> getAds();

    Ad saveAd(Ad ad);

    Ad createAd(CreateEditAdForm form);
}
