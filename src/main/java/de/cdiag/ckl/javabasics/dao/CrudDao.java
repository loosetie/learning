package de.cdiag.ckl.javabasics.dao;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by CKL on 17.03.2017.
 */
public interface CrudDao<T> {

	@Transactional
	T store( T entity );

	@Transactional(readOnly = true)
	List<T> all();

	@Transactional(readOnly = true)
	Optional<T> get( Long id );

	@Transactional
	int delete( Long id );

}
