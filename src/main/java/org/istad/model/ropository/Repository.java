package org.istad.model.ropository;

import java.util.List;

public interface Repository<T, K> {
    List<T> findAll();
    T findById(K id);
    T save(T t);
}
