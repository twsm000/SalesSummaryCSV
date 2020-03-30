package model.entities;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.exceptions.CreateNewDirectoryException;
import model.exceptions.EmptyCSVException;
import model.exceptions.InvalidDirectoryException;
import model.exceptions.ProductCSVReadedException;

public class SalesSummaryCSV {

    private static final String INPUT_FILE_NAME = "sales.csv";
    private static final String OUTPUT_FILE_NAME = "summary.csv";    
    private static final int PRODUCT_NAME_INDEX = 0;
    private static final int PRODUCT_VALUE_INDEX = 1;    
    private static final int PRODUCT_QUANTITY_INDEX = 2;    
    private String directory;    
    private List<Sale> sales = new ArrayList<>();

    public SalesSummaryCSV(String directory) throws InvalidDirectoryException {
        this.validateDirectory(directory);	
        this.directory = directory;	
    }

    private void validateDirectory(String directory) throws InvalidDirectoryException {
        File folder = new File(directory);
        if (!folder.exists()) {
            throw new InvalidDirectoryException("The directory '" + folder.getPath() + "' won't exists!");
        }
    }

    public void loadProductsFromCSVFile() 
    throws FileNotFoundException, IOException, 
    ProductCSVReadedException, EmptyCSVException {
        try (BufferedReader reader = newReader()) {
            loadSales(reader);
        }

        validateSales();
    }

    private BufferedReader newReader() throws FileNotFoundException {
        return new BufferedReader(new FileReader(getInputCSVFilePath()));
    }

    private String getInputCSVFilePath() {
        return getDirectory() + INPUT_FILE_NAME;
    }
    
    public String getDirectory() {
        if (!directory.endsWith("\\")) {
            directory += "\\";
        }

        return directory;
    }    

    private void loadSales(BufferedReader reader) throws IOException, ProductCSVReadedException {
        String line = reader.readLine();
        while (line != null) {
            sales.add(parseStringToSale(line));
            line = reader.readLine();
        }
    }
    
    private Sale parseStringToSale(String line) 
    throws ProductCSVReadedException, IOException {
        String name = null;
        Double value = null;
        Integer quantity = null;        
        String[] splitedLine;

        try {
            splitedLine = line.split(";");
            name = splitedLine[PRODUCT_NAME_INDEX].trim();
            value = Double.parseDouble(splitedLine[PRODUCT_VALUE_INDEX].trim());
            quantity = Integer.parseInt(splitedLine[PRODUCT_QUANTITY_INDEX].trim());
            Product product = new Product(name, value);     
            return new Sale(product, quantity);         
        } catch (Exception e) {
            throw new ProductCSVReadedException("An error occurs when tried read the product: " 
                + e.getMessage());
        }
    }    
    
    private void validateSales() throws EmptyCSVException {
        if (sales.size() < 0) {
            throw new EmptyCSVException("Product sales not found. The file is empty!");
        }        
    }
    
    public void writeSummaryCSV() 
    throws IOException, CreateNewDirectoryException {        
        try (BufferedWriter writer = newWriter()) {
            for (Sale sale : sales) {                
                writer.write(getCSVLineFromSale(sale));
                writer.newLine();
            }
        }
    }

    private BufferedWriter newWriter() 
    throws IOException, CreateNewDirectoryException {
        return new BufferedWriter(new FileWriter(getOutputCSVFileName()));
    }
    
    private String getOutputCSVFileName() throws CreateNewDirectoryException {
        return getOutputDirectory() + OUTPUT_FILE_NAME;
    }

    private String getOutputDirectory() throws CreateNewDirectoryException {
        String output = getDirectory() + "out\\";
        createOutputDirectory(output);
        return output;
    }

    private void createOutputDirectory(String output) throws CreateNewDirectoryException {
        File folder = new File(output);
        if (!folder.exists() && !folder.mkdir()) {
            throw new CreateNewDirectoryException("Failed to create the output directory");
        }
    }
    
    private String getCSVLineFromSale(Sale sale) {
        return sale.productName() + ";" + String.format("%.2f", sale.total());
    }
    
    public void openOutputDirectory() {
        try {
            File folder = new File(getOutputDirectory());
            Desktop desktop = Desktop.getDesktop();
            desktop.open(folder);
        } catch (CreateNewDirectoryException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
