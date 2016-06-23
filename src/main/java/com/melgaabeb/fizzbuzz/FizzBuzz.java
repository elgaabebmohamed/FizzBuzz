package com.melgaabeb.fizzbuzz;

import com.melgaabeb.fizzbuzz.controler.TransformationEngine;
import com.melgaabeb.fizzbuzz.controler.TransformerFactory;
import com.melgaabeb.fizzbuzz.controler.TransformerType;
import com.melgaabeb.fizzbuzz.model.Line;
import com.melgaabeb.fizzbuzz.view.Board;

public class FizzBuzz 
{
    
    public static void main(String[] args) {

		Line line = new Line.Builder().originalItems(new Integer[]{1, 15, 2, 10, 22, 3}).build();
		
		TransformationEngine engine = new TransformationEngine(
				TransformerFactory.getInstance().create(TransformerType.FIZZER), 
				TransformerFactory.getInstance().create(TransformerType.BUZZER));
		engine.transformLine(line);
		
		Board.getInstance().display(line);
	}
}
