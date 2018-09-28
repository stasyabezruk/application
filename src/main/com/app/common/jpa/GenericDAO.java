package com.app.common.jpa;
import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, ID extends Serializable> {
    T findById(ID id);
    List<T> list();
    List<T> list(final int start, final int maxResults);
    <T> T save(T entity);
    void delete(T entity);
    void refresh (T entity);
}
