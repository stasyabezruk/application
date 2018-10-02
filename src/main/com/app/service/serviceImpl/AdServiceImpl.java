package com.app.service.serviceImpl;

import com.app.dao.AdDAO;
import com.app.model.Ad;
import com.app.model.enums.AdStatus;
import com.app.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adService")
public class AdServiceImpl implements AdService {
    @Autowired
    private AdDAO adDAO;

    @Override
    public List<Ad> getAds() {
        List<Ad> ads = adDAO.listByAdStatus(AdStatus.PUBLISHED);
        return ads;
    }

    @Override
    public Ad saveAd(Ad ad) {
        return null;
    }
}
