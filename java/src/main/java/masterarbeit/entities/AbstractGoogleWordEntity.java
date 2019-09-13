package masterarbeit.entities;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractGoogleWordEntity {

	private Float salience;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idunique")
	private GoogleWordUniqueEntity googleWordUniqueEntity;
	
	public Float getSalience() {
		return salience;
	}

	public void setSalience(Float salience) {
		this.salience = salience;
	}

	public GoogleWordUniqueEntity getGoogleWordUniqueEntity() {
		return googleWordUniqueEntity;
	}

	public void setGoogleWordUniqueEntity(GoogleWordUniqueEntity googleWordUniqueEntity) {
		this.googleWordUniqueEntity = googleWordUniqueEntity;
	}
}
