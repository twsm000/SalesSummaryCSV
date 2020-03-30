package application;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

import model.entities.SalesSummaryCSV;
import model.exceptions.CreateNewDirectoryException;
import model.exceptions.EmptyCSVException;
import model.exceptions.InvalidDirectoryException;
import model.exceptions.ProductCSVReadedException;

public class Program {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
    	Scanner scan = new Scanner(System.in);
    	System.out.println("Enter the file folder location: ");
    	String directory = scan.nextLine();
    
    	try {
            SalesSummaryCSV sales = new SalesSummaryCSV(directory);
            sales.loadProductsFromCSVFile();
            sales.writeSummaryCSV();
            sales.openOutputDirectory();
    	} catch (
	        InvalidDirectoryException | IOException | 
	        ProductCSVReadedException | EmptyCSVException | 
	        CreateNewDirectoryException e) {
            System.out.println(e.getMessage());
        }
    	finally {
    	    scan.close();        
        }
    }
}
