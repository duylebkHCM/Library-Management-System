package Home.Controllers;

import Home.AlertBox;
import javafx.application.Application;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import Home.Controllers.Book;
import Home.Controllers.BookDB;

import java.io.IOException;

public class BorrowBook extends Application {
    @Override
    public void start(Stage primaryStage) {

    }

    @FXML
    TextField txtSearch;

    @FXML
    ImageView imgReturn;

    @FXML
    ImageView imgHome;



    @FXML
    public void onbtnEnter(MouseEvent e) {
        Button button = (Button)e.getSource();
        Glow glowfx = new Glow();
        glowfx.setLevel(1);
        switch(button.getId()) {
            case "btnSearch":
                //Instantiating the Shadow class
                DropShadow dropShadow = new DropShadow();
                //dropShadow.setBlurType(BlurType.GAUSSIAN);
                dropShadow.setColor(Color.LIGHTGRAY);
                dropShadow.setHeight(20);
                dropShadow.setWidth(20);
                dropShadow.setRadius(5);
                dropShadow.setSpread(10);
                button.setEffect(dropShadow);
                break;
            case "btnReturn":
                button.setStyle("-fx-background-color: #009999");
                imgReturn.setEffect(glowfx);
                break;
            case "btnHome":
                button.setStyle("-fx-background-color: #009999");
                imgHome.setEffect(glowfx);
                break;
            case "btnIssue":
                button.setStyle("-fx-background-color: #004d99;" + "-fx-border-color: #ffffff;" + "-fx-border-width: 2");
                break;
            case "btnReturnbook":
                button.setStyle("-fx-border-width: 2;" + "-fx-background-color: #004d99;" + "-fx-border-color: #ffffff");

                break;

        }
    }

    @FXML
    public void onbtnLeave(MouseEvent e) {
        Button button = (Button)e.getSource();
        Glow glowfx = new Glow();
        glowfx.setLevel(0);
        switch(button.getId()) {
            case "btnSearch":
               button.setEffect(null);
                break;
            case "btnReturn":
                button.setStyle("-fx-background-color: #33ffcc");
                imgReturn.setEffect(glowfx);
                break;
            case "btnHome":
                button.setStyle("-fx-background-color: #33ffcc");
                imgHome.setEffect(glowfx);
                break;
            case "btnIssue":
                button.setStyle("-fx-background-color: #0373BD;"+ "-fx-border-color: #ffffff;" + "-fx-border-width: 2");
                break;
            case "btnReturnbook":
                button.setStyle("-fx-background-color: #0373BD;" + "-fx-border-color: #ffffff;" + "-fx-border-width: 2");
                break;
        }
    }

    @FXML
    TextField txtTittle;

    @FXML
    TextField txtAuthor;

    @FXML
    TextField txtVersion;

    @FXML
    TextField txtID;

    @FXML
    TextField txtGenre;

    @FXML
    TextField txtPYear;

    @FXML
    Button btnIssue;

    @FXML
    Button btnReturnbook;
    
    @FXML
    AnchorPane rootanchor;

    boolean t = false;

    @FXML
    public void onbtnClick(MouseEvent e) throws Exception {
        Button button = (Button)e.getSource();
        String srcString;
        switch(button.getId()) {
            case "btnHome":
                try {
                    //Load second scene
                    //Stage stage = (Stage) button.getScene().getWindow();
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
                   // stage.setScene(scene);
                    //stage.show();
                } catch (IOException ex) {
                    System.err.println(ex);
                }
                break;
            case "btnReturn":

                srcString = "BorrowManage.fxml";
               // Stage stage = (Stage) button.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/Home/fxml/" + srcString));/* Exception */
                rootanchor.getChildren().setAll(root);
    	   	 	rootanchor.setTopAnchor(root, 0.0);
    	   	 	rootanchor.setBottomAnchor(root, 0.0);
    	   	 	rootanchor.setLeftAnchor(root, 0.0);
    	   	 	rootanchor.setRightAnchor(root, 0.0);
                //Scene scene = new Scene(root, 900, 600);
                //stage.setScene(scene);
                //stage.show();
                break;
            case "btnSearch":
                Book result = new Book();
                boolean found = false;
                result = BookDB.searchID(txtSearch.getText());


                txtTittle.setText(result.Title);
                txtAuthor.setText(result.Author);
                txtGenre.setText(result.Category);
                txtVersion.setText(result.Edition);
                txtID.setText(result.ID+"");
                txtPYear.setText(result.Publisher);

                boolean isFound = true;

                if(txtTittle.getText().isEmpty()) {
                    isFound = false;
                    btnIssue.setDisable(true);
                    btnReturnbook.setDisable(true);
                }
                else {
                    btnIssue.setDisable(false);
                    btnReturnbook.setDisable(false);
                }

                if (isFound&&result.Available_quantity == 0) {
                    btnIssue.setDisable(true);
                    btnReturnbook.setDisable(false);
                }
                if (isFound&&result.Available_quantity == result.Total_quantity) {
                    btnReturnbook.setDisable(true);
                    btnIssue.setDisable(false);
                }

                if (!isFound){
                    AlertBox box = new AlertBox();
                    box.display("Not Found", "No book in database has matching ID.");
                }


                break;
            case "btnIssue":
                srcString = "InputInfo.fxml";
                /*stage = (Stage) button.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("../fxml/" + srcString));/* Exception */
                /*scene = new Scene(root, 900, 600);
                stage.setScene(scene);
                stage.show();*/

                try {
                    //Load second scene
                    //stage = (Stage) button.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home/fxml/" + srcString));/* Exception */
                    root = loader.load();
                    //scene = new Scene(root, 900, 600);
                    rootanchor.getChildren().setAll(root);
        	   	 	rootanchor.setTopAnchor(root, 0.0);
        	   	 	rootanchor.setBottomAnchor(root, 0.0);
        	   	 	rootanchor.setLeftAnchor(root, 0.0);
        	   	 	rootanchor.setRightAnchor(root, 0.0);
                    //Get controller of scene2
                    InputInfo inputController = loader.getController();
                    //Pass whatever data you want. You can have multiple method calls here
                    inputController.transferID(txtID.getText());

                    //Show scene 2 in new window
                    //stage.setScene(scene);
                    //stage.show();
                } catch (IOException ex) {
                    System.err.println(ex);
                }

                break;
            case "btnReturnbook":
                srcString = "ReturnBook.fxml";

                try {
                    //Load second scene
                    //stage = (Stage) button.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home/fxml/" + srcString));/* Exception */
                    root = loader.load();
                    //scene = new Scene(root, 900, 600);
                    rootanchor.getChildren().setAll(root);
        	   	 	rootanchor.setTopAnchor(root, 0.0);
        	   	 	rootanchor.setBottomAnchor(root, 0.0);
        	   	 	rootanchor.setLeftAnchor(root, 0.0);
        	   	 	rootanchor.setRightAnchor(root, 0.0);
                    //Get controller of scene2
                    ReturnBook returnController = loader.getController();
                    //Pass whatever data you want. You can have multiple method calls here
                    returnController.transferID(txtID.getText());

                    //Show scene 2 in new window
                   // stage.setScene(scene);
                    //stage.show();
                } catch (IOException ex) {
                    System.err.println(ex);
                }
                break;
        }

    }

}
