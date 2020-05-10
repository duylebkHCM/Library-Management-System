package Home.MapProject.HomePage.Mappage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.util.Duration;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import Home.Controllers.Controller;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MapController implements Initializable{
	
		@FXML
		private ImageView map_pin;
		    
	    @FXML
	    private TextField textSearch;
	    
	    @FXML
	    private ImageView imageSearch;
	    
	    @FXML
	    private JFXButton btnSearch;
	    
	    @FXML
	    public AnchorPane Parent;
	    
	    @FXML
	    public JFXButton homebtn;
	    	    
	    @FXML
	    private Slider zoom_slider;
	    
	    @FXML
	    private Button zoomIn;

	    @FXML
	    private Button zoomOut;
	    
	    @FXML
	    private AnchorPane root;
	    
	    @FXML
	    private ScrollPane map_scrollpane;
	    	    
	    private Group zoomGroup;	    
	    
	    private int count;	    
	    
	    String LogIn;

	    @FXML
	    //Receive message from scene 1
	    public void transferPermit(String _per){
	        //Display the message
	        LogIn = _per;
	    }
	    
	    private void zoom(double scaleValue) {
	          double scrollH = map_scrollpane.getHvalue();
	          double scrollV = map_scrollpane.getVvalue();
	          zoomGroup.setScaleX(scaleValue);
	          zoomGroup.setScaleY(scaleValue);
	          map_scrollpane.setHvalue(scrollH);
	          map_scrollpane.setVvalue(scrollV);
	      }
	    
	    //Read color data from file
	    private void readFile(String fileName) {
	    	 String line = null;	        
		        try {
		            FileReader fileReader = 
		                new FileReader(fileName);

		            BufferedReader bufferedReader = 
		                new BufferedReader(fileReader);
		            
		           if((line = bufferedReader.readLine()) != null) {		        	   
		        	   	System.out.println(line);	
				        if(fileName=="colorBG.txt")
				        	Parent.setStyle("-fx-background-color:"+ line.replace("0x", "#")); 
				        else{
				        	for(Node rec : Parent.getChildren()) {
					        	if(rec instanceof Rectangle) {
					        		((Rectangle)rec).setStyle("-fx-fill:"+ line.replace("0x", "#"));
					        	}
					        }			  	          	    	 
				        }				    
		           }				         
		            bufferedReader.close();         
		        }	        
		        catch(FileNotFoundException ex) {
		            System.out.println(
		                "Unable to open file '" + 
		                fileName + "'");                
		        }
		        catch(IOException ex) {
		            System.out.println(
		                "Error reading file '" 
		                + fileName + "'");        
		        }
	    }
	    	    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	/*--------------------Add_effect--------------------------*/
        btnSearch.setOnMouseEntered(e->{
    		Glow glowfx = new Glow();
	        glowfx.setLevel(1);
	        imageSearch.setEffect(glowfx);        	  	              
    	});
    	btnSearch.setOnMouseExited(e->{
    		 Glow glowfx = new Glow();
 	         glowfx.setLevel(0); 	      
 	       imageSearch.setEffect(glowfx);        
    	});
    	
    	textSearch.setOnMouseEntered(e->{
    		DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.LIGHTGREEN);
            dropShadow.setHeight(10);
            dropShadow.setWidth(10);
            dropShadow.setRadius(2);
            dropShadow.setSpread(1);
            textSearch.setEffect(dropShadow);
    	});
    	textSearch.setOnMouseExited(e->{
    		 textSearch.setEffect(null);
    	});
    	  	   	
    	//Update color with edit function
	       readFile("/Home/File/colorBG.txt");
	       readFile("/Home/File/colorBS.txt");
	       
	     /*-------------------------------Zoom--------------------------------------*/
    	 zoom_slider.setMin(0.5);
         zoom_slider.setMax(1.5);
         zoom_slider.setValue(1.0);
         zoom_slider.valueProperty().addListener((o, oldVal, newVal) -> zoom((Double) newVal));
         
         Group contentGroup = new Group();
         zoomGroup = new Group();
         contentGroup.getChildren().add(zoomGroup);
         zoomGroup.getChildren().add(map_scrollpane.getContent());
         map_scrollpane.setContent(contentGroup);
          	
         
        zoomIn.setOnAction(e->{
        	  double sliderVal = zoom_slider.getValue();
              zoom_slider.setValue(sliderVal += 0.1);
        });
        
        zoomOut.setOnAction(e->{
        	  double sliderVal = zoom_slider.getValue();
              zoom_slider.setValue(sliderVal + -0.1);
        });
           	
    	/*_______________________________Read data__________________________________*/
    	
    	ObservableList<BookInfo> inFos = FXCollections.observableArrayList();

		readDataBase(inFos);
        
		/*_______________________________First setting___________________________________*/
        
		map_pin.setVisible(false);
		String line1 = null;	        
        try {
            FileReader fileReader = 
                new FileReader("/Home/File/homebtnsetvisible.txt");

            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            
           if((line1 = bufferedReader.readLine()) != null) {	
        	   System.out.println(line1);
        	   	homebtn.setVisible(Boolean.parseBoolean(line1)); 
           }				         
            bufferedReader.close();         
        }	        
        catch(FileNotFoundException ex) {
                       
        }
        catch(IOException ex) {
                 
        }				 	
		
        /*---------------------------------Backtohomepage--------------------------------------*/
        homebtn.setOnAction(e->{
        	try {
        		 FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home/fxml/Mainpage.fxml"));/* Exception */
        		 Parent root1 = loader.load();      		  	
                 root.getChildren().setAll(root1);             
        		 root.setTopAnchor(root1, 0.0);
                 root.setBottomAnchor(root1, 0.0);
                 root.setLeftAnchor(root1, 0.0);
                 root.setRightAnchor(root1, 0.0);
                 
                 Controller inputController = loader.getController();
                 //Pass whatever data you want. You can have multiple method calls here
                 inputController.transferPermit("false"); 	
        	}catch(IOException ex) {
        		Logger.getLogger(MapController.class.getName()).log(Level.SEVERE, null,ex);
        	}
      
        });
        
        /*--------------------------Search----------------------------------*/
        btnSearch.setOnAction(action -> 
        {
        		count = 0; //Use to check the length of list when traverse
	        	System.out.println(textSearch.getText().length());
	        	
	        	for(int i=0;i<textSearch.getText().length();i++)
	        	{
	        		System.out.println((int)textSearch.getText().charAt(i));
	        	}
	        	
	        	int checkValid = isValidID(textSearch.getText());
	        	if(checkValid != 1)
	        		displayAlert(checkValid);
	        	else {		        	 
	        	  String str= textSearch.getText().replaceAll("\\s+","");
	        	  if(!inFos.isEmpty()) {
		        	  inFos.forEach(Item->
		      		  {	      	      			
		      			if((Item.ID).equals(str)){	      			
			      				for(Node no: Parent.getChildren()){
			      					if(no instanceof Label) {
			      						if(no.getId().equals( "lb" + Item.Location)){
			      							((Label)no).setVisible(true);
			      							double mapWidth = zoomGroup.getBoundsInLocal().getWidth();
			      					        double mapHeight = zoomGroup.getBoundsInLocal().getHeight();
			      					        double scrollH =(-300+(Double)((Label)no).getLayoutX())/ mapWidth;
			      					        double scrollV = (-300+(Double)((Label)no).getLayoutY())/ mapHeight;
			      					        final Timeline timeline = new Timeline();
			      					        final KeyValue kv1 = new KeyValue(map_scrollpane.hvalueProperty(), scrollH);
			      					        final KeyValue kv2 = new KeyValue(map_scrollpane.vvalueProperty(), scrollV);
			      					        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv1, kv2);
			      					        timeline.getKeyFrames().add(kf);
			      					        timeline.play();   
			      					        
				      					    double pinW = map_pin.getBoundsInLocal().getWidth();
				      				        double pinH = map_pin.getBoundsInLocal().getHeight();
				      				        map_pin.setLayoutX(50+(Double)((Label)no).getLayoutX() - (pinW / 2));
				      				        map_pin.setLayoutY(100+(Double)((Label)no).getLayoutY() - (pinH));	      				    
				      				        map_pin.setVisible(true);
			      				        	
				      				        if(Integer.parseInt(Item.avlQuantity) > 0) {
					      				        String line= null;
						      				    try {
						      				    	FileReader fileReader =  new FileReader("Binary.txt");
							      				    BufferedReader bufferedReader = new BufferedReader(fileReader); 
							      				    line = bufferedReader.readLine(); 
							      				    bufferedReader.close();
												} catch (IOException e1) {
													e1.printStackTrace();
												}	                			           
						      				    System.out.println(line);
						      			        if (Boolean.parseBoolean(line))
				      					        	displayBookInfo(Item);						      			     
				      				        }
				      				        else {
				      				        	displaySimilarBook(Item, inFos);
				      				        }				      				        
				      				        writeDataBase(LocalDate.now(), Item); //write search time into database
			      							System.out.println(((Label)no).isVisible());
			      						}
			      					}
			      				}
		      				}
		      			else {
		      				count=count+1;
		      			}      			
		      		  });
	        	  if(count == inFos.size()) {
	        		  displayAlert(checkValid);	        		  
	        	  }
	        	}
	        }
	     });
    }
    
      /*-------------------------------------------------Check_the_text_enter_searchbar------------------------------------------------*/
      private int isValidID(String ID) {
    	  
    	  Boolean flag = false;
    	  for(int i=0;i<ID.length();i++) {
    		  if(ID.charAt(i) != '\0' && ID.charAt(i) != 32){
    			  flag= true;
    			  break;
    		  }   		 
    	  }
    	  if(flag==false) {
    		  return -1;
    	  }  	  
    	  //Co nhap du lieu   	  
    	  else{
    		  System.out.println(flag);
    		  for(int i=0;i<ID.length();i++) {
				  if( ID.charAt(i)< 48 ||  ID.charAt(i)> 57 &&  ID.charAt(i)<65 ||  ID.charAt(i)> 90 &&  ID.charAt(i)<97 ||  ID.charAt(i) > 122) {
					  if(ID.charAt(i) != 32)
						  return 0;
				  }       				  
			  }
			  
			  for(int i= 0; i<ID.length()-1 ; i++) {
				  if(ID.charAt(i+1) == ' ' && ID.charAt(i) != ' ' ) {
					  for(int j=i+1;j< ID.length();j++)
						  if(ID.charAt(j) != ' ' && ID.charAt(j) != '\0') return 0;
				  }
			  } 			  			  
			  
			  String str = ID.replaceAll("\\s+", ""); //loai khoang trang			  
			  if( str.length() != 7) return 0; //Default length of ID , can be changed
			  else return 1;
    	  }  		    
      }
      
      /*---------------------------------------------------------------------------*/
      private void displayAlert(int checkValid) {
    	  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	  DialogPane dialogPane = alert.getDialogPane();
    	  dialogPane.setStyle("-fx-background-color: #90caf9");
          alert.setTitle("Warning");
          if(checkValid == 1) {
        	  alert.setHeaderText("The book you looking for is not in library");
              alert.setContentText("Sorry!");           
          }         
          else if(checkValid == -1) {
        	  alert.setHeaderText("You have not entered the book ID yet");
              alert.setContentText("Please enter the book ID!");
          }
          else if (checkValid == 0) {
        	  alert.setHeaderText("Your ID is invalid");
              alert.setContentText("Please enter the valid ID");
          }
        	  
          Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
          alert1.setTitle("Warning");
          ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/Home/Image/519791-101_Warning-512.png"));
          alert1.setHeaderText("Notification");  
          alert.show();
      }
      
      /*-----------------------------------------------------*/
	  private void displayBookInfo(BookInfo Item) {
	    	Stage inFo = new Stage();	    	
	    	inFo.initModality(Modality.APPLICATION_MODAL);
	    	inFo.setTitle("Information");
	    	inFo.setWidth(300);
	    	inFo.setHeight(200);
	    	Label label= new Label();
	    	label.setText("Information of the book you find:");
	    	label.setStyle("-fx-font-weight: bold");
	    	Text t1,t2 = new Text();
	    	TextFlow tf1 = new TextFlow();
	    	t1 = new Text("Title: ");
	    	t1.setStyle("-fx-font-weight: bold;");
	    	t2 = new Text(Item.Name);
	    	tf1.getChildren().addAll(t1, t2); 
	    	tf1.setStyle("-fx-text-alignment: center;");
	    	TextFlow tf2 = new TextFlow();
	    	t1 = new Text("Author: ");
	    	t1.setStyle("-fx-font-weight: bold;");
	    	t2 = new Text(Item.Author); 
	    	tf2.getChildren().addAll(t1, t2); 
	    	tf2.setStyle("-fx-text-alignment: center;");
	    	TextFlow tf3 = new TextFlow();
	    	t1 = new Text("Publisher: ");
	    	t1.setStyle("-fx-font-weight: bold;");
	    	t2 = new Text(Item.bookPublisher); 
	    	tf3.getChildren().addAll(t1, t2); 
	    	tf3.setStyle("-fx-text-alignment: center;");
	    	Button closeBtn = new Button("Close");
	    	closeBtn.setOnAction(e->{
	    		inFo.close();
	    	});	    	
	    	VBox layout= new VBox(10);
	    	layout.setSpacing(20);
	    	layout.getChildren().addAll(label, tf1, tf2, tf3 , closeBtn);
	    	layout.setAlignment(Pos.CENTER);
	    	layout.setStyle("-fx-background-color: #90caf9");
	    	Scene scene = new Scene(layout, 300, 200);
	    	inFo.getIcons().add(new Image("/Home/Image/book.png"));
	    	inFo.setResizable(false);
	    	inFo.setScene(scene);
	    	inFo.show();
	  }
	  private void displaySimilarBook(BookInfo Item, ObservableList<BookInfo> inFos) {
		  JFXListView<Label> list= new JFXListView<Label>();
		  inFos.forEach(Element->{
			  if(Integer.parseInt(Element.avlQuantity) >0)
				  if(Element.bookCagory == Item.bookCagory) {
					  list.getItems().add(new Label("ID:" + Element.ID + "\t" + "Title: "+ Element.Name + "\t" + "Location: "+ Element.Location));
				  }
		  });
		  if(list.getItems().isEmpty()) {
			  list.getItems().add(new Label("There is no book with similar category"));
		  }
		  Label lbcontent = new Label("This book is now not available");
		  Label lbHeader = new Label("Similar book:");		
		  lbHeader.setStyle("-fx-font-weight: bold;");
		  lbcontent.setStyle("-fx-font-weight: bold;");
		  VBox layout= new VBox(10);
	      layout.setSpacing(20);
	      layout.getChildren().addAll(lbcontent, lbHeader, list);
	      layout.setAlignment(Pos.CENTER);  	
		  Stage stage = new Stage();
		  Scene scene = new Scene(layout, 300, 200);
		  stage.setWidth(300);
		  stage.setHeight(200);
		  stage.setTitle("SimilarBooks");
		  stage.getIcons().add(new Image("/Home/Image/book.png"));
		  stage.setResizable(false);
		  stage.setScene(scene);
		  stage.show();
	  }
	  /*---------------------Book_InfoClass------------------*/
	  private class BookInfo {
	        String Location;
	        String ID;
	        String Author;
	        String bookPublisher;
	        String avlQuantity;
	        String Name;  
	        String bookCagory;
	        private void addData(int order, String data) {
	        	switch(order) {
	        	case 1:
	        		this.ID=data;
	        		System.out.println(data);
	        		break;
	        	case 2:
	        		this.Name=data;
	        		System.out.println(data);
	        		break;
	        	case 3:
	        		this.Author=data;
	        		System.out.println(data);
	        		break;
	        	case 4:
	        		this.bookCagory=data;
	        		System.out.println(data);
	        		break;
	        	case 5:
	        		this.bookPublisher=data;
	        		System.out.println(data);
	        		break;
	        	case 8:
	        		this.avlQuantity=data;
	        		System.out.println(data);
	        		break;
	        	case 10:
	        		this.Location=data;
	        		System.out.println(data);
	        		break;
	        	}
	        }
	    }	  
	  /*--------------------------Read_write_data_from_database---------------------------*/
	  private void readDataBase(ObservableList<BookInfo> users){	  		 
		  try { 		  	
			  	FileInputStream file = new FileInputStream(new File("/Home/File/Book_Database.xlsx")); 
	  
	            // Create Workbook instance holding reference to .xlsx file 
	            XSSFWorkbook workbook = new XSSFWorkbook(file); 
	  
	            // Get first/desired sheet from the workbook 
	            XSSFSheet sheet = workbook.getSheetAt(0); 
	        
	            System.out.println( "Num of row: " + Integer.toString(sheet.getLastRowNum()+1));
	            
	            if(sheet.getLastRowNum() + 1 != 1) {
	                // Iterate through each rows one by one 
		            Iterator<Row> rowIterator = sheet.iterator(); 		            
		            while (rowIterator.hasNext()) { 
		                XSSFRow row = (XSSFRow)rowIterator.next(); 
		                
		                if(row.getRowNum()==0){
		                	   continue; 
		                }	                
		                // For each row, iterate through all the columns 
		                Iterator<Cell> cellIterator = row.cellIterator(); 
		                
		                BookInfo obj= new BookInfo();
		                
		                int count=1;
		                
		                while (cellIterator.hasNext()) { 
		                    Cell cell = cellIterator.next(); 
		                    switch(cell.getCellType()) {
		                    case NUMERIC :
		                    	 obj.addData(count, Integer.toString((int)Math.round(cell.getNumericCellValue())));
		                    	 break;
		                    case STRING:
		                    	 obj.addData(count, cell.getStringCellValue());
		                    	 break;
		                    }                  	                   
		                    count++;
		                } 
		                users.add(obj);	                
		            } 
		        	workbook.close();
		            file.close(); 
	            }	          
		  }			 
	      catch (Exception e) { 
	            e.printStackTrace(); 
	        } 	     
	  }
	  
	  private void writeDataBase(LocalDate day, BookInfo Item) {
		  
		  try { 		  	
			  	FileInputStream file = new FileInputStream(new File("/Home/File/Book_Database.xlsx")); 
	  
	            // Create Workbook instance holding reference to .xlsx file 
	            XSSFWorkbook workbook = new XSSFWorkbook(file); 
	  
	            // Get first/desired sheet from the workbook 
	            XSSFSheet sheet = workbook.getSheetAt(0); 
	  
	            // Iterate through each rows one by one 
	            Iterator<Row> rowIterator = sheet.iterator(); 
	            
	            while (rowIterator.hasNext()) { 
	                XSSFRow row = (XSSFRow)rowIterator.next(); 
	                
	                if(row.getRowNum()==0){
	                	   continue;
	                }
	                
	                // For each row, iterate through all the columns 
	                if(row.getCell(10) == null){
	                	 row.createCell(10);
	                	 row.getCell(10).setCellValue(0);
	                	 System.out.println(row.getCell(10).getNumericCellValue());
	                }
	                
	                if(Item.ID.equals(row.getCell(0).getStringCellValue())) {
	                	if(row.getCell(11) == null) {
	                		row.createCell(11);
	                		row.getCell(11).setCellValue(java.sql.Date.valueOf(day));
	                		row.getCell(10).setCellValue(row.getCell(10).getNumericCellValue()+1);
	                	}
	                	else {
	                		Boolean check= checkNewDay(day,row.getCell(11).getDateCellValue().toInstant().atZone(ZoneId.systemDefault())
	                			      .toLocalDate());
	                		if(check) {
	                			row.getCell(10).setCellValue(1);
	                		}
	                		else row.getCell(10).setCellValue(row.getCell(10).getNumericCellValue()+1);
	                		row.getCell(11).setCellValue(java.sql.Date.valueOf(day));
	                	}	                	
	                }              
	            }

	            file.close(); 
	            FileOutputStream outFile =new FileOutputStream(new File("/Home/File/Book_Database.xlsx"));
	            workbook.write(outFile);
	            outFile.close();
	        	workbook.close();
		  	}
	        catch (Exception e) { 
	            e.printStackTrace(); 
	        } 	  		
	  }
	  
	  //Dung de thong ke so lieu theo ngay
	  private Boolean checkNewDay(LocalDate day1, LocalDate day2) {
		  Period period = Period.between(day1, day2);
		  int diff = period.getDays();
		  if(diff != 0)
			  return true;
		  else return false;
	  }
}
