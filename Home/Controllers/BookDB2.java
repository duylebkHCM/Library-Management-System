package Home.Controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Home.Controllers.Category2;
import Home.Controllers.CategoryDB;

import org.apache.poi.ss.usermodel.Cell;

public class BookDB2 {
	ArrayList<Book2> bookList = new ArrayList<>();
	String dbLocation = "/Home/File/Book_Database.xlsx";
	public void setDBLocation (String newLocation)
	{
		this.dbLocation = newLocation;
	}
	
	public int importBookList(String inputFile)  // return 0 if occurred invalid data type, otherwise return 1
	{
		/*
		try 
		{File newFile = new File(inputFile);
		{File newFile = new File(inputFile);
			if (!newFile.exists() || !newFile.isFile())
			{
				
			}
		FileInputStream inputStream = new FileInputStream(inputFile);
		XSSFWorkbook inworkbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = inworkbook.getSheetAt(0);
		CategoryDB catDB = new CategoryDB();
		catDB.readFile(dbLocation);
		int numOfRow = sheet.getPhysicalNumberOfRows();
		XSSFRow row;
		for (int i = 1 ;i< numOfRow;i++)
		{
			row = sheet.getRow(i);
			Book book = new Book();
			if (row != null) // check category valid or not
			{
				if (catDB.search(row.getCell(2).getStringCellValue()).initBookID()!=null)
				{
				book.setID(catDB.search(row.getCell(2).getStringCellValue()).initBookID());
				catDB.search(row.getCell(2).getStringCellValue()).setNum(1);
				}
			else return 0;
			for ( int j =4; j<=6;j++) // check amount, edition, price unsigned int or not
			{
				if (ControllerImportForm.tryParse(row.getCell(j).getStringCellValue()) < 0)
					return 0;
			}
				book.setTitle(row.getCell(0).getStringCellValue());
				book.setAuthor(row.getCell(1).getStringCellValue());
				book.setCategory(row.getCell(2).getStringCellValue());
				book.setPublisher(row.getCell(3).getStringCellValue());
				book.setEdition(ControllerImportForm.tryParse(row.getCell(4).getStringCellValue()));
				book.setTotalAmount(ControllerImportForm.tryParse(row.getCell(5).getStringCellValue()));
				book.setAvaiAmount(book.getTotalAmount());
				book.setPrice(ControllerImportForm.tryParse(row.getCell(6).getStringCellValue()));
		}
			this.bookList.set(i, book);
		}
		catDB.writeBack(dbLocation);
		inworkbook.close();
        inputStream.close();
		return 1;
	}catch(Exception ioe) 
	 	{
	    ioe.printStackTrace();
	    return 0;
		}
}
	public void writeback()
	{
		try 
		{FileInputStream inputStream = new FileInputStream(dbLocation);
		XSSFWorkbook inworkbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = inworkbook.getSheetAt(0);
		CategoryDB catDB = new CategoryDB();
		
		}
		*/
		return 0;
	}
	public int checkDB() throws IOException
	{
		//File newFile = new File(dbLocation);
		InputStream newFile= this.getClass().getResourceAsStream(dbLocation);
		if (newFile.available()==1)
		{
			return 1;
		}else
		{
			if (this.createDB() == 0) return 0;
		}
		return 1;
	}
	public int importBookForm(Book2 b)
	{
		try 
		{
			if (checkDB() == 0) return 0;
			//File newFile = new File(dbLocation);
			//FileInputStream inputStream = new FileInputStream(newFile);
			InputStream inputStream= this.getClass().getResourceAsStream(dbLocation);
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(0); // Sheet[0] is book DB,
			XSSFSheet sheet1 = workbook.getSheetAt(1);  //sheet[1] is categoryDB 
			CategoryDB catDB = new CategoryDB(); // read data from category DB
			int numOfRow = sheet1.getPhysicalNumberOfRows();
			for (int i = 1; i < numOfRow;i++)
			{
				Category2 cat = new Category2();
				XSSFRow row = sheet1.getRow(i);
				cat.setCategory(row.getCell(0).getStringCellValue());
				cat.setNum((int)row.getCell(1).getNumericCellValue());
				catDB.listCat.add(cat);
				System.out.println(cat.getNum());
			}
			int flag = 0; // check if this book have been in DB or not
			int rowNo ; //
			numOfRow = sheet.getPhysicalNumberOfRows();
			for (rowNo = 1;rowNo < numOfRow;rowNo++)
			{
				XSSFRow row = sheet.getRow(rowNo);
					if (b.getTitle().compareTo(row.getCell(1).getStringCellValue()) == 0)
						if( b.getCategory().compareTo(row.getCell(3).getStringCellValue()) == 0)
							if(b.getAuthor().compareTo(row.getCell(2).getStringCellValue()) == 0 )
								if (b.getPublisher().compareTo(row.getCell(4).getStringCellValue()) == 0 )
									if (b.getEdition().compareTo(row.getCell(5).getStringCellValue()) == 0
										&& b.getPrice() == (int)row.getCell(8).getNumericCellValue())
									{
										row.getCell(6).setCellValue(b.getTotalAmount() + row.getCell(6).getNumericCellValue());
										row.getCell(7).setCellValue(b.getTotalAmount() + row.getCell(7).getNumericCellValue());
										flag = 1; // this book existed in DB an DB have been updated
										break;
									}
									else continue;		
				}
			if (flag == 0) // otherwise
			{   Category2 c = catDB.search(b.getCategory());
				XSSFRow row = sheet.createRow(rowNo);
				row.createCell(0).setCellValue(c.initBookID());
				catDB.search(b.getCategory()).setNum(1);
				row.createCell(1).setCellValue(b.getTitle());
				row.createCell(2).setCellValue(b.getAuthor());
				row.createCell(3).setCellValue(b.getCategory());
				row.createCell(4).setCellValue(b.getPublisher());
				row.createCell(5).setCellValue(b.getEdition());
				row.createCell(6).setCellValue(b.getTotalAmount());
				row.createCell(7).setCellValue(b.getTotalAmount());
				row.createCell(8).setCellValue(b.getPrice());
				row.createCell(9).setCellValue(c.initBookLocation());
			}
			
			int numOfRow1 = sheet1.getPhysicalNumberOfRows();// write back to categoryDB
			for (int i = 1; i < numOfRow1;i++)
				{
				XSSFRow row = sheet1.getRow(i);
						row.getCell(1).setCellValue(catDB.listCat.get(i-1).getNum());
						System.out.println(row.getCell(1).getNumericCellValue());
				}
			OutputStream fileOut = new FileOutputStream(dbLocation);
			workbook.write(fileOut);
			fileOut.close();
			return 1;		}
		catch ( Exception ex ) {  // Error occurred
            System.out.println(ex);
            return 0;
		}
		
	}
	public int createDB()
	{try
	{
		//File newFile = new File(dbLocation);
		InputStream newFile= this.getClass().getResourceAsStream(dbLocation);
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet1 = workbook.createSheet("BookDB");
		
		XSSFRow rowhead = sheet1.createRow(0);
        rowhead.createCell(0).setCellValue("Boook_ID");
        rowhead.createCell(1).setCellValue("Book_Title");
        rowhead.createCell(2).setCellValue("Book_Author");
        rowhead.createCell(3).setCellValue("Book_Category");
        rowhead.createCell(4).setCellValue("Book_Publisher");
        rowhead.createCell(5).setCellValue("Book_Edition");
        rowhead.createCell(6).setCellValue("Total_Quantity");
        rowhead.createCell(7).setCellValue("Available_Quantity");
        rowhead.createCell(8).setCellValue("Price");
        rowhead.createCell(9).setCellValue("Position");
        
        XSSFSheet sheet2 = workbook.createSheet("CategoryDB"); 
        XSSFRow row9 = sheet2.createRow(0);
        row9.createCell(0).setCellValue("Category_Name");
        row9.createCell(1).setCellValue("Quantity");
        
        XSSFRow row0 = sheet2.createRow(1);
        row0.createCell(0).setCellValue("Business");
        row0.createCell(1).setCellValue(0);
        
        XSSFRow row1 = sheet2.createRow(2);
        row1.createCell(0).setCellValue("Comic");
        row1.createCell(1).setCellValue(0);
        
        XSSFRow row2 = sheet2.createRow(3);
        row2.createCell(0).setCellValue("Computer & Tech");
        row2.createCell(1).setCellValue(0);
        
        XSSFRow row3 = sheet2.createRow(4);
        row3.createCell(0).setCellValue("Edu & Reference");
        row3.createCell(1).setCellValue(0);
        
        XSSFRow row4 = sheet2.createRow(5);
        row4.createCell(0).setCellValue("Literature & Fiction");
        row4.createCell(1).setCellValue(0);
        
        XSSFRow row5 = sheet2.createRow(6);
        row5.createCell(0).setCellValue("Music & Art");
        row5.createCell(1).setCellValue(0);
        
        XSSFRow row6 = sheet2.createRow(7);
        row6.createCell(0).setCellValue("Magazine");
        row6.createCell(1).setCellValue(0);
        
        XSSFRow row7 = sheet2.createRow(8);
        row7.createCell(0).setCellValue("Science & Math");
        row7.createCell(1).setCellValue(0);
        
        XSSFRow row8 = sheet2.createRow(9);
        row8.createCell(0).setCellValue("Social Science");
        row8.createCell(1).setCellValue(0);
        
        OutputStream fileOut = new FileOutputStream(dbLocation);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        return 1;
	}
        catch ( Exception ex ) {  // Error occurred
            System.out.println(ex);
            return 0;
        }
	}

}