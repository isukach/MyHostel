package by.bsuir.suite.dao.common;

import by.bsuir.suite.domain.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author i.sukach
 */
public interface GenericDao<T extends Entity> extends Serializable {

    void create(T entity);

    T update(T entity);

    T get(Long id);

    void delete(T entity);

    Long count();

    List<T> findFrom(Integer from, Integer count);

    List<T> findAll();
}
