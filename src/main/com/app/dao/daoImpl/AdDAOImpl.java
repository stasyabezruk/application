package com.app.dao.daoImpl;

import com.app.common.jpa.GenericDAOImpl;
import com.app.dao.AdDAO;
import com.app.model.Ad;
import com.app.model.enums.AdStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository("adDao")
public class AdDAOImpl extends GenericDAOImpl<Ad, Long> implements AdDAO, Serializable {

    @Override
    @Transactional
    public <T> T save(T entity) {
        Ad ad = (Ad) entity;
        return super.save((T) ad);
    }

    @Override
    public Ad findById(Long id) {
        return findByQuery("Ad.findById", "id", id);
    }

    @Override
    public List<Ad> listByAdStatus(AdStatus adStatus) {
        return listByQuery("Ad.listByAdStatus", "adStatus", adStatus);
    }

}
