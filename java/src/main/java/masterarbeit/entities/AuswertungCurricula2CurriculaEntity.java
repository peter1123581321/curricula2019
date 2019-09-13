package masterarbeit.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "auswertung_curricula2curricula")
public class AuswertungCurricula2CurriculaEntity extends AbstractAuswertungEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_id_1")
	private CurriculaEntity curricula1;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_id_2")
	private CurriculaEntity curricula2;
	
	public AuswertungCurricula2CurriculaEntity() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CurriculaEntity getCurricula1() {
		return curricula1;
	}

	public void setCurricula1(CurriculaEntity curricula1) {
		this.curricula1 = curricula1;
	}

	public CurriculaEntity getCurricula2() {
		return curricula2;
	}

	public void setCurricula2(CurriculaEntity curricula2) {
		this.curricula2 = curricula2;
	}

	@Override
	public AbstractTextEntity getText1() {
		return curricula1;
	}

	@Override
	public AbstractTextEntity getText2() {
		return curricula2;
	}

	@Override
	public String getInfo() {
		return "process: course1 " + curricula1.getId() + ", course2 " + curricula2.getId();
	}
}
