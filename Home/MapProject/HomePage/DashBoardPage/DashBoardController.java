package Home.MapProject.HomePage.DashBoardPage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.ResourceBundle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DashBoardController implements Initializable{
	
	@FXML 
	private ComboBox<String> comboBox;
	
	@FXML
	private AnchorPane anchorpane;

    @FXML
    private JFXTreeTableView<BookInfos> treeView;
    
    @FXML
    private NumberAxis yAxis;
    
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;
    
    @FXML
    private MenuItem byDayItem;
    
    @FXML
    private MenuItem byAll;
    
    @FXML
    private Label avlBookLB;
    
    private int count=0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	   	
    	/*---------------------------------------------------------------------------------------------*/
        JFXTreeTableColumn<BookInfos, String> bookName = new JFXTreeTableColumn<>("Book's Name");
        bookName.setPrefWidth(120);
        bookName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookInfos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookInfos, String> param) {
                return param.getValue().getValue().Name;
            }
        });

        JFXTreeTableColumn<BookInfos, String> searchTime = new JFXTreeTableColumn<>("Number of searches");
        searchTime.setPrefWidth(120);
        searchTime.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookInfos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookInfos, String> param) {
                return param.getValue().getValue().Number;
            }
        });

        JFXTreeTableColumn<BookInfos, String> LocateCol = new JFXTreeTableColumn<>("Locate");
        LocateCol.setPrefWidth(120);
        LocateCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookInfos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookInfos, String> param) {
                return param.getValue().getValue().Location;
            }
        });
        
        JFXTreeTableColumn<BookInfos, String> Day = new JFXTreeTableColumn<>("Time");
        Day.setPrefWidth(120);
        Day.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookInfos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookInfos, String> param) {
                return param.getValue().getValue().Date;
            }
        });
        
        /*--------------------------------------------------------------------------------------------*/
               
        ObservableList<BookInfos> inFos = FXCollections.observableArrayList();
        readData(inFos);
        if(!inFos.isEmpty()) {
        	final TreeItem<BookInfos> root = new RecursiveTreeItem<BookInfos>(inFos, RecursiveTreeObject::getChildren);
            treeView.getColumns().setAll(bookName, searchTime, LocateCol, Day);
            treeView.setRoot(root);
            treeView.setShowRoot(false);  
            
            avlBookLB.setText("Available books: " + Integer.toString(numofBooks(inFos)));
            
            /*-------------------------------------*/	
            //Option
            comboBox.getItems().add("Chart");
            
            comboBox.setOnAction(e->{ 
            	if(comboBox.getValue().toString()== "Chart" && count==0){     
            		if(!byDayOption(inFos).isEmpty()) {
            			drawBarChart(byDayOption(inFos));
                		count++;
            		}
            		else {
            	    	Stage alert = new Stage();	 
            	    	AnchorPane layout= new AnchorPane();
            	    	Label lb = new Label("No data to display");
            	    	lb.setStyle("-fx-font-size: 16px");
            	    	layout.getChildren().addAll(lb);
            	    	layout.setStyle("-fx-background-color: #90caf9");
            	    	Scene scene = new Scene(layout, 200,50);
            	    	alert.setTitle("Warning");
            	    	alert.setMinWidth(200);
            	    	alert.setMinHeight(50);
            	    	alert.setResizable(false);
            	    	alert.setScene(scene);
            	    	alert.show();
            		}
            	}       	
            });
            
            byDayItem.setOnAction(e->{
            	final TreeItem<BookInfos> alroot = new RecursiveTreeItem<BookInfos>(byDayOption(inFos), RecursiveTreeObject::getChildren);
                treeView.getColumns().setAll(bookName, searchTime, LocateCol, Day);
                treeView.setRoot(alroot);
                treeView.setShowRoot(false);   
            });
            byAll.setOnAction(e->{
            	final TreeItem<BookInfos> alroot = new RecursiveTreeItem<BookInfos>(inFos, RecursiveTreeObject::getChildren);
                treeView.getColumns().setAll(bookName, searchTime, LocateCol, Day);
                treeView.setRoot(alroot);
                treeView.setShowRoot(false);  
            });
        }
        else {
        	avlBookLB.setText("Available books: ");
        	byDayItem.setDisable(true);
        	comboBox.getItems().add("Chart");
        	comboBox.setDisable(true);
        	byAll.setDisable(true);
        }
    }    
    
    /*-----------------------------------------------------------*/
    
    private void setMaxBarWidth(double maxBarWidth, double minCategoryGap){
        double barWidth=0;
        do{
            double catSpace = xAxis.getCategorySpacing();
            double avilableBarSpace = catSpace - (barChart.getCategoryGap() + barChart.getBarGap());
            barWidth = (avilableBarSpace / barChart.getData().size()) - barChart.getBarGap();
            if (barWidth > maxBarWidth) {
                avilableBarSpace=(maxBarWidth + barChart.getBarGap())* barChart.getData().size();
                barChart.setCategoryGap(catSpace-avilableBarSpace-barChart.getBarGap());
            }
        } while(barWidth > maxBarWidth);

        do{
            double catSpace = xAxis.getCategorySpacing();
            double avilableBarSpace = catSpace - (minCategoryGap + barChart.getBarGap());
            barWidth = Math.min(maxBarWidth, (avilableBarSpace / barChart.getData().size()) - barChart.getBarGap());
            avilableBarSpace=(barWidth + barChart.getBarGap())* barChart.getData().size();
            barChart.setCategoryGap(catSpace-avilableBarSpace-barChart.getBarGap());
        } while(barWidth < maxBarWidth && barChart.getCategoryGap() > minCategoryGap);
    }
    
    private void displayLabelForData(XYChart.Data<String, Number> data) {
    	  final Node node = data.getNode();
    	  final Text dataText = new Text(data.getYValue() + "");
    	  node.parentProperty().addListener(new ChangeListener<Parent>() {
    	    @Override public void changed(ObservableValue<? extends Parent> ov, Parent oldParent, Parent parent) {
    	      Group parentGroup = (Group) parent;
    	      parentGroup.getChildren().add(dataText);
    	    }
    	  });

    	  node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
    	    @Override public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
    	      dataText.setLayoutX(
    	        Math.round(
    	          bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2
    	        )
    	      );
    	      if(node.getBoundsInParent().getHeight()>30) {
    	    	  dataText.setFill(Color.WHITE);
    	    	  dataText.setLayoutY(
    	      	        Math.round(
    	      	          bounds.getMinY() + dataText.prefHeight(-1) * 0.5 +5
    	      	        )
    	      	   );
    	      } 
    	      else {
    	    	  dataText.setLayoutY(   	    			  
      	      	        Math.round(
      	      	          bounds.getMinY() - dataText.prefHeight(-1) * 0.5
      	      	        )
      	      	  );
    	      }
    	    }
    	  });
    	}
    
    void drawBarChart(ObservableList<BookInfos> users) {
    	
    	XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
            	
        int[] searchNumber = new int[9]; // number of bookshelves
        
        for(int i=0;i<searchNumber.length;i++){
        	searchNumber[i]=0;
        }
        
        StringProperty[] bookShelfList= new StringProperty[9];// number of bookshelves
        
        int count=0; //Current length of the list
               
        for(BookInfos Node: users) {      	
            Boolean flag=false;            
        	for(int i=0; i<count; i++) {
        		if(Node.Location.getValue().equals(bookShelfList[i].getValue())) {
        			flag=true;
        			break;
        		}
        	}
        	if(flag==false) {
        		bookShelfList[count]=Node.Location;
        		count++;
        	}
        	else if(flag == true) continue;
        };
        System.out.println(count);
        for(int i= 0; i <count ; i++) {
        	int idx=i;
        	 users.forEach(Node->{
             	if(Node.Location.getValue().equals(bookShelfList[idx].getValue())) {
             		searchNumber[idx] += Integer.parseInt(Node.Number.getValue());
             	}             	
             });
        	 System.out.println(searchNumber[idx]);
        }  
        for(int i=0;i<count;i++) {
        	System.out.println(bookShelfList[i].getValue());
        	final XYChart.Data<String, Number> data = new XYChart.Data<String, Number>(bookShelfList[i].getValue(), searchNumber[i]);
        	
        	data.nodeProperty().addListener(new ChangeListener<Node>() {
    	        @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
    	        	 if (node != null) {
    	        		 displayLabelForData(data);
    	        	 }
    	        }
    	 });	
        	dataSeries1.getData().add(data);
        }
        barChart.getData().add(dataSeries1);
        barChart.setTitle("Number of searches by location");   
        barChart.setAnimated(false);
        Platform.runLater(()->setMaxBarWidth(40, 10));
        barChart.widthProperty().addListener((obs,b,b1)->{
            Platform.runLater(()->setMaxBarWidth(40, 10));
        });
    }
        
    ////////////////////////////////////////////////////
    class BookInfos extends RecursiveTreeObject<BookInfos>{
       StringProperty Location;
       StringProperty Number;
       StringProperty Name;
       StringProperty Date;
       StringProperty avlBooks;
        private void addData(int order, String data) {
        	switch(order) {
        	case 2:
        		this.Name= new SimpleStringProperty(data);
        		System.out.println(data);
        		break;       	
        	case 8:
        		this.avlBooks= new SimpleStringProperty(data);
        		System.out.println(data);
        		break;       	
        	case 10:
        		this.Location= new SimpleStringProperty(data);
        		System.out.println(data);
        		break;
        	case 11:
        		this.Number= new SimpleStringProperty(data);
        		System.out.println(data);
        		break;
        	case 12:
        		this.Date= new SimpleStringProperty(data);
        	}
        }
    }
    
    private int numofBooks(ObservableList<BookInfos> infoBook) {
    	int numBooks = 0;
    	for(BookInfos book : infoBook) {    		
    		numBooks = numBooks + Integer.parseInt(book.avlBooks.getValue());
    	};
    	return numBooks;
    }
    
    /*--------------ReadDataBase----------------------*/
    private void readData(ObservableList<BookInfos> infoBook) {
    	try { 		  	
		  	FileInputStream file = new FileInputStream(new File("/Home/File/Book_Database.xlsx")); 
  
            // Create Workbook instance holding reference to .xlsx file 
            XSSFWorkbook workbook = new XSSFWorkbook(file); 
  
            // Get first/desired sheet from the workbook 
            XSSFSheet sheet = workbook.getSheetAt(0); 
            
            if(sheet.getLastRowNum() + 1 != 1) {
	            // Iterate through each rows one by one 
	            Iterator<Row> rowIterator = sheet.iterator(); 
	            while (rowIterator.hasNext()) { 
	                XSSFRow row = (XSSFRow)rowIterator.next(); 
	                
	                if(row.getRowNum()== 0){
	             	   continue;
	                }
	                if(row.getCell(10) == null){
	               	 row.createCell(10);
	               	 row.getCell(10).setCellValue(0);
	               	 System.out.println(row.getCell(10).getNumericCellValue());
	               }                
	                // For each row, iterate through all the columns 
	                Iterator<Cell> cellIterator = row.cellIterator(); 
	                
	                BookInfos obj= new BookInfos();
	                
	                int count=1;
	                while (cellIterator.hasNext()) { 
	                    Cell cell = cellIterator.next(); 
	                    switch(cell.getCellType()){
	                    case NUMERIC :
	                    	if (count != 12)
	                    	 obj.addData(count, Integer.toString((int)Math.round(cell.getNumericCellValue())));
	                    	else{
	                    		LocalDate day = cell.getDateCellValue().toInstant()
	                    	      .atZone(ZoneId.systemDefault())
	                    	      .toLocalDate();                    		
	                    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	                    		String formattedString = day.format(formatter);
	
	                    		obj.addData(count,formattedString);
	                    	 }
	                    	 break;
	                    case STRING:
	                    	 obj.addData(count, cell.getStringCellValue());
	                    	 break;
	                    }                  	                   
	                    count++;
	                } 
	                infoBook.add(obj);	                
	            } 
	        	workbook.close();
	            file.close(); 
            }
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    }   
    
    ObservableList<BookInfos> byDayOption(ObservableList<BookInfos> users){
    	ObservableList<BookInfos> subUsers = FXCollections.observableArrayList();
    	users.forEach(Item->{
    		if(Item.Date != null)
	    		if(Item.Date.getValue().equals(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))) {
	    			System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	    			subUsers.add(Item);
	    		}
    	});
    	return subUsers;
    }
}
