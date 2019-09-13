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
@Table(name = "auswertung")
public class AuswertungJob2CurriculaEntity extends AbstractAuswertungEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="job_id")
	private JobEntity job;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_id")
	private CurriculaEntity curricula;
	
	public AuswertungJob2CurriculaEntity() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public JobEntity getJob() {
		return job;
	}

	public void setJob(JobEntity job) {
		this.job = job;
	}

	public CurriculaEntity getCurricula() {
		return curricula;
	}

	public void setCurricula(CurriculaEntity curricula) {
		this.curricula = curricula;
	}

	@Override
	public AbstractTextEntity getText1() {
		return job;
	}

	@Override
	public AbstractTextEntity getText2() {
		return curricula;
	}

	@Override
	public String getInfo() {
		return "process: course " + curricula.getId() + ", job " + job.getId();
	}
}
