package com.wilby.paintshop.object;

public class Paint {
	
	private int colour;
	private Finish finish;

	public Paint(int colour, Finish finish) {
		super();
		this.colour = colour;
		this.finish = finish;
	}

	public int getColour() {
		return colour;
	}

	public void setColour(int colour) {
		this.colour = colour;
	}

	public Finish getFinish() {
		return finish;
	}

	public void setFinish(Finish finish) {
		this.finish = finish;
	}
	
	public String toString() {
		return "Paint[" + colour + "," + finish + "]";
	}	
}
