package com.wilby.paintshop;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import com.wilby.paintshop.object.Customer;
import com.wilby.paintshop.object.Finish;
import com.wilby.paintshop.processor.PaintshopEngine;

public class PaintshopApplication 
{	
	private List<Customer> custList = new ArrayList<Customer>();
	
	private int colours = 0;
	
    public static void main( String[] args )
    {    		
    		if (args.length < 1)
                throw new IllegalArgumentException("No file in argument array.");
    		
        String fileName = args[0]; 
        PaintshopApplication app = new PaintshopApplication();
        String result = app.readFile(fileName);
        System.out.println(result);
    }
    
	public String readFile(String path) {
		try {
			Scanner fileScanner = new Scanner(new FileReader(path));
			if (fileScanner.hasNextInt()) {
				colours = fileScanner.nextInt();
			}
			fileScanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
			
			while (fileScanner.hasNextLine()) {
				String customerLine = fileScanner.nextLine();
				Customer newCustomer = createCustomerFromFileLine(customerLine);
				custList.add(newCustomer);
	        }						
			fileScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("\nCannot reconcile file name with existing file\n" + e.getMessage());
		} catch (Exception e) {
			System.out.println("\nSystem Error\n" + e.getMessage());
		}
		PaintshopEngine engine = new PaintshopEngine();
		String result = engine.determineFeasibility(colours, custList);
		return result;		
	}
	
 
	private Customer createCustomerFromFileLine(String customerLine) {
		Customer cust = new Customer();
		String[] custDetails = customerLine.split(" ");
		for (int i = 0; i < custDetails.length; i+=2) {
			cust.addPaintPref(Integer.parseInt(custDetails[i]), Finish.getFinish(custDetails[i+1]));
		}
		cust.setMatteStatus();
		cust.setMatteAlternatives();
		return cust;
	}
}
