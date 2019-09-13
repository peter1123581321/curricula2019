package masterarbeit.similarity;

import masterarbeit.entities.AbstractTextEntity;

public class SimilarityJaccardKoeffizient extends AbstractSimilarity {

	public SimilarityJaccardKoeffizient(AbstractTextEntity text1, AbstractTextEntity text2) {
		super(text1, text2);
	}

	@Override
	public double calculateSimilarity() {
		float nenner = (getFirstSum()+getSecondSum()-getThirdSum());
		if (nenner==0) {
			return 0;
		} else {
			return getZaehler()/nenner;
		}
	}

	private float getZaehler() {
		return getSumOfProducts();
	}

	private float getFirstSum() {
		return getSum(this.saliencesText1Total);
	}

	private float getSecondSum() {
		return getSum(this.saliencesText2Total);
	}
	
	private float getThirdSum() {
		return getSumOfProducts();
	}

}
