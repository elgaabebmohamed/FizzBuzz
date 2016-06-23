package com.melgaabeb.fizzbuzz.model;

public class Line {
	
	private Integer[] originalItems;
	private String[] transformedItems;
	
	
	public static class Builder {
		
		private Integer[] originalItems;
		
		public Builder originalItems(Integer[] originalItems) {
			this.originalItems = originalItems; 
			return this; 
		}

        public Line build() {
            return new Line(this);
        }
	}

	private Line(Builder builder) {
        this.originalItems = builder.originalItems;     
    }

	public Integer length() {
		return originalItems.length;
	}
	
	public boolean isEmpty() {
		return ((originalItems == null) || (length() == 0));
	}
	
	public Integer[] getOriginalItems() {
		return originalItems;
	}

	public void setOriginalItems(Integer[] items) {
		this.originalItems = items;
	}
	
	public String[] getTransformedItems() {
		return transformedItems;
	}

	public void setTransformedItems(String[] transformedItems) {
		this.transformedItems = transformedItems;
	}

}
