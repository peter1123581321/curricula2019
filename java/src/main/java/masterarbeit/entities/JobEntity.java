package masterarbeit.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "job")
public class JobEntity extends AbstractTextEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	private String text;
	
	@OneToMany(mappedBy="jobId")
	private List<GoogleWordJobEntity> googleWordJobEntities;
	
	public JobEntity() {
		
	}
	
	public JobEntity(String text) {
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<? extends AbstractGoogleWordEntity> getGoogleWordEntities() {
		return googleWordJobEntities;
	}

	public void setGoogleWordJobEntities(List<GoogleWordJobEntity> googleWordJobEntities) {
		this.googleWordJobEntities = googleWordJobEntities;
	}
}
