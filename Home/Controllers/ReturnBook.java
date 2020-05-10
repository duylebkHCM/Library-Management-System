package Home.Controllers;

import Home.Resetbox;
import Home.AlertBox;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ReturnBook extends Application {
    @Override
    public void start(Stage primaryStage) {

    }
    @FXML
    AnchorPane rootanchor;
    
    @FXML
    ImageView imgCancel;

    @FXML
    ImageView imgOkay;

    @FXML
    ImageView imgHome;


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
    TextField txtIssue_Code;

    @FXML
    DatePicker dateReturn;

    @FXML
    public void onbtnClick(MouseEvent e) throws Exception {
        Button button = (Button)e.getSource();
        String srcString = "ReturnBook.fxml";
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

                if(dateReturn.getValue()==null||txtIssue_Code.getText().replaceFirst("^ *", "").isEmpty()) {
                    AlertBox alertbox = new AlertBox();
                    alertbox.display("Missing information", "Some information is still missing!");
                }
                else {

                    int isReturned = IssueBD.updateStatus(txtBookID.getText(), txtIssue_Code.getText(), dateReturn.getValue());

                    if(isReturned != 0 && isReturned != -1) {
                        int total_book =  BookDB.issue_return(txtBookID.getText(), "Return");
                        System.out.println(IssueBD.findRecordList("Status", "Issued", txtBookID.getText()));
                        if(IssueBD.findRecordList("Status", "Issued", txtBookID.getText()).isEmpty() ){

                            Resetbox box = new Resetbox();
                            box.display(txtBookID.getText());
                        }
                        else {
                            AlertBox box = new AlertBox();
                            box.display("Return Book", "Book is successfully returned!");
                        }

                    } else {
                        if (isReturned == -1) {
                            AlertBox box = new AlertBox();
                            box.display("Return Book", "Book has already been returned!");
                        }
                        else {
                            AlertBox box = new AlertBox();
                            box.display("Error", "This Issue code doesn't exist");
                        }
                    }
                    srcString = "BorrowBook.fxml";
                    //stage=(Stage) button.getScene().getWindow();
                    root = FXMLLoader.load(getClass().getResource("/Home/fxml/"+srcString));/* Exception */
                    rootanchor.getChildren().setAll(root);
        	   	 	rootanchor.setTopAnchor(root, 0.0);
        	   	 	rootanchor.setBottomAnchor(root, 0.0);
        	   	 	rootanchor.setLeftAnchor(root, 0.0);
        	   	 	rootanchor.setRightAnchor(root, 0.0);
                    //scene = new Scene(root, 900,600);
                    //stage.setScene(scene);
                    //stage.show();
                }
                break;
            case "btnCancel":
                txtIssue_Code.setText("");
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
