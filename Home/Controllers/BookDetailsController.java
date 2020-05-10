package Home.Controllers;

import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import Home.AlertBox;
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


public class BookDetailsController implements Initializable {

    String LogIn;

    @FXML
    //Receive message from scene 1
    public void transferPermit(String _per){
        //Display the message
        LogIn = _per;
    }

   @FXML
   Label label1;
   @FXML
   MenuButton menuCat;
   @FXML
   RadioMenuItem itemSci,  itemMaga, itemMusic, itemLit, itemEdu, itemComputer, itemComic, itemBusiness, itemSocial;
   @FXML
   ToggleGroup Category = new ToggleGroup();
   @FXML
   TextField txtTitle,txtAuthor,txtEdition,txtQuan, txtPublisher, txtPrice;
   @FXML
   Button btnOK;
   Book temp, tempBook ;
   String tempCat;


   @Override
   public void initialize(URL location, ResourceBundle resources) {

      label1.setVisible(false);
      txtPublisher.setDisable(true);
      txtTitle.setDisable(true);
      txtEdition.setDisable(true);
      txtAuthor.setDisable(true);
      txtPrice.setDisable(true);
      txtQuan.setDisable(true);
      menuCat.setDisable(true);

       Category.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
           public void changed(ObservableValue<? extends Toggle> ov,
                               Toggle old_toggle, Toggle new_toggle) {
               if (Category.getSelectedToggle() != null) {
                   menuCat.setText(getCat());
               }
           }
       });
   }
    void initData(Book book) {
        tempBook = new Book(book);
        txtTitle.setText(book.getBookTitle());
        String tempPrice = String.format("%10.0f",book.getBookPrice());
        txtPrice.setText(tempPrice );
        txtAuthor.setText(book.getBookAuthor());
        txtEdition.setText(book.getBookEdition());
        String tempQuan = String.format("%d",book.getBookAvlbAmt());
        txtQuan.setText(tempQuan);
        txtPublisher.setText(book.getBookPublisher());
        menuCat.setText(book.getBookCat());
        switch(book.getBookCat()) {
            case "Comic": itemComic.setSelected(true); break;
            case "Social Science": itemSocial.setSelected(true);break;
            case "Magazine": itemMaga.setSelected(true);break;
            case "Music & Art": itemMusic.setSelected(true);break;
            case "Business": itemBusiness.setSelected(true);break;
            case "Computer & Tech": itemComputer.setSelected(true);break;
            case "Edu & Reference": itemEdu.setSelected(true);break;
            case "Literature & Fiction":  itemLit.setSelected(true);break;
            case "Science & Math": itemSci.setSelected(true);break;
        }


        temp = new Book(book.getBookID(),0);
        tempCat = book.getBookCat();
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
        Object catItem = Category.getSelectedToggle().getUserData();
        menuCat.setText(catItem.toString());
        return catItem.toString();
    }
    public void onbtnEnter(MouseEvent e) {
        Button button = (Button)e.getSource();
        Glow glowfx = new Glow();
        glowfx.setLevel(1);
        DropShadow dropShadow = new DropShadow();
        //dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setColor(Color.LIGHTGRAY);
        dropShadow.setHeight(20);
        dropShadow.setWidth(20);
        dropShadow.setRadius(5);
        dropShadow.setSpread(10);
        button.setEffect(dropShadow);
        switch(button.getId()){
            case "btnMod":
                label1.setVisible(true);
                break;
            case "btnOK":
                break;
        }

   }

    @FXML
    public void onbtnLeave(MouseEvent e) {
        Button button = (Button)e.getSource();
        Glow glowfx = new Glow();
        glowfx.setLevel(0);
        button.setEffect(null);
        switch(button.getId()){
            case "btnMod":
                label1.setVisible(false);
                break;
            case "btnOK":
                break;
        }
    }

    void updateBook(){
        temp.setBookTitle(txtTitle.getText());
        temp.setBookAuthor(txtAuthor.getText());
        temp.setBookEdition(txtEdition.getText());
        temp.setBookCat(getCat());
        temp.setBookPublisher(txtPublisher.getText());
        temp.setBookPrice(Float.parseFloat(txtPrice.getText()));
        temp.setBookAvlbAmt(Integer.parseInt(txtQuan.getText()));
    }
    private String headID;
    void updateDB()throws IOException {
        String path = "/Home/File/Book_Database.xlsx";
        //File excelFile = new File(path);
        //FileInputStream fis = new FileInputStream(excelFile);
        InputStream fis= this.getClass().getResourceAsStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows();

        for (int i = 1; i < rows; i++) {
            Row row = sheet.getRow(i);


            if (temp.getBookID(). equals(row.getCell(0).getStringCellValue())){

                row.getCell(1).setCellValue(temp.getBookTitle());
                row.getCell(2).setCellValue(temp.getBookAuthor());
                row.getCell(3).setCellValue(temp.getBookCat());
                row.getCell(4).setCellValue(temp.getBookPublisher());
                //row.getCell(5).setCellValue(temp.getBookEdition());
                row.getCell(7).setCellValue(temp.getBookAvlbAmt());
                row.getCell(8).setCellValue(temp.getBookPrice());

            }

        }
        fis.close();
        OutputStream fos = new FileOutputStream(path);
        workbook.write(fos);
        workbook.close();
        fos.close();
    }

    public void onbtnClick(MouseEvent e) throws Exception {
        Button button = (Button)e.getSource();
        switch (button.getId()){
            case "btnMod":
                if(LogIn.matches("false")){
                    AlertBox box = new AlertBox();
                    box.display("Inaccessible","Log in as librarian to access this.");
                }
                else {
                    txtPublisher.setDisable(false);
                    txtTitle.setDisable(false);
                    txtEdition.setDisable(false);
                    txtAuthor.setDisable(false);
                    txtPrice.setDisable(false);
                    txtQuan.setDisable(false);
                    //menuCat.setDisable(false);
                }
                break;
            case "btnOK":
                updateBook();
                if(LogIn.matches("true") && !temp.CompareBook(tempBook)){
                    updateDB();
                }
                Stage stage = (Stage) btnOK.getScene().getWindow();
                stage.close();
                break;

        }
    }
  
}