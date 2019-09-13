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

import masterarbeit.entities.GoogleWordJobEntity;
import masterarbeit.entities.JobEntity;
import masterarbeit.repositories.GoogleWordJobRepository;
import masterarbeit.repositories.JobRepository;

@Service
public class JobProcessor {

	private static Logger LOG = LoggerFactory.getLogger(JobProcessor.class);
	
	@Autowired
	JobRepository jobRepository;

	@Autowired
	GoogleWordJobRepository googleWordJobRepository;
	
	@Transactional
	public void process() throws IOException {

		List<JobEntity> jobEntities = jobRepository.findAll();

		LOG.info("size: " + jobEntities.size());

		for (JobEntity jobEntity : jobEntities) {
			
			LOG.info("job id: " + jobEntity.getId());
			
			if (jobEntity.getGoogleWordEntities().size()>0) {
				LOG.info("job already analyzed: "+jobEntity.getGoogleWordEntities().size());
			} else {

				try (LanguageServiceClient language = LanguageServiceClient.create()) {

					String text = jobEntity.getText().toLowerCase();
					Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();

					// Detects the sentiment of the text
					AnalyzeEntitiesResponse response = language.analyzeEntities(doc, EncodingType.UTF8);

					List<Entity> entitiesList = response.getEntitiesList();

					for (Entity entity : entitiesList) {
						LOG.info(entity.getName() + " --> " + entity.getSalience());

						GoogleWordJobEntity googleWordJobEntity = new GoogleWordJobEntity();

						googleWordJobEntity.setName(entity.getName());
						googleWordJobEntity.setSalience(entity.getSalience());
						googleWordJobEntity.setJobId(jobEntity);

						googleWordJobRepository.save(googleWordJobEntity);
					}
				}
			}
		}
	}	
}
