package masterarbeit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import masterarbeit.entities.GoogleWordCurriculaEntity;

@Repository
public interface GoogleWordCurriculaRepository extends JpaRepository<GoogleWordCurriculaEntity, Long> {

}
