package masterarbeit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import masterarbeit.entities.AuswertungJob2JobEntity;

@Repository
public interface AuswertungJob2JobRepository extends JpaRepository<AuswertungJob2JobEntity, Long> {

}
