package com.app.service.serviceImpl;

import com.app.dao.AdDAO;
import com.app.dto.CreateEditAdForm;
import com.app.model.Ad;
import com.app.model.enums.AdStatus;
import com.app.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("adService")
public class AdServiceImpl implements AdService {
    private AdDAO adDAO;

    @Autowired
    public AdServiceImpl(AdDAO adDAO) {
        this.adDAO = adDAO;
    }

    public AdDAO getAdDAO() {
        return adDAO;
    }

    public void setAdDAO(AdDAO adDAO) {
        this.adDAO = adDAO;
    }

    @Override
    public List<Ad> getAds() {
        List<Ad> ads = adDAO.listByAdStatus(AdStatus.PENDING);
        return ads;
    }

    @Override
    public Ad saveAd(Ad ad) {
        ad = adDAO.save(ad);
        return ad;
    }

    @Override
    @Transactional
    public Ad createAd(CreateEditAdForm form) {
        Ad ad = new Ad();
        ad.setTitle(form.getTitle());
        ad.setDescription(form.getDescription());
        ad.setAdStatus(AdStatus.PENDING);
        ad.setPublishedDate(null);

        return saveAd(ad);
    }
}
