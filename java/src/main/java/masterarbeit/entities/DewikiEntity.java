package masterarbeit.entities;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="dewiki")
public class DewikiEntity {

//	public static final String PRIMARYKEY = "page_id";

	@Id
	@Column(name="page_id")
	private Long pageId;

	@Lob
	private Blob old_text;
	
	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	public Blob getOld_text() {
		return old_text;
	}

	public void setOld_text(Blob old_text) {
		this.old_text = old_text;
	}	
}
