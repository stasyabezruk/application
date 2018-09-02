package app.common;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.List;

public abstract class GenericDAOSpring<T, ID extends Serializable> implements GenericDAO<T, ID> {
    private EntityManager em;
    protected Class<T> entityBeanType;

    protected final String QUERY_LIST_ALL;
    private final String QUERY_COUNT_ALL;

    public GenericDAOSpring() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();

        try {
            Object typeVariable = parameterizedType.getActualTypeArguments()[0];
            if (typeVariable instanceof Class)
                this.entityBeanType = (Class<T>) typeVariable;
            else {
                @SuppressWarnings("rawtypes")
                TypeVariable<?> t = (TypeVariable) typeVariable;
                this.entityBeanType = (Class<T>) t.getBounds()[0];
            }

            if (this.entityBeanType.isInterface())
                this.entityBeanType = EntityFactory.getImplementation(entityBeanType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        QUERY_LIST_ALL = "FROM " + this.entityBeanType.getName();
        QUERY_COUNT_ALL = "SELECT COUNT(*) FROM " + this.entityBeanType.getName();
    }

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    protected EntityManager getEntityManager() {
        if (em == null)
            throw new IllegalStateException("EntityManager has not been set on DAO before usage");

        return em;
    }

    protected Class<T> getEntityBeanType() {
        return entityBeanType;
    }

    @Override
    public T findById(ID id) {
        return getEntityManager().find(getEntityBeanType(), id);
    }

    @Override
    public List<Long> listIDs() {
        return null;
    }

    @Override
    public List<T> list() {
        Query query = getEntityManager().createQuery(QUERY_LIST_ALL);
        return listByQuery(query);
    }

    @Override
    public List<T> list(final int first, final int maxResults) {
        Query query = getEntityManager().createQuery(QUERY_LIST_ALL);
        return listByQuery(query.setFirstResult(first).setMaxResults(maxResults));
    }

    @Override
    @Transactional
    @SuppressWarnings("hiding")
    public <T> T save(T entity) {
        return getEntityManager().merge(entity);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        getEntityManager().remove(getEntityManager().contains(entity) ? entity : getEntityManager().merge(entity));
    }

    @Override
    public void refresh(T entity) {
        getEntityManager().refresh(entity);
    }

    protected List listByNativeQuery(String queryString) {
        Query query = getEntityManager().createNativeQuery(queryString);
        return query.getResultList();
    }

    protected List<?> listByNativeQuery(String queryString, Object... parameters) {
        Query query = getEntityManager().createNativeQuery(queryString);
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

    protected Query createLimitedQuery(String namedQuery, Integer firstResult, Integer maxResults, Object... parameters) {
        return createLimitedCacheableQuery(namedQuery, firstResult, maxResults, true, parameters);
    }

    protected Query createLimitedCacheableQuery(String namedQuery, Integer firstResult, Integer maxResults, boolean cacheable, Object... parameters) {
        Query query = getEntityManager().createNamedQuery(namedQuery);
        return prepareQuery(query, firstResult, maxResults, cacheable, parameters);
    }

    public Query prepareQuery(Query query, Integer firstResult, Integer maxResults, boolean cacheable, Object... parameters) {
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
