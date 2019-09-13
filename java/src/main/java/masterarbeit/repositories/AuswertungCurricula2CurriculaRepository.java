package masterarbeit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import masterarbeit.entities.AuswertungCurricula2CurriculaEntity;

@Repository
public interface AuswertungCurricula2CurriculaRepository extends JpaRepository<AuswertungCurricula2CurriculaEntity, Long> {

}
