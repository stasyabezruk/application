package app.common;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.List;

public abstract class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

    @PersistenceContext
    private EntityManager em;

    private Class<T> entityClass;

    private final String QUERY_LIST_ALL;

    public GenericDAOImpl() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();

        try {
            Object typeVariable = parameterizedType.getActualTypeArguments()[0];
            if (typeVariable instanceof Class)
                this.entityClass = (Class<T>) typeVariable;
            else {
                @SuppressWarnings("rawtypes")
                TypeVariable<?> t = (TypeVariable) typeVariable;
                this.entityClass = (Class<T>) t.getBounds()[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        QUERY_LIST_ALL = "FROM " + this.entityClass.getName();
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public T findById(ID id) {
        return em.find(getEntityClass(), id);
    }

    @Override
    public List<T> list() {
        Query query = em.createQuery(QUERY_LIST_ALL);
        return listByQuery(query);
    }

    @Override
    public List<T> list(final int first, final int maxResults) {
        Query query = em.createQuery(QUERY_LIST_ALL);
        return listByQuery(query.setFirstResult(first).setMaxResults(maxResults));
    }

    @Override
    @Transactional
    @SuppressWarnings("hiding")
    public <T> T save(T entity) {
        return em.merge(entity);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    public void refresh(T entity) {
        em.refresh(entity);
    }

    protected List listByNativeQuery(String queryString) {
        Query query = em.createNativeQuery(queryString);
        return query.getResultList();
    }

    protected List<?> listByNativeQuery(String queryString, Object... parameters) {
        Query query = em.createNativeQuery(queryString);
        prepareQuery(query, null, null, false, parameters);
        return query.getResultList();
    }

    protected List<T> listByQuery(Query query) {
        return query.getResultList();
    }

    protected List<T> listByQuery(String namedQuery, Object... parameters) {
        Query query = createLimitedQuery(namedQuery, null, null, parameters);
        return listByQuery(query);
    }

    protected List<T> listByLimitedQuery(String namedQuery, Integer maxResults, Object... parameters) {
        Query query = createLimitedQuery(namedQuery, null, maxResults, parameters);
        return listByQuery(query);
    }

    protected Query createQuery(String namedQuery, Object... parameters) {
        return createLimitedCacheableQuery(namedQuery, null, null, true, parameters);
    }

    @SuppressWarnings("unchecked")
    protected T findByQuery(String namedQuery, Object... parameters) {
        try {
            Query query = createLimitedQuery(namedQuery, null, 1, parameters);
            return (T) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private Query createLimitedQuery(String namedQuery, Integer firstResult, Integer maxResults, Object... parameters) {
        return createLimitedCacheableQuery(namedQuery, firstResult, maxResults, true, parameters);
    }

    private Query createLimitedCacheableQuery(String namedQuery, Integer firstResult, Integer maxResults, boolean cacheable, Object... parameters) {
        Query query = em.createNamedQuery(namedQuery);
        return prepareQuery(query, firstResult, maxResults, cacheable, parameters);
    }

    private Query prepareQuery(Query query, Integer firstResult, Integer maxResults, boolean cacheable, Object... parameters) {
        if (firstResult != null && firstResult >= 0)
            query.setFirstResult(firstResult);

        if (maxResults != null && maxResults > 0)
            query.setMaxResults(maxResults);

        if (parameters != null && parameters.length > 0)
            if (parameters.length == 1)
                query.setParameter(0, parameters[0]);
            else if (parameters.length % 2 == 0)
                for (int i = 0; i < parameters.length; i += 2) {
                    Object name = parameters[i];
                    if (name instanceof String) {
                        Object value = parameters[i + 1];
                        query.setParameter((String) name, value);
                    } else
                        throw new RuntimeException("Invalid named parameter when executing NamedQuery: \"" + query.toString() + "\"");
                }
            else
                throw new RuntimeException("Invalid number of parameters when executing NamedQuery: \"" + query.toString() + "\"");

        if (cacheable) {
            query.setHint("org.hibernate.cacheable", true);
            query.setHint("org.hibernate.cacheRegion", "queries");
        } else
            query.setHint("org.hibernate.cacheable", false);

        return query;
    }


}
