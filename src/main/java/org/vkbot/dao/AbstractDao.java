package org.vkbot.dao;

import java.util.List;

public abstract class AbstractDao<E, K> {
    public abstract E findById(K id);
    public abstract void save(E entity);
    public abstract void update(E entity);
    public abstract void delete(E entity);
    public abstract List<E> findAll();
}
