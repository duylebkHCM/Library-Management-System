package Home.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ControllerImportForm implements Initializable {
	@FXML
	public Button btnImport;
	@FXML
	public 	Button btnReturn;
@FXML
public TextField txtTitle;
@FXML
public TextField txtAuthor;
@FXML
public TextField txtEdition;
@FXML
public TextField txtQuan;
@FXML
public TextField txtPublisher;
@FXML
public TextField txtPrice;
@FXML
public MenuButton menuCat;
@FXML
public RadioMenuItem itemBusiness;
@FXML
public RadioMenuItem itemComic;
@FXML
public RadioMenuItem itemComputer;
@FXML
public RadioMenuItem itemEdu;
@FXML
public RadioMenuItem itemLit;
@FXML
public RadioMenuItem itemMusic;
@FXML
public RadioMenuItem itemMaga;
@FXML
public RadioMenuItem itemSci;
@FXML
public RadioMenuItem itemSocial;
public int checker = 0;
@FXML
public AnchorPane rootanchor;
@FXML
Button btnHome;

int valid = 1; // if all text fields in valid type, valid == 1, else, valid == 0 and return error
/*
@FXML
public void handle(ActionEvent event) throws Exception
{
	
}

@FXML 
public void changeState(ActionEvent event) throws Exception
{
	if (checker == 0 || txtQuan.getText().isEmpty() || txtTitle.getText().isEmpty()
			|| txtAuthor.getText().isEmpty() || txtPublisher.getText().isEmpty()
			|| txtPrice.getText().isEmpty() || txtEdition.getText().isEmpty())
	{
		btnImport.setDisable(true);
	}
	else btnImport.setDisable(false);
} */

@Override
public void initialize(URL location, ResourceBundle resources) {
	btnReturn.setOnAction(e->{
		String srcString = "importForm.fxml";
	    //Stage stage=(Stage) btnReturn.getScene().getWindow();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/Home/fxml/"+srcString));
			//Scene preScene = new Scene(root,900,600);
			 rootanchor.getChildren().setAll(root);
			 rootanchor.setTopAnchor(root, 0.0);
			 rootanchor.setBottomAnchor(root, 0.0);
			 rootanchor.setLeftAnchor(root, 0.0);
			 rootanchor.setRightAnchor(root, 0.0);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Stage primaryStage = (Stage) btnReturn.getScene().getWindow();
		//primaryStage.setScene(preScene);
	});
	btnHome.setOnAction(e->{
		try {
	        //Load second scene
	        //Stage stage = (Stage) btnHome.getScene().getWindow();
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home/fxml/Mainpage.fxml"));/* Exception */
	        Parent root = loader.load();
	        //Scene scene = new Scene(root, 900, 600);
	        rootanchor.getChildren().setAll(root);
	   	 	rootanchor.setTopAnchor(root, 0.0);
	   	 	rootanchor.setBottomAnchor(root, 0.0);
	   	 	rootanchor.setLeftAnchor(root, 0.0);
	   	 	rootanchor.setRightAnchor(root, 0.0);
	        //Get controller of scene2
	        Controller inputController = loader.getController();
	        //Pass whatever data you want. You can have multiple method calls here
	        inputController.transferPermit("true");

	        //Show scene 2 in new window
	        //stage.setScene(scene);
	        //stage.show();
	    } catch (IOException ex) {
	        System.err.println(ex);
	    }
	});
}

@FXML
public void getCat(ActionEvent event) throws Exception{
	RadioMenuItem rb = (RadioMenuItem) event.getSource();
	if (rb.isSelected()) menuCat.setText(rb.getText());
	checker = 1;
}

@FXML
public void openNewStage(ActionEvent event) throws Exception
{
		if (checker == 0 || txtQuan.getText().isEmpty() || txtTitle.getText().isEmpty()
			|| txtAuthor.getText().isEmpty() || txtPublisher.getText().isEmpty()
			|| txtPrice.getText().isEmpty() || txtEdition.getText().isEmpty())
	{
			String srcString = "errorPanel.fxml";
            
            Parent root = FXMLLoader.load(getClass().getResource("/Home/fxml/"+srcString));/* Exception */
           
		
		Stage stage = new Stage();
		stage.setScene( new Scene(root,360,140) );
		stage.setTitle("Error");
		stage.show();
	}
		else 
		{
		if ( tryParse(txtQuan.getText().toString()) < 0
				|| tryParse(txtPrice.getText().toString()) < 0) this.valid = 0;
			if (this.valid == 1) 
		  {
			Book2 b = new Book2(txtAuthor.getText().toString(), txtTitle.getText().toString(), menuCat.getText().toString()
					, txtPublisher.getText().toString(), tryParse(txtQuan.getText().toString()),
					tryParse(txtPrice.getText().toString()),txtEdition.getText().toString());
			BookDB2 db = new BookDB2();
			
			if (db.importBookForm(b) == 1) 
		
			{ 
				String srcString = "ImportSuccess.fxml";
						Parent root = FXMLLoader.load(getClass().getResource("/Home/fxml/"+srcString));/* Exception */
						rootanchor.getChildren().setAll(root);
				   	 	rootanchor.setTopAnchor(root, 0.0);
				   	 	rootanchor.setBottomAnchor(root, 0.0);
				   	 	rootanchor.setLeftAnchor(root, 0.0);
				   	 	rootanchor.setRightAnchor(root, 0.0);				
				return;
			}
			else
			{								
					String srcString = "importFailed.fxml";
					//tage stage=(Stage) button.getScene().getWindow();
					try {
						Parent root = FXMLLoader.load(getClass().getResource("/Home/fxml/"+srcString));
						rootanchor.getChildren().setAll(root);
				   	 	rootanchor.setTopAnchor(root, 0.0);
				   	 	rootanchor.setBottomAnchor(root, 0.0);
				   	 	rootanchor.setLeftAnchor(root, 0.0);
				   	 	rootanchor.setRightAnchor(root, 0.0);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}/* Exception */		
			  }
		  }
			else {
					String srcString = "importFailed.fxml";
					//tage stage=(Stage) button.getScene().getWindow();
					try {
						Parent root = FXMLLoader.load(getClass().getResource("/Home/fxml/"+srcString));
						rootanchor.getChildren().setAll(root);
				   	 	rootanchor.setTopAnchor(root, 0.0);
				   	 	rootanchor.setBottomAnchor(root, 0.0);
				   	 	rootanchor.setLeftAnchor(root, 0.0);
				   	 	rootanchor.setRightAnchor(root, 0.0);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}/* Exception */
		  }
		  }
		}

public static int tryParse(String text) {
	  try {
	    return Integer.parseInt(text);
	  } catch (NumberFormatException e) {
	    return -1;
	  }
	}


}

