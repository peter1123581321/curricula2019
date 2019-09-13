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
@Table(name = "auswertung_job2job")
public class AuswertungJob2JobEntity extends AbstractAuswertungEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="job_id_1")
	private JobEntity job1;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="job_id_2")
	private JobEntity job2;
	
	public AuswertungJob2JobEntity() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public JobEntity getJob1() {
		return job1;
	}

	public void setJob1(JobEntity job1) {
		this.job1 = job1;
	}

	public JobEntity getJob2() {
		return job2;
	}

	public void setJob2(JobEntity job2) {
		this.job2 = job2;
	}

	@Override
	public AbstractTextEntity getText1() {
		return job1;
	}

	@Override
	public AbstractTextEntity getText2() {
		return job2;
	}

	@Override
	public String getInfo() {
		return "process: job1 " + job1.getId() + ", job2 " + job2.getId();
	}
}