package com.jubaka.sors.dao;

/**
 * Created by root on 31.10.16.
 */
public interface Dao<T> {
    public T selectById(Long id);


}
