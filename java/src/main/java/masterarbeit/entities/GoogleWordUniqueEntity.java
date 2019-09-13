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
@Table(name = "googlewordunique")
public class GoogleWordUniqueEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	private String name;
	

	
	@OneToMany(mappedBy="googleWordUniqueEntity")
	private List<GoogleSearchEntity> googleSearchEntities;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GoogleSearchEntity> getGoogleSearchEntities() {
		return googleSearchEntities;
	}

	public void setGoogleSearchEntities(List<GoogleSearchEntity> googleSearchEntities) {
		this.googleSearchEntities = googleSearchEntities;
	}
}