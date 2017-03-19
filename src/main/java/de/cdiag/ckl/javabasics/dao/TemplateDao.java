package de.cdiag.ckl.javabasics.dao;

import de.cdiag.ckl.javabasics.LaunchpadException;
import de.cdiag.ckl.javabasics.entities.AppEntity;
import de.cdiag.ckl.javabasics.entities.TemplateEntity;
import de.cdiag.ckl.javabasics.jooq.Tables;
import de.cdiag.ckl.javabasics.jooq.tables.records.AppRecord;
import de.cdiag.ckl.javabasics.jooq.tables.records.TemplateAppRecord;
import de.cdiag.ckl.javabasics.jooq.tables.records.TemplateRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectOnConditionStep;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Christian on 18.03.2017.
 */
@Service
@RequiredArgsConstructor
public class TemplateDao implements CrudDao<TemplateEntity> {

	private final DSLContext dsl;

	@Override
	public TemplateEntity store( TemplateEntity entity ) {
		Long id = entity.getId();
		List<AppEntity> apps = entity.getApps();

		TemplateRecord record;
		if( id != null ) {
			record = dsl.selectFrom( Tables.TEMPLATE )
								 .where( Tables.TEMPLATE.ID.eq( id ) )
								 .fetchOne();
			if( record == null ) {
				throw new LaunchpadException( String.format( "The id of an entity must be defined by the underlying database: %s", entity ) );
			}
		}
		else {
			record = dsl.newRecord( Tables.TEMPLATE );
		}
		record.from( entity );
		record.store();

		if( apps != null ) {
			dsl.deleteFrom( Tables.TEMPLATE_APP )
				.where( Tables.TEMPLATE_APP.TEMPLATE.eq( record.getId() ) )
				.execute();
			for( AppEntity app : apps ) {
				TemplateAppRecord templateApp = dsl.newRecord( Tables.TEMPLATE_APP );
				templateApp.setTemplate( record.getId() );
				templateApp.setApp( app.getId() );
				templateApp.store();
			}
		}
		return get( record.getId() ).orElseThrow( () -> new LaunchpadException( "Stored entity could not be read, check the isolation level of your DB" ) );
	}

	@Override
	public List<TemplateEntity> all() {
		LinkedList<TemplateEntity> result = new LinkedList<>();
		selectWithApp()
			.fetchStream()
			.forEach( record -> mergeTemplate( result, record ) );
		return result;
	}

	@Override
	public Optional<TemplateEntity> get( Long id ) {
		LinkedList<TemplateEntity> result = new LinkedList<>();
		selectWithApp()
			.where( Tables.TEMPLATE.ID.eq( id ) )
			.fetchStream()
			.forEach( record -> mergeTemplate( result, record ) );
		return result.isEmpty() ? Optional.empty() : Optional.of( result.getFirst() );
	}

	@Override
	public int delete( Long id ) {
		if( id == null ) { return 0; }
		return dsl.deleteFrom( Tables.TEMPLATE )
						 .where( Tables.TEMPLATE.ID.eq( id ) )
						 .execute();
	}

	private SelectOnConditionStep<Record> selectWithApp() {
		return dsl.select()
						 .from( Tables.TEMPLATE )
						 .leftJoin( Tables.TEMPLATE_APP ).on( Tables.TEMPLATE_APP.TEMPLATE.eq( Tables.TEMPLATE.ID ) )
						 .leftJoin( Tables.APP ).on( Tables.APP.ID.eq( Tables.TEMPLATE_APP.APP ) );
	}

	private void mergeTemplate( LinkedList<TemplateEntity> result, Record record ) {
		TemplateEntity tpl = record.into( TemplateRecord.class ).into( TemplateEntity.class );
		AppEntity app = record.into( AppRecord.class ).into( AppEntity.class );

		TemplateEntity current;
		if( result.isEmpty() || !result.getLast().getId().equals( tpl.getId() ) ) {
			result.add( current = tpl );
		}
		else {
			current = result.getLast();
		}
		if( app.getId() != null ) {
			current.getApps().add( app );
		}
	}
}
