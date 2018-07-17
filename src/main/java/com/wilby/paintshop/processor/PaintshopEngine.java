package com.wilby.paintshop.processor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wilby.paintshop.object.Customer;
import com.wilby.paintshop.object.Finish;
import com.wilby.paintshop.object.Paint;

public class PaintshopEngine {
	
	private static final String INFEASIBLE = "No solution exists";
	private Map<Integer, String> resultMap = new HashMap<Integer, String>();
	
	private final Comparator<Customer> customerComparator = (c1, c2) ->
    c1.getnumberOfPaints().compareTo(c2.getnumberOfPaints());

    /**
     * returns the sanitized string containing result to main application class
     * @param colours
     * @param custList
     * @return
     */
	public String determineFeasibility(int colours, List<Customer> custList) {
		
		custList.sort(customerComparator);
		
		for (Customer customer : custList) {
			if (customer.getnumberOfPaints() == 1) {
				//Check for cust who only wants one colour
				Paint setPaint = calculateBatch(customer, null);
				if (setPaint == null) {
					return INFEASIBLE;
				}
				resultMap.put(setPaint.getColour(), setPaint.getFinish().getInitial());
			}
			else {
				List<Paint> prospectivePaints = new ArrayList<>();				
				Paint setPaint = calculateBatch(customer, prospectivePaints);
				
				if (setPaint != null) {
					continue;
				}
				else if (prospectivePaints.isEmpty()) {
					return INFEASIBLE;
				}				
				Paint paintDecision = prospectivePaints.get(0);
				for (Paint paint : prospectivePaints) {
					if (paint.getFinish().equals(Finish.GLOSSY)) {
						paintDecision = paint;
					}
				}				
				resultMap.put(paintDecision.getColour(), paintDecision.getFinish().getInitial());
			}
		}
		StringBuilder builder = new StringBuilder();
				
		for (int i = 1; i <= colours; i++) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			String finish = resultMap.get(i);
			if (finish == null) {
				finish = Finish.GLOSSY.getInitial();
			}
		    builder.append(finish);
		}
		
		String finalResult = builder.toString().trim();
		return finalResult;
	}
	
	private Paint calculateBatch(Customer customer, List<Paint> prospectivePaints) {
		
		for (Paint p : customer.getPaintPrefs()) {
			int colour = p.getColour();
			Finish finish = p.getFinish();
			String setOption = resultMap.get(colour);
			
			if (customer.getnumberOfPaints() == 1) {
				if (setOption == null || setOption.equals(finish.getInitial())) {
					return p;
				}
				else {
					return null;
				}
			}
			else {
				if (setOption == null) {
					prospectivePaints.add(p);
				}
				else if (setOption.equals(finish.getInitial())){
					return p;
				}		
			}
		}
		return null;
	}

}
