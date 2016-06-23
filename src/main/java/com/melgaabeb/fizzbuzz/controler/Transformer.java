package com.melgaabeb.fizzbuzz.controler;

public abstract class Transformer {

	private Integer[] dividibilityNumbers;
	
	
	public Transformer() {
		super();
		dividibilityNumbers = new Integer[]{};
	}

	abstract public String transform();
	
	public boolean isTransformable(Integer number) {
		boolean isEligible = true;
		
		for (Integer denominator : dividibilityNumbers) {
			isEligible = isEligible && (number % denominator == 0);
		}
		
		return isEligible;
	}
	
	public Integer[] getDividibilityNumbers() {
		return dividibilityNumbers;
	}

	public void setDividibilityNumbers(Integer[] dividibilityNumbers) {
		this.dividibilityNumbers = dividibilityNumbers;
	}

}
