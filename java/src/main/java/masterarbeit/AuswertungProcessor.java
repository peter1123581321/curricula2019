package masterarbeit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import masterarbeit.entities.AbstractAuswertungEntity;
import masterarbeit.entities.AbstractTextEntity;
import masterarbeit.entities.AuswertungCurricula2CurriculaEntity;
import masterarbeit.entities.AuswertungJob2CurriculaEntity;
import masterarbeit.entities.AuswertungJob2JobEntity;
import masterarbeit.repositories.AuswertungCurricula2CurriculaRepository;
import masterarbeit.repositories.AuswertungJob2CurriculaRepository;
import masterarbeit.repositories.AuswertungJob2JobRepository;
import masterarbeit.similarity.SimilarityCosinusKoeffizient;
import masterarbeit.similarity.SimilarityDiceKoeffizient;
import masterarbeit.similarity.SimilarityEinfacheUebereinstimmung;
import masterarbeit.similarity.SimilarityJaccardKoeffizient;
import masterarbeit.similarity.SimilarityOverlapKoeffizient;

@Service
public class AuswertungProcessor {

	private static Logger LOG = LoggerFactory.getLogger(AuswertungProcessor.class);

	@Autowired
	AuswertungJob2CurriculaRepository auswertungJob2CurriculaRepository;

	@Autowired
	AuswertungJob2JobRepository auswertungJob2JobRepository;

	@Autowired
	AuswertungCurricula2CurriculaRepository auswertungCurricula2CurriculaRepository;

	@Transactional
	public void processJob2Curricula() {
		Pageable pageRequest = PageRequest.of(0, 100000, new Sort(Sort.Direction.ASC, "id"));
		while (true) {
			Page<AuswertungJob2CurriculaEntity> auswertungen = auswertungJob2CurriculaRepository.findAll(pageRequest);
			if (auswertungen.getSize() == 0) {
				break;
			}
			for (AuswertungJob2CurriculaEntity auswertung : auswertungen) {
				processAuswertungEntity(auswertung);
				auswertungJob2CurriculaRepository.save(auswertung);
			}
			pageRequest = auswertungen.getPageable().next();
			auswertungJob2CurriculaRepository.flush();
		}
	}

	@Transactional
	public void processJob2Job() {
		Pageable pageRequest = PageRequest.of(0, 100000, new Sort(Sort.Direction.ASC, "id"));
		while (true) {
			Page<AuswertungJob2JobEntity> auswertungen = auswertungJob2JobRepository.findAll(pageRequest);
			if (auswertungen.getSize() == 0) {
				break;
			}
			for (AuswertungJob2JobEntity auswertung : auswertungen) {
				processAuswertungEntity(auswertung);
				auswertungJob2JobRepository.save(auswertung);
			}
			pageRequest = auswertungen.getPageable().next();
			auswertungJob2JobRepository.flush();
		}
	}

	@Transactional
	public void processCurricula2Curricula() {
		Pageable pageRequest = PageRequest.of(0, 100000, new Sort(Sort.Direction.ASC, "id"));
		while (true) {
			Page<AuswertungCurricula2CurriculaEntity> auswertungen = auswertungCurricula2CurriculaRepository
					.findAll(pageRequest);
			if (auswertungen.getSize() == 0) {
				break;
			}
			for (AuswertungCurricula2CurriculaEntity auswertung : auswertungen) {
				processAuswertungEntity(auswertung);
				auswertungCurricula2CurriculaRepository.save(auswertung);
			}
			pageRequest = auswertungen.getPageable().next();
			auswertungCurricula2CurriculaRepository.flush();
		}

	}

	private void processAuswertungEntity(AbstractAuswertungEntity auswertungEntity) {

		AbstractTextEntity text1 = auswertungEntity.getText1();
		AbstractTextEntity text2 = auswertungEntity.getText2();

		LOG.info(auswertungEntity.getInfo());

		double einfacheUebereinstimmung = (new SimilarityEinfacheUebereinstimmung(text1, text2)).calculateSimilarity();
		auswertungEntity.setEinfacheUebereinstimmung(einfacheUebereinstimmung);

		double cosinusKoeffizient = (new SimilarityCosinusKoeffizient(text1, text2)).calculateSimilarity();
		auswertungEntity.setCosinusKoeffizient(cosinusKoeffizient);

		double diceKoeffizient = (new SimilarityDiceKoeffizient(text1, text2)).calculateSimilarity();
		auswertungEntity.setDiceKoeffizient(diceKoeffizient);

		double jaccardKoeffizient = (new SimilarityJaccardKoeffizient(text1, text2)).calculateSimilarity();
		auswertungEntity.setJaccardKoeffizient(jaccardKoeffizient);

		double overlapKoeffizient = (new SimilarityOverlapKoeffizient(text1, text2)).calculateSimilarity();
		auswertungEntity.setOverlapKoeffizient(overlapKoeffizient);

		// LOG.info("similarityEinfacheUebereinstimmung: " + einfacheUebereinstimmung);
		// LOG.info("similarityCosinusKoeffizient: " + cosinusKoeffizient);
		// LOG.info("diceKoeffizient: " + diceKoeffizient);
		// LOG.info("jaccardKoeffizient: " + jaccardKoeffizient);
		// LOG.info("overlapKoeffizient: " + overlapKoeffizient);
	}
}