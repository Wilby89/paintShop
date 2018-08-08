package com.wilby.paintshop.processor;

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
	private Map<Integer, Boolean> lockedMap = new HashMap<Integer, Boolean>();
	private List<Customer> custList;
	
	private final Comparator<Customer> customerComparator = (c1, c2) ->
    c1.getnumberOfPaints().compareTo(c2.getnumberOfPaints());

    /**
     * returns the sanitized string containing result to main application class
     * @param colours
     * @param custList
     * @return
     */
	public String determineFeasibility(int colours, List<Customer> custList) {
		
		this.custList = custList;
		custList.sort(customerComparator);
		
		//pre set result map to all gloss
		presetResults(colours);
		
		for (Customer customer : custList) {
			if (customer.getnumberOfPaints() == 1) {
				Paint oneChoice = customer.getPaintPrefs().get(0);
				if (!resultMap.get(oneChoice.getColour()).equals(oneChoice.getFinish().getInitial()) 
						&& lockedMap.containsKey(oneChoice.getColour())) {
					return INFEASIBLE;
				}
				resultMap.put(oneChoice.getColour(), oneChoice.getFinish().getInitial());
				lockedMap.put(oneChoice.getColour(), true);
			} 
			else {
				//If a customer has a matte choice lets scope it out
				if (customer.getMatteColour() != -1) {
					if (lockedMap.containsKey(customer.getMatteColour())) {
						if (resultMap.get(customer.getMatteColour()).equals(Finish.MATTE.getInitial())) {
							//A matte colour has already been locked in here, satisfying the customer
						}
						else if (resultMap.get(customer.getMatteColour()).equals(Finish.GLOSSY.getInitial())) {
							boolean satisfied = false;
							//Less ideal, a glossy has been locked in, lets see if customer can still be satisfied
							if (customer.getMatteAlternatives().size() > 0) {
								for (Paint candidate : customer.getMatteAlternatives()) {
									if (resultMap.get(candidate.getColour()).equals(candidate.getFinish().getInitial())) {
										//Another of the customers preferences have been satisfied so we can breathe a sigh of relief
										satisfied = true;
										break;
									}																	
								}
								if (!satisfied) {
									//Customer couldn't be satisfied
									return INFEASIBLE;
								}	
							}
							else {
								//Customer didn't want anything but matte, solution not feasible
								return INFEASIBLE;
							}
						}
					}
					else {
						determineMattePlacement(customer);		
					}
				} //No matte choice, our work is already done since we prefilled the result map with all glossys
			}			
		}
		
		finalcheckIfCustomersSatisfied();
		
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
	
	private void determineMattePlacement(Customer customer) {
		//Nothing locked yet
		boolean satisfyingPreference = false;
		//use one of our matte aficionados other preferences to avoid the expensive matte
		for (Paint p : customer.getMatteAlternatives()) {
			if (lockedMap.containsKey(p.getColour()) 
					&& resultMap.get(p.getColour()).equals(p.getFinish().getInitial())) {
				//customer already satisfied by another preference
				satisfyingPreference = true;
			}
			else if	(lockedMap.containsKey(p.getColour()) 
				&& !resultMap.get(p.getColour()).equals(p.getFinish().getInitial())) {
				//this is no good, lets try on the next iteration
			}
			else {
				//it's not locked so just enter the preference, don't lock in case a later iteration wants to override
				if (resultMap.get(p.getColour()).equals(p.getFinish().getInitial())) {
					//do nothing
					satisfyingPreference = true;
				}
				else {
					resultMap.put(p.getColour(), p.getFinish().getInitial());
					satisfyingPreference = true;
				}								
			}
		}
		
		if (!satisfyingPreference) {
			//matte alternatives won't satisfy, got to lock in the matte, pocketbook be damned!
			resultMap.put(customer.getMatteColour(), Finish.MATTE.getInitial());
			lockedMap.put(customer.getMatteColour(), true);
		}		
	}
	
	private void finalcheckIfCustomersSatisfied() {
		boolean matteCheck = false;
		for (Customer c : custList) {
			if (c.getMatteColour() != -1 && resultMap.get(c.getMatteColour()).equals(Finish.MATTE.getInitial())) {
				//ok
			}
			else if (c.getMatteColour() == -1) {
				for (Paint p : c.getPaintPrefs()) {
					if (resultMap.get(p.getColour()).equals(p.getFinish().getInitial())) {
						break;
					}
				}
			}
			else {
				for (Paint pa : c.getPaintPrefs()) {
					if (resultMap.get(pa.getColour()).equals(pa.getFinish().getInitial())) {
						matteCheck = true;
					}
				}
				if (!matteCheck) {
					resultMap.put(c.getMatteColour(), Finish.MATTE.getInitial());
				}
			}
					
		}
	}

	private void presetResults(int colours) {
		for (int i = 1; i <= colours; i++) {
			resultMap.put(i, Finish.GLOSSY.getInitial());
		}
	}
}
