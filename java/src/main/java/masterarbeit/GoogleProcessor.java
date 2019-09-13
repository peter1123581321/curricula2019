package masterarbeit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import masterarbeit.entities.GoogleSearchEntity;
import masterarbeit.entities.GoogleWordUniqueEntity;
import masterarbeit.models.GoogleSearchResult;
import masterarbeit.models.GoogleSearchResultItem;
import masterarbeit.repositories.GoogleSearchRepository;
import masterarbeit.repositories.GoogleWordUniqueRepository;

@Service
public class GoogleProcessor {

	private static Logger LOG = LoggerFactory.getLogger(GoogleProcessor.class);

	@Autowired
	GoogleWordUniqueRepository googleWordUniqueRepository;

	@Autowired
	GoogleSearchRepository googleSearchRepository;

	public void processMissing() {
		Set<Long> ids = googleSearchRepository.findAll().stream().map(a->a.getGoogleWodUniqueEntity().getId()).collect(Collectors.toSet());
		List<GoogleWordUniqueEntity> wordEntities = googleWordUniqueRepository.findByIdNotIn(ids);
		
		for (GoogleWordUniqueEntity wordEntity :wordEntities) {
				processWordEntity(wordEntity);
		}	
		
		LOG.info("count: "+wordEntities.size());
		
	}
	
	public void process() {

		long i = 9999999; // index anpassen

		List<GoogleWordUniqueEntity> wordEntities = googleWordUniqueRepository.findByIdGreaterThanEqual(i);

		for (GoogleWordUniqueEntity wordEntity : wordEntities) {

			processWordEntity(wordEntity);

			i++;
		}
	}
	
	private void processWordEntity(GoogleWordUniqueEntity wordEntity) {
		LOG.info(wordEntity.getId() + ", " + wordEntity.getName());

		long ordnungOffset = 0;
		int startPage = 0;
		int numPages = 1;

		for (int page = startPage; page < numPages; page++) {
			ordnungOffset = processPage(wordEntity, ordnungOffset, page);
		}
	}

	@Transactional
	private long processPage(GoogleWordUniqueEntity wordEntity, long ordnungOffset, int page) {
		List<GoogleSearchResultItem> searchResults = searchPage(page, wordEntity.getName());
		for (GoogleSearchResultItem searchResult : searchResults) {

			GoogleSearchEntity searchEntity = new GoogleSearchEntity(searchResult.getFormattedUrl(),
					searchResult.getLink(), searchResult.getTitle(), wordEntity, ordnungOffset);

			googleSearchRepository.save(searchEntity);
			ordnungOffset++;
		}
		return ordnungOffset;
	}

	private List<GoogleSearchResultItem> searchPage(int pageNumber, String search) {
		
		int retryCount = 0;
		
		while (true) {
		
			try {
				String key = "";
				String engine = "";
	
				int resultsPerPage = 10;
				int offset = pageNumber * resultsPerPage + 1;
	
				String url = "https://www.googleapis.com/customsearch/v1?key=" + key + "&cx=" + engine + "&q=" + search
						+ "&alt=json&start=" + offset;
	
				RestTemplate restTemplate = new RestTemplate();
				GoogleSearchResult result = restTemplate.getForObject(url, GoogleSearchResult.class);
	
				return result.getItems() != null ? result.getItems() : new ArrayList<>();
			} catch (Exception e) {
				if (retryCount==3) {
					LOG.info(e.getMessage());
					LOG.info("skip word");
					return  new ArrayList<>();
				} else {
					LOG.info("error occured ... retry");
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				retryCount++;
			}
		}
		
	}


}