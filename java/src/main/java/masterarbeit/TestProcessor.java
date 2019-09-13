package masterarbeit;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import masterarbeit.models.GoogleSearchResult;
import masterarbeit.models.GoogleSearchResultItem;

@Service
public class TestProcessor {

	private static Logger LOG = LoggerFactory.getLogger(GoogleProcessor.class);

	public void process(String searchString) {
		List<GoogleSearchResultItem> searchResults = searchPage(0, searchString);
		searchResults.addAll(searchPage(1, searchString));
		for (GoogleSearchResultItem searchResult : searchResults) {
			LOG.info(searchResult.getLink(), searchResult.getTitle());
		}
	}

	private List<GoogleSearchResultItem> searchPage(int pageNumber, String search) {

		int retryCount = 0;

		while (true) {

			try {
				String key = "";
				// String engine = ""; // german
				String engine = ""; // english

				int resultsPerPage = 10;
				int offset = pageNumber * resultsPerPage + 1;

				String url = "https://www.googleapis.com/customsearch/v1?key=" + key + "&cx=" + engine + "&q=" + search
						+ "&alt=json&start=" + offset;

				RestTemplate restTemplate = new RestTemplate();
				GoogleSearchResult result = restTemplate.getForObject(url, GoogleSearchResult.class);

				return result.getItems() != null ? result.getItems() : new ArrayList<>();
			} catch (Exception e) {
				if (retryCount == 3) {
					LOG.info(e.getMessage());
					LOG.info("skip word");
					return new ArrayList<>();
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