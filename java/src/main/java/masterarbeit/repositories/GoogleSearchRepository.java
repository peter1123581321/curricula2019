package masterarbeit.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import masterarbeit.entities.GoogleSearchEntity;

@Repository
public interface GoogleSearchRepository extends JpaRepository<GoogleSearchEntity, Long> {

	public Optional<GoogleSearchEntity> findByTitle(String title);
	
//	public List<GoogleSearchEntity> findByGoogleWordUniqueEntity(GoogleWordUniqueEntity googleWordUniqueEntity);
	
	
}
