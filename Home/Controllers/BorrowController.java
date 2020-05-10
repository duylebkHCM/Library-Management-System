package Home.Controllers;


import javafx.application.Application;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class BorrowController extends Application {
    @Override
    public void start(Stage primaryStage) {

    }

    @FXML
    ImageView imgView;

    @FXML
    ImageView imgBorrow;

    @FXML
    ImageView imgHome;

    @FXML
    AnchorPane rootanchor;
    @FXML
    public void onMenubtnEnter(MouseEvent e) {
        Button button = (Button)e.getSource();
        Glow glowfx = new Glow();
        glowfx.setLevel(1);
        switch(button.getId()) {
            case "btnView":
                button.setStyle("-fx-background-color: #004d99");
                imgView.setEffect(glowfx);
                break;
            case "btnBorrow":
                button.setStyle("-fx-background-color: #004d99");
                imgBorrow.setEffect(glowfx);
                break;
            case "btnHome":
                button.setStyle("-fx-background-color: #004d99");
                imgHome.setEffect(glowfx);
                break;

        }
    }

    @FXML
    public void onMenubtnLeave(MouseEvent e) {
        Button button = (Button)e.getSource();
        Glow glowfx = new Glow();
        glowfx.setLevel(0);
        switch(button.getId()) {
            case "btnView":
                button.setStyle("-fx-background-color: #0373BD");
                imgView.setEffect(glowfx);
                break;
            case "btnBorrow":
                button.setStyle("-fx-background-color: #0373BD");
                imgBorrow.setEffect(glowfx);
                break;
            case "btnHome":
                button.setStyle("-fx-background-color: #0373BD");
                imgHome.setEffect(glowfx);
                break;
        }
    }

    @FXML
    public void onMenubtnClick(MouseEvent e) throws Exception {
        Button button = (Button)e.getSource();
        String srcString = "BorrowManage.fxml";
        switch(button.getId()) {
            case "btnHome":
                srcString = "Mainpage.fxml";
                try {
                    //Load second scene
                    //Stage stage = (Stage) button.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home/fxml/" + srcString));/* Exception */
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
                break;
            case "btnView":
                srcString = "ViewBorrowedList.fxml";
                //Stage stage=(Stage) button.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/Home/fxml/"+srcString));/* Exception */
                rootanchor.getChildren().setAll(root);
    	   	 	rootanchor.setTopAnchor(root, 0.0);
    	   	 	rootanchor.setBottomAnchor(root, 0.0);
    	   	 	rootanchor.setLeftAnchor(root, 0.0);
    	   	 	rootanchor.setRightAnchor(root, 0.0);
                //Scene scene = new Scene(root, 900, 600);
                //stage.setScene(scene);
                //stage.show();
                break;
            case "btnBorrow":
                srcString = "BorrowBook.fxml";
                //stage=(Stage) button.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("/Home/fxml/"+srcString));/* Exception */
                rootanchor.getChildren().setAll(root);
    	   	 	rootanchor.setTopAnchor(root, 0.0);
    	   	 	rootanchor.setBottomAnchor(root, 0.0);
    	   	 	rootanchor.setLeftAnchor(root, 0.0);
    	   	 	rootanchor.setRightAnchor(root, 0.0);
                //scene = new Scene(root, 900, 600);
                //stage.setScene(scene);
                //stage.show();
                break;
        }


    }

}
