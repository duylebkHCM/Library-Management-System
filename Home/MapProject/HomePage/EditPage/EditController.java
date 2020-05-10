package Home.MapProject.HomePage.EditPage;

import java.net.URL;
import java.io.*;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXCheckBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class EditController implements Initializable{
	
		 	@FXML
		    private ColorPicker backgroundColor;

		    @FXML
		    private AnchorPane root;

		    @FXML
		    private ColorPicker bookshelfColor;
		    
		    @FXML
		    public JFXCheckBox checkBox;
		    
		    
		    private String colorBG=null, colorBS=null;

		@Override
		public void initialize(URL url, ResourceBundle rb) {
			
			backgroundColor.setStyle("-fx-color-label-visible: false ;");
			bookshelfColor.setStyle("-fx-color-label-visible: false ;");
			
			String line= null;
			    try {
			    	FileReader fileReader =  new FileReader("/Home/File/Binary.txt");
				    BufferedReader bufferedReader = new BufferedReader(fileReader); 
				    line = bufferedReader.readLine(); 
				    bufferedReader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}	                	
			checkBox.setSelected(Boolean.parseBoolean(line));
			
			checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
			        public void changed(ObservableValue<? extends Boolean> ov,
			            Boolean old_val, Boolean new_val) {
			               binary(checkBox.isSelected());
			        }
			});
			backgroundColor.setOnAction(e->{
				Color color = backgroundColor.getValue();			
				colorBG= color.toString();				
		     	colorBGStorage(colorBG);
			});
			bookshelfColor.setOnAction(e->{
				Color color = bookshelfColor.getValue();			
				colorBS= color.toString();
				colorBSStorage(colorBS);
			});
		}	
		
		private void colorBGStorage(String colorBG) {
	        String fileName = "/Home/File/colorBG.txt";
	        
	        try {
	            FileWriter fileWriter =
	                new FileWriter(fileName);
	            BufferedWriter bufferedWriter =
	                new BufferedWriter(fileWriter);
	            if(colorBG != null) {
	            	bufferedWriter.write(colorBG);
	            }

	            bufferedWriter.close();
	        }
	        catch(IOException ex) {
	            System.out.println(
	                "Error writing to file '"
	                + fileName + "'");
	        }
	    }		
		
		private void colorBSStorage(String colorBS) {
	        String fileName = "/Home/File/colorBS.txt";
	        
	        try {
	            FileWriter fileWriter =
	                new FileWriter(fileName);
	            BufferedWriter bufferedWriter =
	                new BufferedWriter(fileWriter);
	            if(colorBS != null) {
	            	bufferedWriter.write(colorBS);
	            }     
		        
	            bufferedWriter.close();
	        }
	        catch(IOException ex) {
	            System.out.println(
	                "Error writing to file '"
	                + fileName + "'");
	        }
	    }		
		private void binary(Boolean bi) {
			String fileName = "/Home/File/Binary.txt";
			 try {
		            FileWriter fileWriter =
		                new FileWriter(fileName);
		            BufferedWriter bufferedWriter =
		                new BufferedWriter(fileWriter);
		            if(bi != null) {
		            	bufferedWriter.write(bi.toString());
		            }     
			        
		            bufferedWriter.close();
		        }
		        catch(IOException ex) {
		            System.out.println(
		                "Error writing to file '"
		                + fileName + "'");
		        }
			 
		}
}

