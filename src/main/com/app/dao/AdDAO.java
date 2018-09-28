package com.app.dao;

import com.app.common.jpa.GenericDAO;
import com.app.model.Ad;
import com.app.model.enums.AdStatus;

import java.util.List;

public interface AdDAO extends GenericDAO<Ad, Long> {
    Ad findById(Long id);

    List<Ad> listByAdStatus(AdStatus adStatus);
}
