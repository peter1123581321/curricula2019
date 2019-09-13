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
@Table(name = "curricula")
public class CurriculaEntity extends AbstractTextEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String curricula;

	@Lob
	private String title;

	@Lob
	private String text;
	
	@OneToMany(mappedBy="curriculaId")
	private List<GoogleWordCurriculaEntity> googleWordCurriculaEntities;

	public CurriculaEntity() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurricula() {
		return curricula;
	}

	public void setCurricula(String curricula) {
		this.curricula = curricula;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
//    public List<GoogleWordCurriculaEntity> getGoogleWordEntities() {
//		return googleWordCurriculaEntities;
//	}

	public void setGoogleWordEntities(List<GoogleWordCurriculaEntity> googleWordCurriculaEntity) {
		this.googleWordCurriculaEntities = googleWordCurriculaEntity;
	}

	@Override
	public List<? extends AbstractGoogleWordEntity> getGoogleWordEntities() {
		return googleWordCurriculaEntities;
	}
}
