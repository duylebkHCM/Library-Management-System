package Home.Controllers;


import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ViewList implements Initializable {

	@FXML
	AnchorPane rootanchor;
	
    @FXML
    public TableView<Borrower> tblInfo;
    @FXML
    public TableColumn<Borrower, Integer> colBookID;

    @FXML
    public TableColumn<Borrower, String> colName;

    @FXML
    public TableColumn<Borrower, String> colID;

    @FXML
    public TableColumn<Borrower, String> colPhone;

    @FXML
    public TableColumn<Borrower, String> colEmail;

    @FXML
    public TableColumn<Borrower, String> colAddress;

    @FXML
    public TableColumn<Borrower, Date> colIssueDate;

    @FXML
    public TableColumn<Borrower, Date> colDueDate;

    @FXML
    public TableColumn<Borrower, String> colStatus;

    @FXML
    public TableColumn<Borrower, String> colIssueCode;

    @FXML
    public TableColumn<Borrower, Date> colReturnDate;

    Date d1 = new Date();
    private ObservableList<Borrower> initBorrower = FXCollections.observableArrayList(new Borrower("None", "None","None", "None", "None", "None", d1 , d1, "None","None", d1) );


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //make sure the property value factory should be exactly same as the e.g getStudentId from your model class
        colBookID.setCellValueFactory(new PropertyValueFactory<>("BookID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("BName"));
        colID.setCellValueFactory(new PropertyValueFactory<>("BID"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colIssueDate.setCellValueFactory(new PropertyValueFactory<>("IssueDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("DueDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));
        colIssueCode.setCellValueFactory(new PropertyValueFactory<>("IssueCode"));
        //firstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
//        //add your data to the table here.
        tblInfo.setItems(initBorrower);
    }

    // add your data here from any source

    @FXML
    ComboBox<String> cbxOptions;

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
        }
    }


    @FXML
    public void onbtnClick(MouseEvent e) throws Exception {
        Button button = (Button)e.getSource();
        String srcString = "ViewBorrowedList.fxml";
        switch(button.getId()) {
            case "btnHome":
                srcString = "Mainpage.fxml";
                try {
                    //Stage stage = (Stage) button.getScene().getWindow();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home/fxml/Mainpage.fxml"));/* Exception */
                    Parent root = loader.load();
                    rootanchor.getChildren().setAll(root);
        	   	 	rootanchor.setTopAnchor(root, 0.0);
        	   	 	rootanchor.setBottomAnchor(root, 0.0);
        	   	 	rootanchor.setLeftAnchor(root, 0.0);
        	   	 	rootanchor.setRightAnchor(root, 0.0);
                    //root1= FXMLLoader.load(getClass().getResource("../../../fxml/Mainpage.fxml"));
                    //Get controller of scene2
                    Controller inputController = loader.getController();
                    //Pass whatever data you want. You can have multiple method calls here
                    inputController.transferPermit("true");/* Exception */
                    //Scene scene = new Scene(root, 900, 600);
                    //stage.setScene(scene);
                    //stage.show();
                } catch (IOException ex) {
                    System.err.println(ex);
                }
                break;
            case "btnReturn":
                srcString = "BorrowManage.fxml";
                //Stage stage=(Stage)button.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/Home/fxml/"+srcString));/* Exception */
                rootanchor.getChildren().setAll(root);
    	   	 	rootanchor.setTopAnchor(root, 0.0);
    	   	 	rootanchor.setBottomAnchor(root, 0.0);
    	   	 	rootanchor.setLeftAnchor(root, 0.0);
    	   	 	rootanchor.setRightAnchor(root, 0.0);
                /*Scene scene = new Scene(root, 900, 600);
                stage.setScene(scene);
                stage.show();*/
                break;
            case "btnSearch":
                ArrayList<Borrower> list = IssueBD.findRecordList(cbxOptions.getValue(), txtSearch.getText(), "0");
                Borrower b = new Borrower();
                ObservableList<Borrower> borrowerList = FXCollections.observableArrayList(list);
                tblInfo.setItems(borrowerList);
                txtSearch.setText("");
                break;
            case "btnStatus":
                break;

        }

    }

}
