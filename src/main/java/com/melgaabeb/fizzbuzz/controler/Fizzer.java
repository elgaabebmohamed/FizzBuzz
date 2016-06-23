package com.melgaabeb.fizzbuzz.controler;

public class Fizzer extends Transformer {

	private final static String FIZZ_LABEL = "Fizz";
	private final static Integer[] FIZZ_DIVIDIBILITY_NUMBERS = new Integer[]{3};
	
	public Fizzer() {
		super();
		setDividibilityNumbers(FIZZ_DIVIDIBILITY_NUMBERS);
	}

	public String transform() {
		return FIZZ_LABEL;
	}
}
