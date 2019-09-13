package masterarbeit.similarity;

import java.util.Map.Entry;

import masterarbeit.entities.AbstractTextEntity;

public class SimilarityOverlapKoeffizient extends AbstractSimilarity {

	public SimilarityOverlapKoeffizient(AbstractTextEntity text1, AbstractTextEntity text2) {
		super(text1, text2);
	}

	@Override
	public double calculateSimilarity() {
		float nenner = getNenner();
		if (nenner == 0) {
			return 0;
		} else {
			return getZaehler() / nenner;
		}
	}

	private float getZaehler() {
		float sum = 0;
		for (Entry<Long, Float> course : this.saliencesText1Total.entrySet()) {
			sum+=Math.min(course.getValue(), this.saliencesText2Total.get(course.getKey()));
		}
		return sum;
	}

	private float getNenner() {
		return Math.min(this.getSum(saliencesText1Total), this.getSum(saliencesText2Total));
	}
}