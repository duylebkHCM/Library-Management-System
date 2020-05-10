package Home;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class AlertBox {
    public static void display(String tittle, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(tittle);
        window.setWidth(300);
        window.setHeight(200);
        window.getIcons().add(new Image("/Home/Image/519791-101_Warning-512.png"));
        Label label = new Label();
        label.setText(message);

        HBox layout = new HBox(100);
        layout.getChildren().add(label);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
