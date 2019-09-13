package masterarbeit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import masterarbeit.entities.CurriculaEntity;

@Repository
public interface CurriculaRepository extends JpaRepository<CurriculaEntity, Long> {

}
