package masterarbeit.similarity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import masterarbeit.entities.AbstractGoogleWordEntity;
import masterarbeit.entities.AbstractTextEntity;
import masterarbeit.entities.GoogleSearchEntity;
import masterarbeit.entities.GoogleWordUniqueEntity;

public abstract class AbstractSimilarity {

	protected Map<Long, Float> saliencesText1;
	protected Map<Long, Float> saliencesText2;
	protected Set<Long> allSearchUniqueIds;
	protected Map<Long, Float> saliencesText1Total;
	protected Map<Long, Float> saliencesText2Total;

	public AbstractSimilarity(AbstractTextEntity text1, AbstractTextEntity text2) {
		saliencesText1 = getUniqueSearchSaliences(getUniqueWordSaliences(text1));				
		saliencesText2 = getUniqueSearchSaliences(getUniqueWordSaliences(text2));
		
		allSearchUniqueIds = getKeys();
		
		saliencesText1Total = getTotalMap(saliencesText1, allSearchUniqueIds);				
		saliencesText2Total = getTotalMap(saliencesText2, allSearchUniqueIds);
	}
	
	public abstract double calculateSimilarity();

	// wie oft kommt das gleiche wort in einem kurs oder einem job mehrfach vor?
	// dokumentieren!
	private Map<GoogleWordUniqueEntity, Float> getUniqueWordSaliences(AbstractTextEntity textEntity) {
		Map<GoogleWordUniqueEntity, Float> uniqueWordSaliences = new HashMap<>();

		@SuppressWarnings("unchecked")
		List<AbstractGoogleWordEntity> googleWordEntities = (List<AbstractGoogleWordEntity>) textEntity
				.getGoogleWordEntities();

		for (AbstractGoogleWordEntity googleWordEntity : googleWordEntities) {
			if (uniqueWordSaliences.containsKey(googleWordEntity.getGoogleWordUniqueEntity())) {

				Float oldSalience = uniqueWordSaliences.get(googleWordEntity.getGoogleWordUniqueEntity());
				Float newSalience = oldSalience + googleWordEntity.getSalience();

				uniqueWordSaliences.put(googleWordEntity.getGoogleWordUniqueEntity(), newSalience);
			} else {
				uniqueWordSaliences.put(googleWordEntity.getGoogleWordUniqueEntity(), googleWordEntity.getSalience());
			}
		}

		return uniqueWordSaliences;
	}

	// wie oft kommt das gleiche suchergebnis in einem kurs oder einem job mehrfach
	// vor? dokumentieren!
	private Map<Long, Float> getUniqueSearchSaliences(Map<GoogleWordUniqueEntity, Float> wordSaliences) {
		Map<Long, Float> uniqueSearchSaliences = new HashMap<>();

		for (Entry<GoogleWordUniqueEntity, Float> wordSalience : wordSaliences.entrySet()) {

			GoogleWordUniqueEntity uniqueWord = wordSalience.getKey();
			Float salience = wordSalience.getValue();

			List<GoogleSearchEntity> googleSearchEntities = uniqueWord.getGoogleSearchEntities();

			for (GoogleSearchEntity googleSearchEntity : googleSearchEntities) {

				Long searchUniqueId = googleSearchEntity.getIdUniqueSearch();

				if (uniqueSearchSaliences.containsKey(searchUniqueId)) {
					uniqueSearchSaliences.put(searchUniqueId, uniqueSearchSaliences.get(searchUniqueId) + salience);
				} else {
					uniqueSearchSaliences.put(searchUniqueId, salience);
				}
			}
		}
		return uniqueSearchSaliences;
	}
	
	private Map<Long, Float> getTotalMap(Map<Long, Float> uniqueSearchSaliences, Set<Long> allSearchUniqueIds) {
		for (Long searchUniqueId : allSearchUniqueIds) {
			if (!uniqueSearchSaliences.containsKey(searchUniqueId)) {
				uniqueSearchSaliences.put(searchUniqueId, 0f);
			}
		}
		return uniqueSearchSaliences;
	}

	private Set<Long> getKeys() {
		Set<Long> searchUniqueIds = new HashSet<>();
		searchUniqueIds.addAll(saliencesText1.keySet());
		searchUniqueIds.addAll(saliencesText2.keySet());
		return searchUniqueIds;
	}
	
	protected float getSum(Map<Long,Float> saliences) {
		float sum = 0;
		for (Long salience : saliences.keySet()) {
			sum+=salience;
		}		
		return sum;
	}
	
	protected float getSumOfProducts() {
		float sum = 0;
		for (Entry<Long, Float> course : this.saliencesText1Total.entrySet()) {
			sum+=course.getValue()*this.saliencesText2Total.get(course.getKey());
		}		
		return sum;
	}
}
