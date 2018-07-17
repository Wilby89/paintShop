package com.wilby.paintshop.object;

public enum Finish {
	GLOSSY("G"),
	MATTE("M");
	
	private String type;

	Finish(final String type) {
		this.type = type;
	}

	public static Finish getFinish(String type) {
		if("G".equals(type.toUpperCase())){
			return Finish.GLOSSY;
		} else {
			return Finish.MATTE;
		}
	}
	
	public String getInitial() {
		return toString().substring(0,1);
	}
}
