package Home.Controllers;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import Home.Controllers.Book;

public class BookDB {

    public static int issue_return(String _ID, String _Type) throws Exception
    {
        String DBpath = "/Home/File/Book_Database.xlsx";
        Book target = new Book();

        // excel files
        //FileInputStream excelFile = new FileInputStream(new File(DBpath));
        InputStream excelFile= BookDB.class.getResourceAsStream(DBpath);
        // Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook = new XSSFWorkbook(excelFile);

        // Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);

        ArrayList<Row> filteredRows = new ArrayList<Row>();
        int row_num = 0;

        for (Row row : sheet) {
            Cell cell = row.getCell(0);
            if (cell.getCellType() == CellType.STRING) {
                if (cell.getStringCellValue().equals(_ID)) {
                    row_num = row.getRowNum();
                }
            }
        }

        Row row = sheet.getRow(row_num);
        if(row_num != 0) {

//            System.console().printf("row_num != 0");
            Cell cell = row.getCell(7);
            int quant = 0;
            switch (_Type) {
                case "Issue":
                    quant = ((int) cell.getNumericCellValue()) - 1;
                    cell.setCellValue(quant);
                    break;
                case "Return":
                    quant = ((int) cell.getNumericCellValue()) + 1;
                    cell.setCellValue(quant);
                    break;
            }
        }

        excelFile.close(); //Close the InputStream

        OutputStream output_file =new FileOutputStream(DBpath);  //Open FileOutputStream to write updates
        //= BookDB.class.
        workbook.write(output_file); //write changes

        output_file.close();
        return (int)row.getCell(6).getNumericCellValue();
    }



    public static Book searchID(String _ID) throws Exception
    {
        String DBpath = "/Home/File/Book_Database.xlsx";
        Book target = new Book();

        // excel files
        //FileInputStream excelFile = new FileInputStream(new File(DBpath));
        InputStream excelFile= BookDB.class.getResourceAsStream(DBpath);
        // Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook = new XSSFWorkbook(excelFile);

        // Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);

        int row_num = 0;

        for (Row row : sheet) {
            Cell cell = row.getCell(0);
            if (cell.getCellType() == CellType.STRING) {
                if (cell.getStringCellValue().equals(_ID)) {
                    row_num = row.getRowNum();
                }
            }
        }

        Row row = sheet.getRow(row_num);


        if(row_num != 0) {

            target.ID = row.getCell(0).getStringCellValue();
            target.Title = row.getCell(1).getStringCellValue();
            target.Author = row.getCell(2).getStringCellValue();
            target.Category = row.getCell(3).getStringCellValue();
            target.Publisher = row.getCell(4).getStringCellValue();
            target.Edition = row.getCell(5).getStringCellValue();
            target.Total_quantity = ((int) row.getCell(6).getNumericCellValue());
            target.Available_quantity = ((int)row.getCell(7).getNumericCellValue());
            target.Price = ((int)row.getCell(8).getNumericCellValue());
            //target.Position = row.getCell(9).getStringCellValue();
        }
        else {
            target.Title = "";
        }


        excelFile.close(); //Close the InputStream

        return target;

    }
    private int bookNumber;
    public Book[] Booklist;

    public BookDB(int num ){
        Booklist = new Book[num];
        bookNumber = num;
    }

    public int getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(int bookNumber) {
        this.bookNumber = bookNumber;
    }
}

