package masterarbeit.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractAuswertungEntity {

	@Column(name="einfache_uebereinstimmung")
	private double einfacheUebereinstimmung;
	
	@Column(name="cosinus_koeffizient")
	private double cosinusKoeffizient;
	
	@Column(name="dice_koeffizient")
	private double diceKoeffizient;
	
	@Column(name="jaccard_koeffizient")
	private double jaccardKoeffizient;
	
	@Column(name="overlap_koeffizient")
	private double overlapKoeffizient;
	
	public double getEinfacheUebereinstimmung() {
		return einfacheUebereinstimmung;
	}

	public void setEinfacheUebereinstimmung(double einfacheUebereinstimmung) {
		this.einfacheUebereinstimmung = einfacheUebereinstimmung;
	}

	public double getCosinusKoeffizient() {
		return cosinusKoeffizient;
	}

	public void setCosinusKoeffizient(double cosinusKoeffizient) {
		this.cosinusKoeffizient = cosinusKoeffizient;
	}

	public double getDiceKoeffizient() {
		return diceKoeffizient;
	}

	public void setDiceKoeffizient(double diceKoeffizient) {
		this.diceKoeffizient = diceKoeffizient;
	}

	public double getJaccardKoeffizient() {
		return jaccardKoeffizient;
	}

	public void setJaccardKoeffizient(double jaccardKoeffizient) {
		this.jaccardKoeffizient = jaccardKoeffizient;
	}

	public double getOverlapKoeffizient() {
		return overlapKoeffizient;
	}

	public void setOverlapKoeffizient(double overlapKoeffizient) {
		this.overlapKoeffizient = overlapKoeffizient;
	}
	
	public abstract AbstractTextEntity getText1();
	
	public abstract AbstractTextEntity getText2();
	
	public abstract String getInfo();
}