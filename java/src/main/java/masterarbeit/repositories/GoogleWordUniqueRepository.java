package masterarbeit.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import masterarbeit.entities.GoogleWordUniqueEntity;

@Repository
public interface GoogleWordUniqueRepository extends JpaRepository<GoogleWordUniqueEntity, Long> {

	List<GoogleWordUniqueEntity> findByIdGreaterThanEqual(long id);

	List<GoogleWordUniqueEntity> findByIdNotIn(Collection<Long> ids);
}
