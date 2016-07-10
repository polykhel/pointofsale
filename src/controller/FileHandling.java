package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Products;

public class FileHandling {

    private static final String PRODUCTS_PATH_LOCATION = "./resources/database/Products.csv";
    private static final String PRODUCTS_BACKUP_PATH_LOCATION = "./backup/Products.csv";
    private static final String AUDIT_TRAIL_PATH_LOCATION = "./audit_trail.txt";
    private static final String AUDIT_TRAIL_BACKUP_PATH_LOCATION = "./backup/audit_trail.txt";

    //CSV file header
    private static final String FILE_HEADER = "Name,Price,Available";

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    Products pro = new Products();

    public Products readFile(File file) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(file)) {
            String line = scanner.nextLine();
            String[] fields = line.split(",");
            System.arraycopy(fields, 0, pro.heading, 0, fields.length);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                fields = line.split(",");
                if (fields.length <= 3) // less than or equal to 3 columns
                {
                    pro.productList.add(new Products(fields[0], Double.parseDouble(fields[1]), Integer.parseInt(fields[2])));
                } else {
                    System.err.println("Invalid record: " + line);
                }
            }
        }
        return pro;
    }

    public void updateCSV(ArrayList<Products> prod) {
        File file = new File(PRODUCTS_PATH_LOCATION);

        for (int i = 0; i < 2; i++) {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }

            try (PrintWriter writer = new PrintWriter(file.getAbsoluteFile())) {
                writer.print(FILE_HEADER);
                writer.print(NEW_LINE_SEPARATOR);

                for (Products product : prod) {     //for each Products instance named "product" in prod (list)
                    writer.print(product.getProductName());
                    writer.print(COMMA_DELIMITER);
                    writer.print(product.getProductPrice());
                    writer.print(COMMA_DELIMITER);
                    writer.print(product.getProductAvail());
                    writer.print(NEW_LINE_SEPARATOR);
                }
            } catch (FileNotFoundException e) {
                System.err.println("File cannot be opened for writing.");
            }

            file = new File(PRODUCTS_BACKUP_PATH_LOCATION);
        }
    }

    public void writeReceipt(Transaction trans, double paidAmount, double change) {
        File file = new File(String.format("./resources/receipts/receipt_%04d%s", trans.transNumber, ".txt"));
        
        for (int i = 0; i < 2; i++) {
            file.getParentFile().mkdirs();

            try (PrintWriter writer = new PrintWriter(file.getAbsoluteFile())) {
                writer.printf("%38s %n", "PAZADONG PAZADO");
                writer.printf("%37s %n%n", "Antipolo City");
                writer.println("Transaction No: #00" + trans.transNumber);
                writer.printf("%n%1$-30s %2$-25s %3$s%n", "Item Desc", "Price", "Total Price");
                trans.getOrderList().stream().forEach((ord) -> {
                    writer.printf("%1$-30s %2$-18.2f %3$-2s %4$-3d %5$.2f%n", ord.getName(), ord.getPrice(), "x",
                            ord.getQuantity(), ord.getTotalPrice());
                });
                writer.printf("%nItem count: %20d", trans.getOrderList().size());
                writer.printf("%n%nSUBTOTAL: %53.2f", trans.getSubtotal());
                writer.printf("%nVAT: %58.2f", trans.getVATvalue());
                writer.printf("%nTOTAL: %56.2f", trans.getTotalPrice());
                writer.printf("%n%n%nPaid Amount: %50.2f", paidAmount);
                writer.printf("%nChange %56.2f", change);
            } catch (FileNotFoundException e) {
                System.err.println("File cannot be opened for writing.");
            }
            
            file = new File(String.format("./backup/receipt_%04d%s", trans.transNumber, ".txt"));
        }
    }

    public void writeAuditTrail(String line) {
        File file = new File("./audit_trail.txt");
        for (int i = 0; i < 2; i++) {

            file.getParentFile().mkdirs();

            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true)))) {        //the true is the append flag

                writer.println(line);

            } catch (FileNotFoundException e) {
                System.err.println("File cannot be opened for writing.");
            } catch (IOException ex) {
                Logger.getLogger(FileHandling.class.getName()).log(Level.SEVERE, null, ex);
            }

            file = new File("./backup/audit_trail.txt");
        }
    }

    public String readAuditTrail() {
        File file = new File(AUDIT_TRAIL_PATH_LOCATION);
        
        String output = "";
        boolean fileFound = false;
        
        while (!fileFound) {
            try (Scanner scanner = new Scanner(file)) {
                
                while (scanner.hasNextLine()) {
                    output += scanner.nextLine() + " ";
                }
                
                fileFound = true;
                
            } catch (FileNotFoundException e) {
                
                File file2 = new File(AUDIT_TRAIL_BACKUP_PATH_LOCATION);
                try {
                    
                    if (!file2.exists()) {
                        file2.createNewFile();
                    }
                    Files.copy(file2.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Problem in from the reading file.");
                }
                
            }
        }
        return output;
    }

    public void writeInventoryLog(String line) {
        File file = new File("./inventory_log.txt");
        
        for (int i = 0; i < 2; i++) {

            file.getParentFile().mkdirs();

            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true)))) {        //the true is the append flag

                writer.println(line);

            } catch (FileNotFoundException e) {
                System.err.println("File cannot be opened for writing.");
            } catch (IOException ex) {
                Logger.getLogger(FileHandling.class.getName()).log(Level.SEVERE, null, ex);
            }

            file = new File("./backup/inventory_log.txt");
        }
    }
}
