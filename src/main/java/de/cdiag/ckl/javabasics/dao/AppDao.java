package de.cdiag.ckl.javabasics.dao;

import de.cdiag.ckl.javabasics.jooq.Tables;
import de.cdiag.ckl.javabasics.jooq.tables.pojos.App;
import de.cdiag.ckl.javabasics.jooq.tables.records.AppRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by CKL on 17.03.2017.
 */
@Service
@RequiredArgsConstructor
public class AppDao implements CrudDao<App> {

	private final DSLContext dsl;

	@Override
	public List<App> all() {
		return dsl.selectFrom( Tables.APP )
						 .fetchInto( App.class );
	}

	@Override
	public App store( App entity ) {
		AppRecord record = getImpl( entity.getId() )
												 .orElse( dsl.newRecord( Tables.APP ) );
		record.from( entity );
		record.store(); // Make use of listeners
		return record.into( App.class );
	}

	@Override
	public Optional<App> get( Long id ) {
		return getImpl( id )
						 .map( r -> r.into( App.class ) );
	}

	private Optional<AppRecord> getImpl( Long id ) {
		return dsl.selectFrom( Tables.APP )
						 .where( Tables.APP.ID.eq( id ) )
						 .fetchOptional();
	}

	@Override
	public int delete( Long id ) {
		return dsl.deleteFrom( Tables.APP )
						 .where( Tables.APP.ID.eq( id ) )
						 .execute();
	}
}
