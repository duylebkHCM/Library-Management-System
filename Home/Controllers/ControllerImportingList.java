package Home.Controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class ControllerImportingList {
	@FXML
	public Button btnBrowse;
	@FXML
	public Button btnImport;
	@FXML
	public  Button btnReturn;
	@FXML
	public TextField txtPath;
	@FXML
	public GridPane nodeL;
	@FXML
	public void fileChooser(ActionEvent event) throws Exception
	{
		FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = 
                new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage stage = (Stage) btnBrowse.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
        txtPath.setText(file.getAbsolutePath().toString());
        }
        if (txtPath.getText().isEmpty()) btnImport.setDisable(true); else btnImport.setDisable(false);
		 /*
		JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File","*.xlsx");
        chooser.addChoosableFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
                  txtPath.setText(chooser.getSelectedFile().getAbsolutePath());
                  Path = chooser.getSelectedFile().getAbsolutePath().toString();
        }*/
		/*Stage stage = (Stage) btnBrowse.getScene().getWindow();
		FileChooser chooser = new FileChooser();
		File file = chooser.showOpenDialog(null);
		txtPath.setText(file.getAbsolutePath().toString());
		Path = file.getAbsolutePath().toString();
		*/
		
	}
	@FXML
	public void openNewStage(ActionEvent event) throws Exception
	{
		/*if (txtPath.getText().isEmpty())
		{
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("errorPanel.fxml"));
			Stage stage = new Stage();
			stage.setScene( new Scene(root,360,140) );
			stage.setTitle("Error");
			stage.show();
			
		}
		else*/
		{
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ImportSuccess.fxml"));
		Stage stage = (Stage) btnImport.getScene().getWindow();
	    stage.setScene(new Scene(root,900 , 600));
		}
	}
	
	@FXML
	public void returnScene(ActionEvent event) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ImportingPane.fxml"));
		Scene preScene = new Scene(root,900,600);
		Stage primaryStage = (Stage) btnReturn.getScene().getWindow();
		primaryStage.setScene(preScene);
	}
	

}
