package masterarbeit.similarity;

import java.util.Map;

import masterarbeit.entities.AbstractTextEntity;

public class SimilarityCosinusKoeffizient extends AbstractSimilarity {

	public SimilarityCosinusKoeffizient(AbstractTextEntity text1, AbstractTextEntity text2) {
		super(text1, text2);
	}

	@Override
	public double calculateSimilarity() {
		float nenner = (getLeftSquare()*getRightSquare());
		if (nenner==0) {
			return 0;
		} else {
			return getZaehler()/nenner;
		}
	}

	private float getZaehler() {
		return getSumOfProducts();
	}

	private float getLeftSquare() {
		return (float) Math.sqrt(getSumOfSquares(this.saliencesText1Total));
	}
	
	private float getRightSquare() {
		return (float) Math.sqrt(getSumOfSquares(this.saliencesText2Total));
	}
	
	private float getSumOfSquares(Map<Long,Float> saliences) {
		float sum = 0;
		for (Long salience : saliences.keySet()) {
			sum+=Math.pow(salience, 2);
		}		
		return sum;
	}
}
