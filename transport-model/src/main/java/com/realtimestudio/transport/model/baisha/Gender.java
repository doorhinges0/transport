package com.realtimestudio.transport.model.baisha;

public enum Gender {
	MALE(1), FEMALE(0);
	
	private int label;
	private Gender(int label){
		this.label = label;
	}
	public int getLabel(){
		return label;
	}
	
	public static Gender getGender(int val){
		switch (val) {
		case 0: return FEMALE;
		case 1: return MALE;
		}
		throw new IllegalArgumentException("no gender for this label: " +  val);
		
	}
	
	

}
