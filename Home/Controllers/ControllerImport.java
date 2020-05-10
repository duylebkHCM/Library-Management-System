package Home.Controllers;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControllerImport {
	@FXML
	TextField txtPath;
	@FXML
	Button btnReturn;
	@FXML
	Button btnBrowse;
	
	String Path = "";
	@FXML

	public void chooseFile(ActionEvent event) throws Exception
	{
		Stage stage = (Stage) btnBrowse.getScene().getWindow();
		FileChooser chooser = new FileChooser();
		File file = chooser.showOpenDialog(stage);
		txtPath.setText(file.getAbsolutePath().toString());
		Path = file.getAbsolutePath().toString();
	}
	@FXML
	public void releaseDialog(ActionEvent event) throws Exception
	{
		String name = "Success.fxml";
		if (Path == "")
		{
			name = "NoPath.fxml";
		}
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(name));
        Stage stage = new Stage();
        stage.setTitle("Status");
        stage.setScene(new Scene(root,360 , 140));
        stage.show();
		}
	
	@FXML
	public void clearPath(ActionEvent event)
	{
		txtPath.setText("");
	}

	@FXML
	Button btnHome;

	@FXML
	public void onbtnHomeClick(MouseEvent e) throws Exception {
		String srcString = "Mainpage.fxml";
		Stage stage = (Stage) btnHome.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/Home/fxml/" + srcString));/* Exception */
		Scene scene = new Scene(root, 900, 600);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void onbtnHomeLeave(MouseEvent e) {
		Button button = (Button) e.getSource();
		Glow glowfx = new Glow();
		glowfx.setLevel(0);
		btnHome.setStyle("-fx-background-color: #0373BD");
	}

	@FXML
	public void onbtnHomeEnter(MouseEvent e) {

		Glow glowfx = new Glow();
		glowfx.setLevel(1);
		btnHome.setStyle("-fx-background-color: #004d99");
	}	
}
