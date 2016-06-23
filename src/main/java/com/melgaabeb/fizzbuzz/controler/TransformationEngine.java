package com.melgaabeb.fizzbuzz.controler;

import java.util.ArrayList;
import java.util.List;

import com.melgaabeb.fizzbuzz.model.Line;

public class TransformationEngine {
	
	private final static String SEPARATOR = " ";
	
	private List<Transformer> transformers;
	
	public TransformationEngine(Transformer ... tranformers) {
		
		transformers = new ArrayList<Transformer>();
		
		for (Transformer transformer : tranformers) {
			transformers.add(transformer);
		}
	}
	
	public void transformLine(Line line) {
		
		if (line == null || line.isEmpty()) 
			throw new IllegalArgumentException("can't transform empty lines!");

		List<String> transformedItems = new ArrayList<String>();
		
    	for (Integer number : line.getOriginalItems()) {

    		String transformedItem = "";

    		transformedItem = transformNumber(number);

    		if (transformedItem.toString().isEmpty()) 
    			transformedItems.add("" + number);
    		else 
    			transformedItems.add(transformedItem.toString().trim());

    	}

    	line.setTransformedItems(transformedItems.toArray(new String[line.length()]));
	}
	
	private String transformNumber(Integer number) {
		
		StringBuilder transformedItem = new StringBuilder();
		
		for (Transformer transformer : transformers) {
			if (transformer.isTransformable(number))
				transformedItem.append(transformer.transform() + SEPARATOR);
		}
		
		return transformedItem.toString();
		
	}

	public List<Transformer> getTransformers() {
		return transformers;
	}

	public void setTransformers(List<Transformer> transformers) {
		this.transformers = transformers;
	}
	
	

}
