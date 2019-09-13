package masterarbeit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import masterarbeit.entities.DewikiEntity;

@Repository
public interface DewikiRepository extends JpaRepository<DewikiEntity, Long> {

}
