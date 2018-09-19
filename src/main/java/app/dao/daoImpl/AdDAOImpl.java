package app.dao.daoImpl;

import app.common.GenericDAOImpl;
import app.dao.AdDAO;
import app.model.Ad;
import app.model.enums.AdStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("adDao")
public class AdDAOImpl extends GenericDAOImpl<Ad, Long> implements AdDAO {

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
