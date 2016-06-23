package com.melgaabeb.fizzbuzz.view;


import com.melgaabeb.fizzbuzz.model.Line;

public final class Board implements Displayer {
	
	private static final Board INSTANCE = new Board();
	private static final String ITEMS_SEPARATOR = ", ";
	
	public static Board getInstance() {
		return INSTANCE;
	}

	public void display(Line line) {
		
		for (String item : line.getTransformedItems()) {
			System.out.print(item + ITEMS_SEPARATOR);
		}
		
	}

}
