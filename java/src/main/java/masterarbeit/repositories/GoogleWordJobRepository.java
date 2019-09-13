package masterarbeit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import masterarbeit.entities.GoogleWordJobEntity;

@Repository
public interface GoogleWordJobRepository extends JpaRepository<GoogleWordJobEntity, Long> {

}
