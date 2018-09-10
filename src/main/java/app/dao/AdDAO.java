package app.dao;

import app.common.GenericDAO;
import app.model.Ad;
import app.model.enums.AdStatus;

import java.util.List;

public interface AdDAO extends GenericDAO<Ad, Long> {
    Ad findById(Long id);

    List<Ad> listByAdStatus(AdStatus adStatus);
}
