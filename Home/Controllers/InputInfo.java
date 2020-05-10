package Home.Controllers;


import Home.AlertBox;
import javafx.application.Application;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import Home.Controllers.BookDB;
import Home.Controllers.IssueBD;

public class InputInfo extends Application {
    @Override
    public void start(Stage primaryStage) {

    }

    @FXML
    ImageView imgCancel;

    @FXML
    ImageView imgOkay;

    @FXML
    ImageView imgHome;
    
    @FXML
    AnchorPane rootanchor;

    @FXML
    public void onbtnEnter(MouseEvent e) {
        Button button = (Button)e.getSource();
        Glow glowfx = new Glow();
        glowfx.setLevel(1);
        switch(button.getId()) {
            case "btnOkay":
                button.setStyle("-fx-background-color: #009999");
                imgOkay.setEffect(glowfx);
                break;
            case "btnCancel":
                button.setStyle("-fx-background-color: #009999");
                imgCancel.setEffect(glowfx);
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
            case "btnOkay":
                button.setStyle("-fx-background-color: #33ffcc");
                imgOkay.setEffect(glowfx);
                break;
            case "btnCancel":
                button.setStyle("-fx-background-color: #33ffcc");
                imgCancel.setEffect(glowfx);
                break;
            case "btnHome":
                button.setStyle("-fx-background-color: #33ffcc");
                imgHome.setEffect(glowfx);
                break;
        }
    }

    @FXML
    TextField txtName;

    @FXML
    TextField txtID;

    @FXML
    TextField txtAddress;

    @FXML
    TextField txtPhone;

    @FXML
    TextField txtEmail;

    @FXML
    DatePicker dateBorrow;

    @FXML
    DatePicker dateReturn;

    @FXML
    public void onbtnClick(MouseEvent e) throws Exception {
        Button button = (Button)e.getSource();
        String srcString = "InputInfo.fxml";
        switch(button.getId()) {
            case "btnHome":
                srcString = "BorrowBook.fxml";
                //Stage stage=(Stage) button.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/Home/fxml/"+srcString));/* Exception */
                rootanchor.getChildren().setAll(root);
    	   	 	rootanchor.setTopAnchor(root, 0.0);
    	   	 	rootanchor.setBottomAnchor(root, 0.0);
    	   	 	rootanchor.setLeftAnchor(root, 0.0);
    	   	 	rootanchor.setRightAnchor(root, 0.0);
                /*Scene scene = new Scene(root, 900,600);
                stage.setScene(scene);
                stage.show();*/
                break;
            case "btnOkay":

                if(dateBorrow.getValue() == null || dateReturn.getValue()==null||txtName.getText().replaceFirst("^ *", "").isEmpty()||txtID.getText().replaceFirst("^ *", "").isEmpty()||txtPhone.getText().replaceFirst("^ *", "").isEmpty()||txtEmail.getText().replaceFirst("^ *", "").isEmpty()||txtAddress.getText().replaceFirst("^ *", "").isEmpty()) {
                    AlertBox alertbox = new AlertBox();
                    alertbox.display("Missing information", "Some information is still missing or invalid!");
                }

                if (dateBorrow.getValue().isAfter(dateReturn.getValue())&&dateBorrow.getValue() != null && dateReturn.getValue()!=null) {
                    AlertBox alertbox = new AlertBox();
                    alertbox.display("Invalid information", "Due Date is invalid!");
                }
                else {
                    BookDB.issue_return(txtBookID.getText(), "Issue");
                    Borrower _record = new Borrower();
                    _record.Name = txtName.getText();
                    _record.ID = txtID.getText();
                    _record.Phone = txtPhone.getText();
                    _record.Email = txtEmail.getText();
                    _record.Address = txtAddress.getText();
                    _record.BookID = txtBookID.getText();

                    LocalDate date1 = dateBorrow.getValue();
                    Instant instant1 = Instant.from(date1.atStartOfDay(ZoneId.systemDefault()));
                    _record.IssueDate = Date.from(instant1);

                    LocalDate date2 = dateReturn.getValue();
                    Instant instant2 = Instant.from(date2.atStartOfDay(ZoneId.systemDefault()));
                    _record.DueDate = Date.from(instant2);

                    int code = IssueBD.addRecord(txtBookID.getText(), _record);

                    AlertBox alertbox = new AlertBox();
                    alertbox.display("Successful", "Issue Code is " + txtBookID.getText()+"_"+ code);
                    srcString = "BorrowBook.fxml";
                    //stage=(Stage) button.getScene().getWindow();
                    root = FXMLLoader.load(getClass().getResource("/Home/fxml/"+srcString));/* Exception */
                    rootanchor.getChildren().setAll(root);
        	   	 	rootanchor.setTopAnchor(root, 0.0);
        	   	 	rootanchor.setBottomAnchor(root, 0.0);
        	   	 	rootanchor.setLeftAnchor(root, 0.0);
        	   	 	rootanchor.setRightAnchor(root, 0.0);
                    /*scene = new Scene(root, 900,600);
                    stage.setScene(scene);
                    stage.show();*/
                }
                break;
            case "btnCancel":
                txtName.setText("");
                txtAddress.setText("");
                txtEmail.setText("");
                txtID.setText("");
                txtPhone.setText("");
                break;

        }




    }

    @FXML
     TextField txtBookID;

    @FXML
    //Receive message from scene 1
    public void transferID(String ID){
        //Display the message
        txtBookID.setText(ID);
    }

}
