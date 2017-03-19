package de.cdiag.ckl.javabasics.entities;

import de.cdiag.ckl.javabasics.jooq.tables.pojos.App;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.sql.Timestamp;

/**
 * Created by Christian on 18.03.2017.
 */
@NoArgsConstructor
public class AppEntity extends App {

	public AppEntity( AppEntity value ) {
		super( value );
	}

	public AppEntity( Long id, Timestamp timestamp, String name ) {
		super( id, timestamp, name );
	}

	@Override
	public boolean equals( Object o ) {
		if( this == o ) { return true; }
		if( o == null || getClass() != o.getClass() ) { return false; }
		AppEntity that = (AppEntity) o;
		return new EqualsBuilder()
						 .append( getId(), that.getId() )
						 .isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder( 17, 37 )
						 .append( getId() )
						 .toHashCode();
	}
}
