package Home.MapProject.HomePage.SideBar;


import Home.Controllers.Controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import Home.MapProject.HomePage.Mappage.MapController;


public class HomePageController implements Initializable {

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    public AnchorPane root;
    
    @FXML
    public AnchorPane anchorPane;
    
    @FXML
    public JFXButton btnBack;
    
    @FXML
	public JFXButton btnmap;

    @FXML
    public JFXButton btnedit;
    
    @FXML
    public JFXButton btndash;

    @FXML
    private BorderPane bdpane;
    
    @FXML   
    public GridPane grid;
       
	//VBox box;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {        	
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home/MapProject/HomePage/SideBar/SidePanel.fxml"));
            VBox box = loader.load();           
            drawer.setSidePane(box);
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        bdpane.setVisible(false);
        
        FXMLLoader load = new FXMLLoader(getClass().getResource("/Home/MapProject/HomePage/SideBar/SidePanel.fxml"));
        VBox newBox= new VBox();
        
		try {
			newBox = load.load();
			SidePanelController controller = load.getController();
	        controller.setCallback(this);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        AnchorPane pane = new AnchorPane();
   	 	pane.setPrefWidth(350);
   	 	pane.getChildren().add(newBox);  
   	 	pane.setTopAnchor(newBox, 0.0);
   	 	pane.setLeftAnchor(newBox, 0.0);
   	 	pane.setBottomAnchor(newBox, 0.0);    
   	 	root.getChildren().add(pane);
   	 	root.setTopAnchor(pane, 0.0);
   	 	root.setLeftAnchor(pane, 0.0);
   	 	root.setBottomAnchor(pane, 0.0);  	
   	 	
   	 	pane.setVisible(false);       
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
           
            if(bdpane.isVisible()) {            	
            	pane.setVisible(false);            	
            	drawer.close();   
            	new Timer().schedule(new TimerTask(){  
                	 @Override
                     public void run() {
                		 bdpane.setVisible(false);
                	 }
                 },500);                 	
            }
            else {
            	 bdpane.setVisible(true);
            	 drawer.open();              
            	 pane.setVisible(true);  
            }            
        });
        
        //Out
        btnBack.setVisible(true);
        btnBack.setOnAction(e->{
        	Parent root1= null;
        	
        	try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home/fxml/Mainpage.fxml"));/* Exception */
                root1 = loader.load();      

                Controller inputController = loader.getController();

                root.getChildren().setAll(root1);
        		root.setTopAnchor(root1, 0.0);
        		root.setBottomAnchor(root1, 0.0);
        		root.setLeftAnchor(root1, 0.0);
        		root.setRightAnchor(root1, 0.0);
                inputController.transferPermit("true");
        	}catch(IOException ex) {
        		Logger.getLogger(MapController.class.getName()).log(Level.SEVERE, null,ex);
        	}
        });
        
        //In
        btnmap.setVisible(true);
        btnmap.setOnAction(e->{
        	LoadUi("/Home/MapProject/HomePage/Mappage/Map.fxml", anchorPane);
        });
        
        btnedit.setVisible(true);
        btnedit.setOnAction(e->{
        	LoadUi("/Home/MapProject/HomePage/EditPage/Edit.fxml", anchorPane);
        });
        
        btndash.setVisible(true);
        btndash.setOnAction(e->{
        	LoadUi("/Home/MapProject/HomePage/DashBoardPage/DashBoard.fxml", anchorPane);
        });              
    }
    
    public void LoadUi(String UI, AnchorPane ref) {
    	Parent root1= null;
    	
    	try {
    	root1= FXMLLoader.load(getClass().getResource(UI));
    	}catch(IOException ex) {
    		Logger.getLogger(SidePanelController.class.getName()).log(Level.SEVERE, null,ex);
    	}
    	    	
    	ref.getChildren().setAll(root1);
    	ref.setTopAnchor(root1, 0.0);
    	ref.setBottomAnchor(root1, 0.0);
    	ref.setLeftAnchor(root1, 0.0);
    	ref.setRightAnchor(root1, 0.0);
    	btnBack.setVisible(false);
    	btnmap.setVisible(false);
    	btnedit.setVisible(false);
    	btndash.setVisible(false);
    	grid.setDisable(true);   	   	
    }
}
