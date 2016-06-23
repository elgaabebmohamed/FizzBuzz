package com.melgaabeb.fizzbuzz.controler;



public final class TransformerFactory {

	private static final TransformerFactory INSTANCE = new TransformerFactory();
	
	private TransformerFactory(){
	}
	
	public static TransformerFactory getInstance(){
		return INSTANCE;
	}
	
	public Transformer create(TransformerType transformerType) {
		
		Transformer transformer = null;
		
		switch (transformerType) {
		case BUZZER :
			transformer = new Buzzer(); 
			break;
		case FIZZER :
			transformer = new Fizzer(); 
			break;
		}
		
		return transformer;
	}
}
