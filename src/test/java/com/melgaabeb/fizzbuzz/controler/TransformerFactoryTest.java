package com.melgaabeb.fizzbuzz.controler;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.melgaabeb.fizzbuzz.controler.Buzzer;
import com.melgaabeb.fizzbuzz.controler.Fizzer;
import com.melgaabeb.fizzbuzz.controler.Transformer;
import com.melgaabeb.fizzbuzz.controler.TransformerFactory;
import com.melgaabeb.fizzbuzz.controler.TransformerType;

public class TransformerFactoryTest {
	
	private TransformerFactory transformerFactory;
	
	@Before
	public void setUp() {
		transformerFactory = TransformerFactory.getInstance();
	}

	@Test
	public void whenCreateFizzerReturnInstanceOfFizzer() {
		
		Transformer fizzerTransformer = TransformerFactory.getInstance().create(TransformerType.FIZZER);
		assertTrue(fizzerTransformer instanceof Fizzer);
	}
	
	@Test
	public void whenCreateBuzzerReturnInstanceOfBuzzer() {
		
		Transformer buzzerTransformer = TransformerFactory.getInstance().create(TransformerType.BUZZER);
		assertTrue(buzzerTransformer instanceof Buzzer);
	}
}
