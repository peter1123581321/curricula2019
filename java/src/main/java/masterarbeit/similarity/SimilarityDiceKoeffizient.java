package masterarbeit.similarity;

import masterarbeit.entities.AbstractTextEntity;

public class SimilarityDiceKoeffizient extends AbstractSimilarity {

	public SimilarityDiceKoeffizient(AbstractTextEntity text1, AbstractTextEntity text2) {
		super(text1, text2);
	}

	@Override
	public double calculateSimilarity() {
		float nenner = (getLeftSum()+getRightSum());
		if (nenner==0) {
			return 0;
		} else {
			return getZaehler()/nenner;
		}
	}

	private float getZaehler() {
		return 2*getSumOfProducts();
	}

	private float getLeftSum() {
		return getSum(this.saliencesText1Total);
	}
	
	private float getRightSum() {
		return getSum(this.saliencesText2Total);
	}
}
