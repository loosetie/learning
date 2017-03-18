package de.cdiag.ckl.javabasics.rest;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by CKL on 17.03.2017.
 */
public interface CrudController<T> {

	@RequestMapping(value = "", method = RequestMethod.GET)
	HttpEntity<List<T>> all();

	@RequestMapping(value = "", method = RequestMethod.POST)
	HttpEntity<T> create( T entity );

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	HttpEntity<T> get( Long id );

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	HttpEntity<T> update( T entity );

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	HttpEntity<?> delete( Long id );

}
