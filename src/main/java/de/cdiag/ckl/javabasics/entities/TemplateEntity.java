package de.cdiag.ckl.javabasics.entities;

import de.cdiag.ckl.javabasics.jooq.tables.pojos.Template;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Christian on 18.03.2017.
 */
@NoArgsConstructor
@ToString(callSuper = true)
public class TemplateEntity extends Template implements HasId {

	private List<AppEntity> apps;

	public TemplateEntity( TemplateEntity value ) {
		super( value );
		this.apps = new ArrayList<>( value.apps );
	}

	public TemplateEntity( Long id, Timestamp timestamp, String name, List<AppEntity> apps ) {
		super( id, timestamp, name );
		this.apps = apps;
	}

	public List<AppEntity> getApps() {
		return apps == null ? apps = new LinkedList<>() : apps;
	}

	public void setApps( List<AppEntity> apps ) {
		this.apps = apps;
	}

	@Override
	public boolean equals( Object o ) {
		if( this == o ) { return true; }
		if( o == null || getClass() != o.getClass() ) { return false; }
		TemplateEntity that = (TemplateEntity) o;
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
