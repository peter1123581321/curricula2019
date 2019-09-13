package masterarbeit.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "googlesearch")
public class GoogleSearchEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idunique")
	private GoogleWordUniqueEntity googleWordUniqueEntity;

	@Lob
	private String title;

	@Lob
	private String link;

	@Lob
	@Column(name = "url")
	private String formattedUrl;
	
	private Long ordnung;
		
	@Column(name="iduniquesearch")
	private Long idUniqueSearch;
	
	public GoogleSearchEntity() {
		
	}

	public GoogleSearchEntity(String formattedUrl, String link, String title, GoogleWordUniqueEntity googleWordUniqueEntity, long ordnung) {
		this.formattedUrl = formattedUrl;
		this.link = link;
		this.title = title;
		this.googleWordUniqueEntity = googleWordUniqueEntity;
		this.ordnung = ordnung;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GoogleWordUniqueEntity getGoogleWodUniqueEntity() {
		return googleWordUniqueEntity;
	}

	public void setGoogleWodUniqueEntity(GoogleWordUniqueEntity googleWodUniqueEntity) {
		this.googleWordUniqueEntity = googleWodUniqueEntity;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getFormattedUrl() {
		return formattedUrl;
	}

	public void setFormattedUrl(String formattedUrl) {
		this.formattedUrl = formattedUrl;
	}

	public Long getOrdnung() {
		return ordnung;
	}

	public void setOrdnung(Long ordnung) {
		this.ordnung = ordnung;
	}
	
	public Long getIdUniqueSearch() {
		return idUniqueSearch;
	}

	public void setIdUniqueSearch(Long idUniqueSearch) {
		this.idUniqueSearch = idUniqueSearch;
	}
}
