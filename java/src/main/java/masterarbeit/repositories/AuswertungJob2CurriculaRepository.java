package masterarbeit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import masterarbeit.entities.AuswertungJob2CurriculaEntity;

@Repository
public interface AuswertungJob2CurriculaRepository extends JpaRepository<AuswertungJob2CurriculaEntity, Long> {

}


