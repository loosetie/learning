package de.cdiag.ckl.javabasics.dao;

import de.cdiag.ckl.javabasics.entities.AppEntity;
import de.cdiag.ckl.javabasics.jooq.Tables;
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
public class AppDao implements CrudDao<AppEntity> {

	private final DSLContext dsl;

	@Override
	public AppEntity store( AppEntity entity ) {
		AppRecord record = getImpl( entity.getId() )
												 .orElse( dsl.newRecord( Tables.APP ) );
		record.from( entity );
		record.store(); // Make use of listeners
		return record.into( AppEntity.class );
	}

	@Override
	public List<AppEntity> all() {
		return dsl.selectFrom( Tables.APP )
						 .fetchInto( AppEntity.class );
	}

	@Override
	public Optional<AppEntity> get( Long id ) {
		return getImpl( id )
						 .map( r -> r.into( AppEntity.class ) );
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
