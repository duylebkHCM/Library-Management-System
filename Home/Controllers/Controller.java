package Home.Controllers;


import Home.AlertBox;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    ImageView img1;

    String LogIn;

    @FXML
    //Receive message from scene 1
    public void transferPermit(String _per){
        //Display the message
        LogIn = _per;
    }

    @FXML
    Button btnBorrow;

    @FXML
    Button btnImport;

    @FXML
    Button btnRequest;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    ImageView imgImport;

    @FXML
    Rectangle btnL_Import;

    @FXML
    ImageView imgRequest;

    @FXML
    Rectangle btnL_Request;

    @FXML
    ImageView imgSearch;

    @FXML
    Rectangle btnL_Search;

    @FXML
    ImageView imgLocation;

    @FXML
    Rectangle btnL_Location;

    @FXML
    ImageView imgBorrow;

    @FXML
    Rectangle btnL_Borrow;

    @FXML
    ImageView imgHome;

    @FXML
    Rectangle btnL_Home;

    @FXML
    AnchorPane parent;

    @FXML
    public void onSideMenubtnEnter(MouseEvent e) {
        Button button = (Button)e.getSource();
        Glow glowfx = new Glow();
        glowfx.setLevel(1);
        switch(button.getId()) {
            case "btnImport":
                button.setStyle("-fx-background-color: #004d99");
                btnL_Import.setFill(Color.rgb(255,255,255));
                btnL_Import.setEffect(glowfx);
                imgImport.setEffect(glowfx);
                break;
            case "btnRequest":
                button.setStyle("-fx-background-color: #004d99");
                btnL_Request.setFill(Color.rgb(255,255,255));
                btnL_Request.setEffect(glowfx);
                imgRequest.setEffect(glowfx);
                break;
            case "btnSearch":
                button.setStyle("-fx-background-color: #004d99");
                btnL_Search.setFill(Color.rgb(255,255,255));
                btnL_Search.setEffect(glowfx);
                imgSearch.setEffect(glowfx);
                break;
            case "btnLocation":
                button.setStyle("-fx-background-color: #004d99");
                btnL_Location.setFill(Color.rgb(255,255,255));
                btnL_Location.setEffect(glowfx);
                imgLocation.setEffect(glowfx);
                break;
            case "btnBorrow":
                button.setStyle("-fx-background-color: #004d99");
                btnL_Borrow.setFill(Color.rgb(255,255,255));
                btnL_Borrow.setEffect(glowfx);
                imgBorrow.setEffect(glowfx);
                break;
            default:
                button.setStyle("-fx-background-color: #004d99");
                btnL_Home.setFill(Color.rgb(255,255,255));
                btnL_Home.setEffect(glowfx);
                imgHome.setEffect(glowfx);
        }
    }

    @FXML
    public void onSideMenubtnLeave(MouseEvent e) {
        Button button = (Button)e.getSource();
        Glow glowfx = new Glow();
        glowfx.setLevel(0);
        switch(button.getId()) {
            case "btnImport":
                button.setStyle("-fx-background-color: #0373BD");
                btnL_Import.setFill(Color.rgb(3,115,189));
                btnL_Import.setEffect(glowfx);
                imgImport.setEffect(glowfx);
                break;
            case "btnRequest":
                button.setStyle("-fx-background-color: #0373BD");
                btnL_Request.setFill(Color.rgb(3,115,189));
                btnL_Request.setEffect(glowfx);
                imgRequest.setEffect(glowfx);
                break;
            case "btnSearch":
                button.setStyle("-fx-background-color: #0373BD");
                btnL_Search.setFill(Color.rgb(3,115,189));
                btnL_Search.setEffect(glowfx);
                imgSearch.setEffect(glowfx);
                break;
            case "btnLocation":
                button.setStyle("-fx-background-color: #0373BD");
                btnL_Location.setFill(Color.rgb(3,115,189));
                btnL_Location.setEffect(glowfx);
                imgLocation.setEffect(glowfx);
                break;
            case "btnBorrow":
                button.setStyle("-fx-background-color: #0373BD");
                btnL_Borrow.setFill(Color.rgb(3,115,189));
                btnL_Borrow.setEffect(glowfx);
                imgBorrow.setEffect(glowfx);
                break;
            default:
                button.setStyle("-fx-background-color: #0373BD");
                btnL_Home.setFill(Color.rgb(3,115,189));
                btnL_Home.setEffect(glowfx);
                imgHome.setEffect(glowfx);
        }
    }

    @FXML
    public void onSideMenubtnClick(MouseEvent e) throws Exception {
        Button button = (Button)e.getSource();
        System.out.println(LogIn);
        switch(button.getId()) {
            case "btnImport":
                if(LogIn.matches("false")){
                    AlertBox box = new AlertBox();
                    box.display("Inaccessible","Log in as librarian to access this.");
                }
                if(LogIn.matches("true")) {
                    Parent root = FXMLLoader.load(getClass().getResource("/Home/fxml/importForm.fxml"));/* Exception */
                    parent.getChildren().setAll(root);
    				parent.setTopAnchor(root, 0.0);
    				parent.setBottomAnchor(root, 0.0);
    				parent.setLeftAnchor(root, 0.0);
    				parent.setRightAnchor(root, 0.0);
                }
                break;
            case "btnRequest":
                if(LogIn.matches("false")){
                    AlertBox box = new AlertBox();
                    box.display("Inaccessible","Log in as librarian to access this.");
                }
                if(LogIn.matches("true")) {
                    Parent root = FXMLLoader.load(getClass().getResource("/Home/fxml/applicationReport.fxml"));
                    parent.getChildren().setAll(root);
    				parent.setTopAnchor(root, 0.0);
    				parent.setBottomAnchor(root, 0.0);
    				parent.setLeftAnchor(root, 0.0);
    				parent.setRightAnchor(root, 0.0);
                   
                }
                break;
            case "btnSearch":
            	try {
                    //Load second scene
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home/fxml/demoSearchTool.fxml"));/* Exception */
                    Parent root = loader.load();
                    parent.getChildren().setAll(root);
    				parent.setTopAnchor(root, 0.0);
    				parent.setBottomAnchor(root, 0.0);
    				parent.setLeftAnchor(root, 0.0);
    				parent.setRightAnchor(root, 0.0);
                    //Get controller of scene2
                    myControllerSearch inputController = loader.getController();
                    //Pass whatever data you want. You can have multiple method calls here
                    inputController.transferPermit(LogIn);
                 
                } catch (IOException ex) {
                    System.err.println(ex);
                }
                break;
            case "btnLocation":
                String src = "SideBar/MapHomePage.fxml";
                if(LogIn.matches("false")){
                    src = "Mappage/Map.fxml";
                }
                //stage=(Stage) button.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home/MapProject/HomePage/"+src));
                Parent root = loader.load();                
                parent.getChildren().setAll(root);
				parent.setTopAnchor(root, 0.0);
				parent.setBottomAnchor(root, 0.0);
				parent.setLeftAnchor(root, 0.0);
				parent.setRightAnchor(root, 0.0);
                //scene = new Scene(root);
                //stage.setScene(scene);
                //stage.setTitle("MapGenerator");
                //stage.show();
                break;
            case "btnBorrow":
                if(LogIn.matches("false")){
                    AlertBox box = new AlertBox();
                    box.display("Inaccessible","Log in as librarian to access this.");
                }
                if(LogIn.matches("true")) {
                    root = FXMLLoader.load(getClass().getResource("/Home/fxml/BorrowManage.fxml"));/* Exception */
                    parent.getChildren().setAll(root);
    				parent.setTopAnchor(root, 0.0);
    				parent.setBottomAnchor(root, 0.0);
    				parent.setLeftAnchor(root, 0.0);
    				parent.setRightAnchor(root, 0.0);
                }
                break;
            case "btnHome":
                root = FXMLLoader.load(getClass().getResource("/Home/Login/Select.fxml"));
                parent.getChildren().setAll(root);
				parent.setTopAnchor(root, 0.0);
				parent.setBottomAnchor(root, 0.0);
				parent.setLeftAnchor(root, 0.0);
				parent.setRightAnchor(root, 0.0);
                break;

        }
    }

}
