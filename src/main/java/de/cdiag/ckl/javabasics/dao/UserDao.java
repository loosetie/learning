package de.cdiag.ckl.javabasics.dao;

import de.cdiag.ckl.javabasics.LaunchpadException;
import de.cdiag.ckl.javabasics.jooq.Tables;
import de.cdiag.ckl.javabasics.jooq.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Christian on 18.03.2017.
 */
@Service
@RequiredArgsConstructor
public class UserDao implements CrudDao<User> {

	private final DSLContext dsl;

	@Override
	public User store( User entity ) {
		throw new LaunchpadException( "Not supported" );
	}

	@Override
	public List<User> all() {
		return dsl.selectFrom( Tables.USER )
						 .fetchInto( User.class );
	}

	@Override
	public Optional<User> get( Long id ) {
		return dsl.selectFrom( Tables.USER )
						 .where( Tables.USER.ID.eq( id ) )
						 .fetchOptional()
						 .map( r -> r.into( User.class ) );
	}

	@Override
	public int delete( Long id ) {
		throw new LaunchpadException( "Not supported" );
	}
}
