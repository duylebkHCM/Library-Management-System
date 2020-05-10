package Home;

import Home.Controllers.IssueBD;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Resetbox extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

    }

    @FXML
    Button btnYes;

    @FXML
    Button btnNo;


    Label txtMes;

    public void display(String _ID) throws Exception{
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(_ID);
        window.setWidth(300);
        window.setHeight(200);

        //Label txtMes = new Label();
        //txtMes.setText("Book is successfully returned!\n No book is being issued at the moment, would you like to reset borrower list?");
        Parent root = FXMLLoader.load(getClass().getResource("Resetbox.fxml"));
        window.setScene(new Scene(root));
        window.showAndWait();
    }

    @FXML

    public void onbtnClick(MouseEvent e) throws Exception {
        Button btn = (Button)e.getSource();
        Stage stage = (Stage) btn.getScene().getWindow();

        if (btn.getId().matches("btnYes")){
            IssueBD.resetBorrowerList(stage.getTitle());
            System.out.println("onbtn");
            stage.close();
        }
        else {
            stage.close();
        }
    }
}
