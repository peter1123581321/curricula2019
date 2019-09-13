package masterarbeit;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.cloud.language.v1.AnalyzeEntitiesResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.EncodingType;
import com.google.cloud.language.v1.Entity;
import com.google.cloud.language.v1.LanguageServiceClient;

import masterarbeit.entities.CurriculaEntity;
import masterarbeit.entities.GoogleWordCurriculaEntity;
import masterarbeit.repositories.CurriculaRepository;
import masterarbeit.repositories.GoogleWordCurriculaRepository;

@Service
public class CurriculaProcessor {

	private static Logger LOG = LoggerFactory.getLogger(CurriculaProcessor.class);

	@Autowired
	CurriculaRepository curriculaRepository;

	@Autowired
	GoogleWordCurriculaRepository googleWordCurriculaRepository;
	
	@Transactional
	public void process() throws IOException {

		List<CurriculaEntity> curriculaEntities = curriculaRepository.findAll();

		LOG.info("size: " + curriculaEntities.size());

		for (CurriculaEntity curriculaEntity : curriculaEntities) {

			LOG.info("curricula id: " + curriculaEntity.getId());
			
			if (curriculaEntity.getGoogleWordEntities().size()>0) {
				LOG.info("curricula already analyzed: "+curriculaEntity.getGoogleWordEntities().size());
			} else {

				try (LanguageServiceClient language = LanguageServiceClient.create()) {

					String text = curriculaEntity.getText().toLowerCase();
					Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();

					// Detects the sentiment of the text
					AnalyzeEntitiesResponse response = language.analyzeEntities(doc, EncodingType.UTF8);

					List<Entity> entitiesList = response.getEntitiesList();

					for (Entity entity : entitiesList) {
						LOG.info(entity.getName() + " --> " + entity.getSalience());

						GoogleWordCurriculaEntity googleWordCurriculaEntity = new GoogleWordCurriculaEntity();

						googleWordCurriculaEntity.setName(entity.getName());
						googleWordCurriculaEntity.setSalience(entity.getSalience());
						googleWordCurriculaEntity.setCurriculaId(curriculaEntity);

						googleWordCurriculaRepository.save(googleWordCurriculaEntity);
					}


				}
			}
		}
	}
}
