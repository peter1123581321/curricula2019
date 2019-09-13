package masterarbeit.similarity;

import masterarbeit.entities.AbstractTextEntity;

public class SimilarityEinfacheUebereinstimmung extends AbstractSimilarity {
	
	public SimilarityEinfacheUebereinstimmung(AbstractTextEntity text1, AbstractTextEntity text2) {
		super(text1, text2);
	}

	@Override
	public double calculateSimilarity() {
		return getSumOfProducts();
	}
}