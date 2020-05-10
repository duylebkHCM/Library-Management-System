package Home;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setHeight(630);
        primaryStage.setWidth(900);
        Parent root = FXMLLoader.load(getClass().getResource("/Home/Login/Select.fxml"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Library Management System");
        primaryStage.getIcons().add(new Image("/Home/Image/imagesremovebg.png"));
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
