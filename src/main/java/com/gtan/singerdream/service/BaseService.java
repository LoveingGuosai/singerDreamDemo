package com.gtan.singerdream.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 15-7-30.
 */
public abstract class BaseService<T,ID extends Serializable> {
    abstract JpaRepository<T,ID> getDao();

    @Transactional
    public T save(T t){return  getDao().save(t);}

    @Transactional
    public void delete(T t){getDao().delete(t);}

    @Transactional
    public  void  deleteById(ID id){getDao().delete(id);}

    public T getById(ID id){return getDao().findOne(id);}

    public List<T> getAll(){return getDao().findAll();}
}
