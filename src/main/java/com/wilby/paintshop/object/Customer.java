package com.wilby.paintshop.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer {
	
	private int colour;
	private Finish finish;
	
	private List<Paint> paintPrefs = new ArrayList<Paint>();
	
	public void addPaintPref(int colour, Finish finish) {		
		Paint newPaint = new Paint(colour, finish);
		paintPrefs.add(newPaint);
	}
	
	public List<Paint> getPaintPrefs() {
		return paintPrefs;
	}
	
	public Integer getnumberOfPaints() {
		return paintPrefs.size();
	}
	
	public String toString() {
		return "Customer " + getPaintPrefs();
	}
}
