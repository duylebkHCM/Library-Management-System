package Home.Controllers;

//import java.io.File;
//import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;


public class ControllerImportingPane {
	@FXML
	ToggleGroup ImportingWay;
	@FXML
	GridPane rootPane;
	@FXML
	RadioButton radbtnList;
	@FXML
	RadioButton radbtnForm;
	@FXML
	Button btnNext;
	@FXML
	public void changeButtonState(ActionEvent event) throws Exception
	{
		btnNext.setDisable(false);
	}
	@FXML
	public void changeScene(ActionEvent event) throws Exception
	{

		String nextScene = new String ("");
		/*if (!radbtnList.isSelected() && !radbtnForm.isSelected())
		{
			
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("errorPanel.fxml"));
			Stage stage = new Stage();
	        stage.setTitle("Error");
	        stage.setScene(new Scene(root,360 , 140));
	        stage.show();
	        
		}
		else 
			*/
		
			if(radbtnList.isSelected())
				{
					nextScene = "importList.fxml";
				}
			else if (radbtnForm.isSelected())
			{
				nextScene = "importForm.fxml";
			};
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(nextScene));
			rootPane.getChildren().setAll(root);
		
		
	}

}
