package de.cdiag.ckl.javabasics.dao;

import de.cdiag.ckl.javabasics.jooq.tables.pojos.App;

import java.util.List;
import java.util.Optional;

/**
 * Created by CKL on 17.03.2017.
 */
public interface CrudDao<T> {

	List<T> all();

	T store( T entity );

	Optional<App> get( Long id );

	int delete( Long id );

}
