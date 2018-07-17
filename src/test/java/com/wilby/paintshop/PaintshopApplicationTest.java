package com.wilby.paintshop;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class PaintshopApplicationTest{
	
	private final String INFEASIBLE = "No solution exists";

	PaintshopApplication paintShop = new PaintshopApplication();
	ClassLoader classLoader = getClass().getClassLoader();
	String inputFilesFolder = "files" + File.separator;
	
	@Test
	public void test1cus1col_impossible() throws Exception {

		File file = new File(classLoader.getResource(inputFilesFolder 
				+ "2Cus1Col.txt").toURI());
		String output = paintShop.readFile(file.toString());
		
		assertEquals(INFEASIBLE, output);
	}
	
	@Test
	public void test14cus5col_possible() throws Exception {

		File file = new File(classLoader.getResource(inputFilesFolder 
				+ "14Cus5Col.txt").toURI());
		String output = paintShop.readFile(file.toString());
		
		assertEquals("G M G M G", output);
	}
	
	@Test
	public void test2cus2col_possible() throws Exception {

		File file = new File(classLoader.getResource(inputFilesFolder 
				+ "2Cus2Col.txt").toURI());
		String output = paintShop.readFile(file.toString());
		
		assertEquals("M M", output);
	}
	
	@Test
	public void test3cus5col_possible() throws Exception {

		File file = new File(classLoader.getResource(inputFilesFolder 
				+ "3Cus5Col.txt").toURI());
		String output = paintShop.readFile(file.toString());
		
		assertEquals("G G G G M", output);
	}
	
	
}
