package de.cdiag.ckl.javabasics.dao;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by CKL on 17.03.2017.
 */
public interface CrudDao<T> {

    List<T> all();

    T store(T entity);

    T get(Long id);

    T delete(Long id);

}
