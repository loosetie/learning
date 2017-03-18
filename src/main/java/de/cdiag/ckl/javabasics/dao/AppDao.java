package de.cdiag.ckl.javabasics.dao;

import de.cdiag.ckl.javabasics.jooq.Tables;
import de.cdiag.ckl.javabasics.jooq.tables.pojos.App;
import de.cdiag.ckl.javabasics.jooq.tables.records.AppRecord;
import de.cdiag.ckl.javabasics.security.TransactionPermission;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by CKL on 17.03.2017.
 */
@Service
@TransactionPermission(read = "USER", write = "APP")
@RequiredArgsConstructor
public class AppDao implements CrudDao<App> {

	private final DSLContext dsl;

	@Override
	public App store( App entity ) {
		AppRecord record = getImpl( entity.getId() )
												 .orElse( dsl.newRecord( Tables.APP ) );
		record.from( entity );
		record.store(); // Make use of listeners
		return record.into( App.class );
	}

	@Override
	public List<App> all() {
		return dsl.selectFrom( Tables.APP )
						 .fetchInto( App.class );
	}

	@Override
	public Optional<App> get( Long id ) {
		return getImpl( id )
						 .map( r -> r.into( App.class ) );
	}

	@Override
	public int delete( Long id ) {
		if( id == null ) { return 0; }
		return dsl.deleteFrom( Tables.APP )
						 .where( Tables.APP.ID.eq( id ) )
						 .execute();
	}

	private Optional<AppRecord> getImpl( Long id ) {
		if( id == null ) { return Optional.empty(); }
		return dsl.selectFrom( Tables.APP )
						 .where( Tables.APP.ID.eq( id ) )
						 .fetchOptional();
	}
}
