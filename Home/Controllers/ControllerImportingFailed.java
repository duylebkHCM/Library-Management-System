package Home.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ControllerImportingFailed implements Initializable {
	
@FXML
Button btnBack;

@FXML
AnchorPane rootPane;

/*@FXML
public void changeScene(ActionEvent event) throws Exception
{
	String srcString = "importForm.fxml";
    Stage stage=(Stage) btnBack.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("../fxml/"+srcString));
    Scene scene = new Scene(root, 900, 600);
    stage.setScene(scene);
    stage.show();
	
}*/
@Override
public void initialize(URL location, ResourceBundle resources) {	
	btnBack.setOnAction(e->{
		String srcString = "importForm.fxml";
		//tage stage=(Stage) button.getScene().getWindow();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/Home/fxml/"+srcString));
			rootPane.getChildren().setAll(root);
			rootPane.setTopAnchor(root, 0.0);
			rootPane.setBottomAnchor(root, 0.0);
			rootPane.setLeftAnchor(root, 0.0);
			rootPane.setRightAnchor(root, 0.0);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}/* Exception */
	});
}
}