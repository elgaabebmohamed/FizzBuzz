package com.melgaabeb.fizzbuzz.controler;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

import com.melgaabeb.fizzbuzz.controler.TransformationEngine;
import com.melgaabeb.fizzbuzz.controler.TransformerFactory;
import com.melgaabeb.fizzbuzz.controler.TransformerType;
import com.melgaabeb.fizzbuzz.model.Line;

public class TransformerEngineTest {
	
	private TransformationEngine engine;
	
	@Before
	public void setUp() {
		engine = new TransformationEngine(
				TransformerFactory.getInstance().create(TransformerType.FIZZER), 
				TransformerFactory.getInstance().create(TransformerType.BUZZER));
	}
	
	@Test
	public void WhenAllNumbersAreDividableByThreeThenReturnOnlyFizzS() {
		
		Line line = new Line.Builder().originalItems(new Integer[]{3, 9, 609}).build();
		
		engine.transformLine(line);
		
		assertArrayEquals(new String[] {"Fizz", "Fizz", "Fizz"}, line.getTransformedItems());		
		
	}
	
	@Test
	public void WhenAllNumbersAreDividableByFiveThenReturnOnlyBuzzS() {
		
		Line line = new Line.Builder().originalItems(new Integer[]{5, 10, 100}).build();
		
		engine.transformLine(line);
		
		assertArrayEquals(new String[] {"Buzz", "Buzz", "Buzz"}, line.getTransformedItems());		
		
	}
	
	@Test
	public void WhenAllNumbersAreDividableByThreeAndFiveThenReturnOnlyFizzBuzzS() {
		
		Line line = new Line.Builder().originalItems(new Integer[]{15, 30, 60}).build();
		
		engine.transformLine(line);
		
		assertArrayEquals(new String[] {"Fizz Buzz", "Fizz Buzz", "Fizz Buzz"}, line.getTransformedItems());		
		
	}
	
	@Test
	public void WhenAllNumbersAreNotDividableByThreeNeitherByFiveThenReturnOnlyNumbers() {
		
		Line line = new Line.Builder().originalItems(new Integer[]{1, 2, 22}).build();
		
		engine.transformLine(line);
		
		assertArrayEquals(new String[] {"1", "2", "22"}, line.getTransformedItems());		
		
	}
	
	
	@Test
	public void WhenNumbersAreMixedThenReturnMixedResult() {
		
		Line line = new Line.Builder().originalItems(new Integer[]{1, 15, 2, 10, 22, 3}).build();
		
		engine.transformLine(line);
		
		assertArrayEquals(new String[] {"1", "Fizz Buzz", "2", "Buzz", "22", "Fizz"}, line.getTransformedItems());		
		
	}
	
	@Test
	public void WhenLineIsEmptyThenThrowException() {
		
		Line line = new Line.Builder().originalItems(new Integer[]{}).build();
		
		try {
			engine.transformLine(line);
		} catch (IllegalArgumentException exception) {
		    assert(exception.getMessage().contains("can't transform empty lines!"));
		}
	}

}
