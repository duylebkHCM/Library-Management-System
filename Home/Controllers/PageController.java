package Home.Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PageController implements Initializable{

    @FXML
    private JFXButton SignInbtn;
    
    @FXML
    private AnchorPane root;
    
    @FXML
    private JFXPasswordField passField;

    @FXML
    private JFXTextField textField;
    
    @FXML
    private ImageView inValidUsr;

    @FXML
    private ImageView inValidPass;

    private final int usrTrue=1;
    private final int passTrue=2;
    private final int allFalse=0;
    private final int allTrue=3;

    @Override
    public void initialize(URL url, ResourceBundle rb){
    	String style = new String("-fx-background-color: #ef9a9a; -jfx-focus-color: red; -fx-prompt-text-fill: red;");
    	inValidUsr.setVisible(false);
    	inValidPass.setVisible(false);
    	
    	SignInbtn.setOnAction(e->{
    		try {
				if(getAccess(passField, textField) == allTrue) {
					Parent root1= null;
					try {            	
							//Load second scene
							FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home/fxml/Mainpage.fxml"));/* Exception */
							root1 = loader.load();
					
							//Get controller of scene2
							Controller inputController = loader.getController();
							//Pass whatever data you want. You can have multiple method calls here
							inputController.transferPermit("true"); 						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}           	
					root.getChildren().setAll(root1);
					root.setTopAnchor(root1, 0.0);
					root.setBottomAnchor(root1, 0.0);
					root.setLeftAnchor(root1, 0.0);
					root.setRightAnchor(root1, 0.0);
				}    	
				else {
					try {
						if(getAccess(passField, textField) == usrTrue) {
							//inValidUsr.setVisible(true);
							inValidPass.setVisible(true);
							passField.setStyle(style);   
						}
						else if(getAccess(passField, textField) == passTrue) {
							inValidUsr.setVisible(true);
							textField.setStyle(style);  
							
						}
						else {
							inValidPass.setVisible(true);
							passField.setStyle(style);   
							inValidUsr.setVisible(true);
							textField.setStyle(style);    
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}   			
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	});    	   	
    }
    
    private int getAccess(JFXPasswordField passField,JFXTextField textField) throws IOException{ 
    			InputStream file= this.getClass().getResourceAsStream("/Home/File/usrDB.xlsx");
			  	//FileInputStream file = new FileInputStream(File.toString()); 
	  
	            // Create Workbook instance holding reference to .xlsx file 
	            XSSFWorkbook workbook = new XSSFWorkbook(file); 
	  
	            // Get first/desired sheet from the workbook 
	            XSSFSheet sheet = workbook.getSheetAt(0); 
	  
	            // Iterate through each rows one by one 
	            Iterator<Row> rowIterator = sheet.iterator(); 
	            while (rowIterator.hasNext()) { 
	                XSSFRow row = (XSSFRow)rowIterator.next(); 
	                // For each row, iterate through all the columns 
	                Iterator<Cell> cellIterator = row.cellIterator(); 
	                System.out.println(row.getCell(0).getStringCellValue());
                	System.out.println(row.getCell(1).getStringCellValue());           
	                    if (row.getCell(0).getStringCellValue().equals(textField.getText()) && row.getCell(1).getStringCellValue().equals(passField.getText())){	
	                    	workbook.close();
	        	            file.close(); 
	                    	return allTrue;
	                    }
	                    else {
	                    	if(row.getCell(0).getStringCellValue().equals(textField.getText())) {
	                    		workbook.close();
	            	            file.close(); 
	            	            return usrTrue;
	                    	}	                    		
	                    	else {
	                    		if(row.getCell(1).getStringCellValue().equals(passField.getText())) {
	                    			workbook.close();
		            	            file.close(); 
	                    			return passTrue;
	                    		}
	                    		else continue;	
	                    	}
	                    }
	            }	           
	        	workbook.close();
	            file.close(); 
	            return allFalse;
    }
}
