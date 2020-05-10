package Home.Controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import Home.Controllers.Controller;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SelectController  implements Initializable{

	@FXML
	JFXButton btnReader;

	@FXML
	JFXButton btnlibrarian;

	@FXML
	public AnchorPane mainSelect;

	@Override
	public void initialize(URL url, ResourceBundle rb){

	}

	public void Load(String UI){
		Parent root= null;
		try{
			root = FXMLLoader.load(getClass().getResource(UI));
		} catch(IOException ex) {
			Logger.getLogger(SelectController.class.getName()).log(Level.SEVERE, null, ex);
		}
		mainSelect.getChildren().setAll(root);
		mainSelect.setTopAnchor(root, 0.0);
		mainSelect.setBottomAnchor(root, 0.0);
		mainSelect.setLeftAnchor(root, 0.0);
		mainSelect.setRightAnchor(root, 0.0);
	}
	private void writeState(String binary){
		String fileName = "homebtnsetvisible.txt";

		try {
			FileWriter fileWriter =
					new FileWriter(fileName);
			BufferedWriter bufferedWriter =
					new BufferedWriter(fileWriter);
			if(binary != null) {
				bufferedWriter.write(binary);
			}

			bufferedWriter.close();
		}
		catch(IOException ex) {
			System.out.println(
					"Error writing to file '"
							+ fileName + "'");
		}
	}





	@FXML
	public void onbtnClick(MouseEvent e) throws Exception {
		JFXButton button = (JFXButton)e.getSource();
		String srcString;
		switch(button.getId()) {
			case "buttonR":
				srcString = "Mainpage.fxml";
				writeState("true");
				try {
					//Load second scene
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home/fxml/" + srcString));/* Exception */
					Parent root = loader.load();
					mainSelect.getChildren().setAll(root);
					mainSelect.setTopAnchor(root, 0.0);
					mainSelect.setBottomAnchor(root, 0.0);
					mainSelect.setLeftAnchor(root, 0.0);
					mainSelect.setRightAnchor(root, 0.0);
					//Get controller of scene2
					Controller inputController = loader.getController();
					//Pass whatever data you want. You can have multiple method calls here
					inputController.transferPermit("false");
					//Show scene 2 in new window
					
				} catch (IOException ex) {
					System.err.println(ex);
				}

				break;
			case "buttonL":
				srcString = "LoginPage.fxml";
				writeState("false");
				try {

					//Load second scene
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home/Login/" + srcString));/* Exception */
					Parent root = loader.load();
					
					mainSelect.getChildren().setAll(root);
					mainSelect.setTopAnchor(root, 0.0);
					mainSelect.setBottomAnchor(root, 0.0);
					mainSelect.setLeftAnchor(root, 0.0);
					mainSelect.setRightAnchor(root, 0.0);
					//Get controller of scene2
					//Controller inputController = loader.getController();
					//Pass whatever data you want. You can have multiple method calls here
					//inputController.transferPermit("true");
					//Show scene 2 in new window				
				} catch (IOException ex) {
					System.err.println(ex);
				}

				break;

		}

	}

}


