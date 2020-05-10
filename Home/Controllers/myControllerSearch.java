package Home.Controllers;

import java.net.URL;
import java.io.IOException;
import java.io.File;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Iterator;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.io.FileOutputStream;

public class myControllerSearch implements Initializable {

    String LogIn;

    @FXML
    //Receive message from scene 1
    public void transferPermit(String _per){
        //Display the message
        LogIn = _per;
    }

    @FXML
    Button btnSearch2;
    public String input = "";
    @FXML
    AnchorPane rootanchor;
    @FXML
    ToggleGroup Search_state = new ToggleGroup();
    @FXML
    ToggleGroup CatOption = new ToggleGroup();
    @FXML
    TextField barSearch;
    @FXML
    MenuButton CatMenu;
    @FXML
    TableView<Book> tblSearchInfo;
    @FXML
    TableColumn IDCol, CatCol, TitleCol, PubCol, AuthorCol;
    @FXML
    RadioButton radiobtnAll, radiobtnName, radiobtnGenre, radiobtnAuthor;

    @FXML
    RadioMenuItem itemSci,  itemMaga, itemMusic, itemLit, itemEdu, itemComputer, itemComic, itemBusiness, itemSocial;

    @FXML
    ImageView imgHome;

    @FXML
    Button btnHome;
    public String SState;
    public String radioChecked(){
        radiobtnAll.setUserData("All");
        radiobtnAuthor.setUserData("Author");
        radiobtnGenre.setUserData("Category");
        radiobtnName.setUserData("Name");
        Object Sstate = Search_state.getSelectedToggle().getUserData();
        return Sstate.toString();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        CatMenu.setVisible(false);
        SState = radioChecked();



        Search_state.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (Search_state.getSelectedToggle() != null) {
                    if (Search_state.getSelectedToggle().getUserData().toString() == "Category") {
                        CatMenu.setVisible(true);
                        CatOption.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                            public void changed(ObservableValue<? extends Toggle> ov,
                                                Toggle old_toggle, Toggle new_toggle) {
                                if (CatOption.getSelectedToggle() != null) {
                                    CatMenu.setText(getCat());
                                }
                            }
                        });}
                    else CatMenu.setVisible(false);
                }
            }
        });

            try {
                final BookDB sample = outData();
                btnSearch2.setOnMouseClicked(actionEvent -> {
                    input = barSearch.getText();
                    SState = radioChecked();
                    BookDB resultList = null;
                    switch (SState) {
                        case "All":
                            BookDB re1, re2;
                            re1 = searchTitle(sample, input);
                            re2 = searchAuthor(sample, input);
                            resultList = mergeResult(re1, re2);
                            break;
                        case "Name":
                            resultList = searchTitle(sample, input);
                            break;
                        case "Author":
                            resultList = searchAuthor(sample, input);
                            break;
                        case "Category":
                            resultList = searchCat(sample, getCat());
                            break;

                    }
                    displayDB(resultList);

                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            tblSearchInfo.setRowFactory(tv -> {
                TableRow<Book> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Book rowData = row.getItem();
                        if (rowData.getBookID() == null) return;
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Home/fxml/BookDetails.fxml"));
                            Parent root = (Parent) fxmlLoader.load();

                            //Get controller of scene2
                            BookDetailsController inputController = fxmlLoader.getController();
                            //Pass whatever data you want. You can have multiple method calls here
                            inputController.transferPermit(LogIn);

                            Stage stage = new Stage();
                            stage.setScene(new Scene(root, 700, 500));
                            stage.show();
                            stage.setOnHidden(new EventHandler<WindowEvent>() {
                                @Override
                                public void handle(WindowEvent windowEvent) {
                                    try{

                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                            BookDetailsController controller = fxmlLoader.getController();
                            controller.initData(rowData);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });
                return row;
            });

    }

    public BookDB searchTitle(BookDB sample_file, String input){
        if (sample_file == null) return null;
        if (input.length() == 0) return null;
        int k = 0;
        for (int i = 0; i<sample_file.getBookNumber(); i++){
            if (sample_file.Booklist[i].getBookTitle().contains(input)) k++;
        }

        if (k == 0) return null;
        BookDB result = new BookDB(k+1);

        result.Booklist[0] = new Book("Search By Title",1);
        k=1;

        for (int i = 0; i<sample_file.getBookNumber(); i++){
            if (sample_file.Booklist[i].getBookTitle().contains(input)){
                result.Booklist[k] = new Book(sample_file.Booklist[i]);
                k++;
            }

        }

        return result;
    }

    public BookDB searchCat(BookDB sample_file,String stringCat){
        Category myCat = new Category(stringCat, 0);
        int k = 0;
        for (int i = 0; i<sample_file.getBookNumber(); i++){
            if (sample_file.Booklist[i].getBookID().contains(myCat.catID())) k++;
        }

        if (k == 0) return null;
        BookDB result = new BookDB(k+1);

        result.Booklist[0] = new Book("Search By Category",1);
        k=1;

        for (int i = 0; i<sample_file.getBookNumber(); i++){
            if (sample_file.Booklist[i].getBookID().contains(myCat.catID()))
            {
                result.Booklist[k] = new Book(sample_file.Booklist[i]);
                k++;
            }

        }

        return result;
    }

    public BookDB searchAuthor(BookDB sample_file, String input){
        if (sample_file == null) return null;
        if (input.length() == 0) return null;
        int k = 0;
        for (int i = 0; i<sample_file.getBookNumber(); i++){
            if (sample_file.Booklist[i].getBookAuthor().contains(input)) k++;}
        if (k == 0) return null;
        BookDB result = new BookDB(k+1);
        result.Booklist[0] = new Book("Search By Author",1);
        k=1;
        for (int i = 0; i<sample_file.getBookNumber(); i++) {
            if (sample_file.Booklist[i].getBookAuthor().contains(input)) {
                result.Booklist[k] = new Book(sample_file.Booklist[i]);
                k++;
            }
        }

        return result;
    }
    public BookDB mergeResult(BookDB a, BookDB b){
        if (a == null && b == null) return null;
        if (a == null) return b;
        else if(b== null) return a;
        BookDB c = new BookDB(a.getBookNumber()+b.getBookNumber());
        for (int i = 0; i< a.getBookNumber() + b.getBookNumber(); i++){
            if (i < a.getBookNumber()) c.Booklist[i] = new Book(a.Booklist[i]);
            else c.Booklist[i] = new Book(b.Booklist[i-a.getBookNumber()]);
        }
        return c;
    }

    public void displayDB(BookDB sample){
        if(sample== null) {

            return;
        }
        tblSearchInfo.setEditable(true);
        IDCol.setMinWidth(sample.getBookNumber());
        IDCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("bookID"));
        AuthorCol.setMinWidth(sample.getBookNumber());
        AuthorCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("bookAuthor"));
        TitleCol.setMinWidth(sample.getBookNumber());
        TitleCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("bookTitle"));
        PubCol.setMinWidth(sample.getBookNumber());
        PubCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("bookPublisher"));
        CatCol.setMinWidth(sample.getBookNumber());
        CatCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("bookCat"));
        ObservableList<Book> data = FXCollections.observableArrayList(sample.Booklist);
        tblSearchInfo.setItems(data);
        tblSearchInfo.getColumns();
    }
    public BookDB outData() throws IOException{

            String path = "/Home/File/Book_Database.xlsx";
            File excelFile = new File(path);
            FileInputStream fis = new FileInputStream(excelFile);

            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
             BookDB sample = new BookDB(rows-1);
            for (int i = 1; i < rows; i++) {
                Row row = sheet.getRow(i);
                String _bookID = row.getCell(0).getStringCellValue();
                String _bookTitle = row.getCell(1).getStringCellValue();
                String _bookAuthor = row.getCell(2).getStringCellValue();
                String _bookCat = row.getCell(3).getStringCellValue();
                String _bookPub = row.getCell(4).getStringCellValue();
                String _bookEdi = row.getCell(5).getStringCellValue();
                int _totalAmt = (int)row.getCell(6).getNumericCellValue();
                int _avlbAmt = (int)row.getCell(7).getNumericCellValue();
                float _price = (float)row.getCell(8).getNumericCellValue();


                sample.Booklist[i-1] = new Book(_bookID+"", _bookTitle, _bookAuthor, _bookCat, _bookPub, _bookEdi, _totalAmt, _avlbAmt, _price);
            }
            fis.close();
            workbook.close();
        return sample;
    }
    @FXML
    public String getCat(){
        itemSocial.setUserData("Social Science");
        itemBusiness.setUserData("Business");
        itemComic.setUserData("Comic");
        itemComputer.setUserData("Computer & Tech");
        itemEdu.setUserData("Edu & Reference");
        itemLit.setUserData("Literature & Fiction");
        itemMaga.setUserData("Magazine");
        itemMusic.setUserData("Music & Art");
        itemSci.setUserData("Science & Math");
        Object catItem = CatOption.getSelectedToggle().getUserData();
        CatMenu.setText(catItem.toString());
        return catItem.toString();
    }
    @FXML
    public void onbtnEnter(MouseEvent e) {
        Button button = (Button)e.getSource();
        Glow glowfx = new Glow();
        glowfx.setLevel(1);
        switch(button.getId()) {
            case "btnSearch2":
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
            case "btnHome":
                button.setStyle("-fx-background-color: #33ffcc");
                imgHome.setEffect(glowfx);
                break;
            case "btnSearch2":
                button.setEffect(null);
                break;
        }
    }

    public void onbtnClick(MouseEvent e) throws Exception {
        try {
            //Load second scene
            //Stage stage = (Stage) btnHome.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home/fxml/Mainpage.fxml"));/* Exception */
            Parent root = loader.load();
            //Scene scene = new Scene(root, 900, 600);
        	rootanchor.getChildren().setAll(root);
	   	 	rootanchor.setTopAnchor(root, 0.0);
	   	 	rootanchor.setBottomAnchor(root, 0.0);
	   	 	rootanchor.setLeftAnchor(root, 0.0);
	   	 	rootanchor.setRightAnchor(root, 0.0);
            //Get controller of scene2
            Controller inputController = loader.getController();
            //Pass whatever data you want. You can have multiple method calls here
            inputController.transferPermit(LogIn);

            //Show scene 2 in new window
            //stage.setScene(scene);
            //stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }


}