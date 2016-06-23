package com.melgaabeb.fizzbuzz.controler;

public class Buzzer extends Transformer {

	private final static String BUZZ_LABEL = "Buzz";
	private final static Integer[] BUZZ_DIVIDIBILITY_NUMBERS = new Integer[]{5};
	
	public Buzzer() {
		super();
		setDividibilityNumbers(BUZZ_DIVIDIBILITY_NUMBERS);
	}

	public String transform() {
		return BUZZ_LABEL;
	}
}
