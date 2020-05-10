package Home.Controllers;

import java.util.ArrayList;
import java.util.Iterator;
//import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CategoryDB {
 ArrayList<Category2> listCat = new ArrayList<Category2>();
public int readFile(String fileName)
 {
	 	//File file = new File(fileName);
		try 
		{InputStream inputStream= this.getClass().getResourceAsStream(fileName);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheetAt(1); // Sheet[0] is book DB, sheet[1] is categoryDB 
		int numOfCollumn = sheet.getRow(0).getLastCellNum(); // sheet[2]is borrower DB
		int numOfRow = sheet.getPhysicalNumberOfRows();
		XSSFRow row;
		for (int i = 1; i < numOfRow;i++)
		{
			Category2 cat = new Category2();
			row = sheet.getRow(i);
			if (row != null)
			{
				cat.setCategory(row.getCell(0).getStringCellValue());
				cat.setNum((int)row.getCell(1).getNumericCellValue());
			}
			this.listCat.add(cat);
		}
		/*Iterator<Row> iterator = sheet.iterator();
		while(iterator.hasNext())
			{
			Row nextRow = iterator.next();
			Category cat = new Category();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            while (cellIterator.hasNext()) 
            	{
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) 
                	{
                case STRING:
                		{cat.setCategory(cell.getStringCellValue());
                			break;
                		}
                case NUMERIC:
                		{cat.setNum((int)cell.getNumericCellValue());
                			break;
                		}
                	
                	}
            
            	}
            this.listCat.add(cat);
			}*/
		workbook.close();
        inputStream.close();
        return 1;
		} catch(Exception ioe) {
			 System.out.println(ioe);
			 return 0;
		}
 }
 public Category2 search(String catname)
 {
	 
	 for (int i = 0; i < this.listCat.size();i++)
	 {
		 if (this.listCat.get(i).getCategory().compareTo(catname) == 0)
		 {
			 return this.listCat.get(i);
		 }
	 }
	 return null;
 }
 public int writeBack(String fileName)
 {
	
	 try 
	 {
		File file = new File(fileName);
		InputStream inputStream= this.getClass().getResourceAsStream(fileName);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheetAt(1); 
		
		int numOfRow = sheet.getPhysicalNumberOfRows();
		for (int i = 1; i < numOfRow;i++)
			{
			XSSFRow row = sheet.getRow(i);
					row.getCell(1).setCellValue(listCat.get(i-1).getNum());
			}
		
		/*int numOfCollumn = sheet.getRow(0).getLastCellNum();
		Iterator<Row> iterator = sheet.iterator();
		while(iterator.hasNext())
			{
			Row nextRow = iterator.next();
			Category cat = new Category();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            while (cellIterator.hasNext()) 
            	{
                Cell cell = cellIterator.next();
            	}*/
        OutputStream outputStream = new FileOutputStream(fileName);
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
        return 1;
		}
		catch(Exception ioe) 
	 	{
			 System.out.println(ioe);
			 return 0;
		}
 }
}