package masterarbeit.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleSearchResult {

	private List<GoogleSearchResultItem> items;

	public List<GoogleSearchResultItem> getItems() {
		return items;
	}

	public void setItems(List<GoogleSearchResultItem> items) {
		this.items = items;
	}

}
