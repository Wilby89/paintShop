package com.wilby.paintshop.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer {
	
	private int matteColour = -1;
	
	private List<Paint> paintPrefs = new ArrayList<Paint>();
	private List<Paint> matteAlternatives = new ArrayList<Paint>();
	
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
	
	public void setMatteStatus() {
		for (Paint p : getPaintPrefs()) {
			if (p.getFinish().equals(Finish.MATTE)) {
				matteColour = p.getColour();
			}
		}
	}
	
	public int getMatteColour() {
		return matteColour;
	}
	
	public void setMatteAlternatives() {
		if (matteColour != -1) {
			for (Paint p : getPaintPrefs()) {
				if (!p.getFinish().equals(Finish.MATTE)) {
					matteAlternatives.add(p);
				}
			}
		}		
	}
	
	public List<Paint> getMatteAlternatives() {
		return matteAlternatives;
	}
}
