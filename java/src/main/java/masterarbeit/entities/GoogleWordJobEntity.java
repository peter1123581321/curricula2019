package masterarbeit.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "googlewordjob")
public class GoogleWordJobEntity extends AbstractGoogleWordEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="job")
	private JobEntity jobId;

	@Lob
	private String name;
	
//	private Float salience;
//	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="idunique")
//	private GoogleWordUniqueEntity googleWordUniqueEntity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public JobEntity getJobId() {
		return jobId;
	}

	public void setJobId(JobEntity jobId) {
		this.jobId = jobId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public Float getSalience() {
//		return salience;
//	}
//
//	public void setSalience(Float salience) {
//		this.salience = salience;
//	}
//
//	public GoogleWordUniqueEntity getGoogleWordUniqueEntity() {
//		return googleWordUniqueEntity;
//	}
//
//	public void setGoogleWordUniqueEntity(GoogleWordUniqueEntity googleWordUniqueEntity) {
//		this.googleWordUniqueEntity = googleWordUniqueEntity;
//	}
}
