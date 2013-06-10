package by.bsuir.suite.dao.common;

import by.bsuir.suite.domain.Entity;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author i.sukach
 */
@Transactional
public class GenericDaoImpl<T extends Entity> extends HibernateDaoSupport implements GenericDao<T> {
    private Class<T> persistentClass;

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void init(SessionFactory sessionFactory){
        setSessionFactory(sessionFactory);
    }

    public GenericDaoImpl(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @Override
    public void create(T entity){
        getSession().persist(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T update(T entity) {
        return (T) getSession().merge(entity);
    }

    @Override
    public Long count(){
        return (Long) getSession().createQuery("SELECT count(*) FROM " + persistentClass.getName()).uniqueResult();
    }

    @Override
    public void delete(T entity) {
        getSession().delete(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(Long id) {
        return (T) getSession().get(persistentClass, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findFrom(final Integer from, final Integer count){
        Criteria criteria = getSession().createCriteria(persistentClass);
        criteria.setFirstResult(from);
        criteria.setMaxResults(count);
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        Criteria criteria = getSession().createCriteria(persistentClass);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }
}
