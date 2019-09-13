package masterarbeit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import masterarbeit.entities.JobEntity;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {

}
