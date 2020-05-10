package Home.Controllers;

import Home.AlertBox;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import Home.Controllers.Borrower;

public class IssueBD {
    private static final int LIMITROW = 3;


    public static int addRecord(String _BookID, Borrower _borrower) throws Exception {
        String DBpath = "/Home/File/Borrower_Database.xlsx";

        FileInputStream excelFile = null;
        XSSFWorkbook workbook = null;
        // excel files

        boolean exist = new File(DBpath).exists();
        if (exist) {
            File file = new File(DBpath);

            excelFile = new FileInputStream(file);
            workbook = new XSSFWorkbook(excelFile);

        } else {
            workbook = new XSSFWorkbook();
        }

        // Create Workbook instance holding reference to .xlsx file


        // Get desired sheet from the workbook
        XSSFSheet sheet = null;

        //Check if the sheet exist
        boolean sheet_exist = false;
        if (workbook.getNumberOfSheets() != 0) {
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                if (workbook.getSheetAt(i).getSheetName().matches(_BookID)) {
                    sheet = workbook.getSheet(_BookID);
                    sheet_exist = true;
                }
            }

            if (!sheet_exist) {
                sheet = workbook.createSheet(_BookID);
                Row row = sheet.createRow(0);
                Cell cell = row.createCell(0);
                cell.setCellValue("#Book_ID");

                cell = row.createCell(1);
                cell.setCellValue("#Name");

                cell = row.createCell(2);
                cell.setCellValue("#ID");

                cell = row.createCell(3);
                cell.setCellValue("#Phone");

                cell = row.createCell(4);
                cell.setCellValue("#Address");

                cell = row.createCell(5);
                cell.setCellValue("#Email");

                cell = row.createCell(6);
                cell.setCellValue("#Issue_Date");

                cell = row.createCell(7);
                cell.setCellValue("#Due_Date");

                cell = row.createCell(8);
                cell.setCellValue("#Status");

                cell = row.createCell(9);
                cell.setCellValue("#Issue_Code");

                cell = row.createCell(10);
                cell.setCellValue("#Return_Date");

            }
        } else {
            // Create new sheet to the workbook if empty
            sheet = workbook.createSheet(_borrower.BookID+"");
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("#Book_ID");

            cell = row.createCell(1);
            cell.setCellValue("#Name");

            cell = row.createCell(2);
            cell.setCellValue("#ID");

            cell = row.createCell(3);
            cell.setCellValue("#Phone");

            cell = row.createCell(4);
            cell.setCellValue("#Address");

            cell = row.createCell(5);
            cell.setCellValue("#Email");

            cell = row.createCell(6);
            cell.setCellValue("#Issue_Date");

            cell = row.createCell(7);
            cell.setCellValue("#Due_Date");

            cell = row.createCell(8);
            cell.setCellValue("#Status");

            cell = row.createCell(9);
            cell.setCellValue("#Issue_Code");

            cell = row.createCell(10);
            cell.setCellValue("#Return_Date");
        }


        int dest_row = sheet.getLastRowNum() + 1;
        sheet.createRow(dest_row);

        Row row = sheet.getRow(dest_row);
        Cell cell = row.createCell(0);
        cell.setCellValue(_borrower.BookID);

        cell = row.createCell(1);
        cell.setCellValue(_borrower.Name);

        cell = row.createCell(2);
        cell.setCellValue(_borrower.ID);

        cell = row.createCell(3);
        cell.setCellValue(_borrower.Phone);

        cell = row.createCell(4);
        cell.setCellValue(_borrower.Email);

        cell = row.createCell(5);
        cell.setCellValue(_borrower.Address);

        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

        cell = row.createCell(6);
        cell.setCellValue(_borrower.IssueDate);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(7);
        cell.setCellValue(_borrower.DueDate);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(8);
        cell.setCellValue("Issued");

        cell = row.createCell(9);
        cell.setCellValue(_BookID+"_" + dest_row);

        cell = row.createCell(10);
        cell.setCellValue(_borrower.IssueDate);
        cell.setCellStyle(cellStyle);

        if (exist) {
            excelFile.close(); //Close the InputStream
        }

        FileOutputStream output_file = new FileOutputStream(new File(DBpath));  //Open FileOutputStream to write updates

        workbook.write(output_file); //write changes

        output_file.close();

        return dest_row;
    }

    public static int updateStatus(String _BookID, String txtIssue_Code, LocalDate _ReturnDate) throws Exception {
        String DBpath = "/Home/File/Book_Database.xlsx";
        Book target = new Book();

        // excel files
        FileInputStream excelFile = new FileInputStream(new File(DBpath));

        // Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook = new XSSFWorkbook(excelFile);

        // Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheet(_BookID+"");
        if (sheet == null)
            return 0;

        int n_row = Integer.parseInt(txtIssue_Code.replace(_BookID+"_", ""));


        if(n_row > sheet.getLastRowNum())
            return 0;

        Cell cell = sheet.getRow(n_row).getCell(8);
        if(cell.getStringCellValue().matches("Returned"))
            return -1;

        cell.setCellValue("Returned");

        if(sheet.getRow(n_row).getCell(10) == null)
            cell = sheet.getRow(n_row).createCell(10);
        else
            cell = sheet.getRow(n_row).getCell(10);

        Instant instant2 = Instant.from(_ReturnDate.atStartOfDay(ZoneId.systemDefault()));
        cell.setCellValue(java.util.Date.from(instant2));

        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

        cell.setCellStyle(cellStyle);

        excelFile.close();

        FileOutputStream output_file =new FileOutputStream(new File(DBpath));  //Open FileOutputStream to write updates

        workbook.write(output_file); //write changes

        output_file.close();

        return n_row;

    }

    public static ArrayList<Borrower> findRecordList(String _Field, String _input, String _opID) throws Exception {
        String DBpath = "/Home/File/Borrower_Database.xlsx";

        FileInputStream excelFile = null;
        XSSFWorkbook workbook = null;
        // excel files

        File file = new File(DBpath);

        excelFile = new FileInputStream(file);
        workbook = new XSSFWorkbook(excelFile);

        // Get desired sheet from the workbook
        XSSFSheet sheet = null;


        ArrayList<Borrower> list = new ArrayList<>();
        // Create Workbook instance holding reference to .xlsx file
        switch (_Field){
            case "BookID":
                if (workbook.getNumberOfSheets() != 0) {
                    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                        if (workbook.getSheetAt(i).getSheetName().matches(_input)) {
                            sheet = workbook.getSheet(_input);
                            break;
                        }
                    }
                }

                if(sheet != null) {
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        Borrower tmpBorrower = new Borrower();
                        Cell cell = row.getCell(0);
                        tmpBorrower.BookID = cell.getStringCellValue();


                        cell = row.getCell(1);
                        tmpBorrower.Name = cell.getStringCellValue();

                        cell = row.getCell(2);
                        tmpBorrower.ID = cell.getStringCellValue();

                        cell = row.getCell(3);
                        tmpBorrower.Phone = cell.getStringCellValue();

                        cell = row.getCell(4);
                        tmpBorrower.Email = cell.getStringCellValue();

                        cell = row.getCell(5);
                        tmpBorrower.Address = cell.getStringCellValue();

                        cell = row.getCell(6);
                        tmpBorrower.IssueDate = cell.getDateCellValue();

                        cell = row.getCell(7);
                        tmpBorrower.DueDate = cell.getDateCellValue();

                        cell = row.getCell(8);
                        tmpBorrower.Status = cell.getStringCellValue();
                        System.out.println(i+" "+tmpBorrower.IssueCode);

                        cell = row.getCell(9);
                        tmpBorrower.IssueCode = cell.getStringCellValue();

                        cell = row.getCell(10);
                        tmpBorrower.ReturnDate = cell.getDateCellValue();
                        list.add(tmpBorrower);
                    }
                }
                break;
            case "Due Date":
                if (workbook.getNumberOfSheets() != 0) {
                    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                        sheet = workbook.getSheetAt(i);

                        for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                            Row row = sheet.getRow(j);
                            Cell cell = row.getCell(7);
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                            String date = formatter.format(cell.getDateCellValue());

                            if(date.matches(_input)) {
                                Borrower tmpBorrower = new Borrower();
                                cell = row.getCell(0);
                                tmpBorrower.BookID = cell.getStringCellValue();

                                cell = row.getCell(1);
                                tmpBorrower.Name = cell.getStringCellValue();

                                cell = row.getCell(2);
                                tmpBorrower.ID = cell.getStringCellValue();

                                cell = row.getCell(3);
                                tmpBorrower.Phone = cell.getStringCellValue();

                                cell = row.getCell(4);
                                tmpBorrower.Email = cell.getStringCellValue();

                                cell = row.getCell(5);
                                tmpBorrower.Address = cell.getStringCellValue();

                                cell = row.getCell(6);
                                tmpBorrower.IssueDate = cell.getDateCellValue();

                                cell = row.getCell(7);
                                tmpBorrower.DueDate = cell.getDateCellValue();

                                cell = row.getCell(8);
                                tmpBorrower.Status = cell.getStringCellValue();

                                cell = row.getCell(9);
                                tmpBorrower.IssueCode = cell.getStringCellValue();

                                cell = row.getCell(10);
                                tmpBorrower.ReturnDate = cell.getDateCellValue();
                                list.add(tmpBorrower);
                            }

                        }
                    }
                }
                break;
            case "Return Date":
                if (workbook.getNumberOfSheets() != 0) {
                    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                        sheet = workbook.getSheetAt(i);

                        for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                            Row row = sheet.getRow(j);
                            Cell cell = row.getCell(10);
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                            String date = formatter.format(cell.getDateCellValue());

                            if(date.matches(_input)) {
                                Borrower tmpBorrower = new Borrower();
                                cell = row.getCell(0);
                                tmpBorrower.BookID = cell.getStringCellValue();

                                cell = row.getCell(1);
                                tmpBorrower.Name = cell.getStringCellValue();

                                cell = row.getCell(2);
                                tmpBorrower.ID = cell.getStringCellValue();

                                cell = row.getCell(3);
                                tmpBorrower.Phone = cell.getStringCellValue();

                                cell = row.getCell(4);
                                tmpBorrower.Email = cell.getStringCellValue();

                                cell = row.getCell(5);
                                tmpBorrower.Address = cell.getStringCellValue();

                                cell = row.getCell(6);
                                tmpBorrower.IssueDate = cell.getDateCellValue();

                                cell = row.getCell(7);
                                tmpBorrower.DueDate = cell.getDateCellValue();

                                cell = row.getCell(8);
                                tmpBorrower.Status = cell.getStringCellValue();

                                cell = row.getCell(9);
                                tmpBorrower.IssueCode = cell.getStringCellValue();

                                cell = row.getCell(10);
                                tmpBorrower.ReturnDate = cell.getDateCellValue();
                                list.add(tmpBorrower);
                            }

                        }
                    }
                }
                break;
            case "Issue Date":
                if (workbook.getNumberOfSheets() != 0) {
                    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                        sheet = workbook.getSheetAt(i);

                        for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                            Row row = sheet.getRow(j);
                            Cell cell = row.getCell(6);
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                            String date = formatter.format(cell.getDateCellValue());

                            if(date.matches(_input)) {
                                Borrower tmpBorrower = new Borrower();
                                cell = row.getCell(0);
                                tmpBorrower.BookID = cell.getStringCellValue();

                                cell = row.getCell(1);
                                tmpBorrower.Name = cell.getStringCellValue();

                                cell = row.getCell(2);
                                tmpBorrower.ID = cell.getStringCellValue();

                                cell = row.getCell(3);
                                tmpBorrower.Phone = cell.getStringCellValue();

                                cell = row.getCell(4);
                                tmpBorrower.Email = cell.getStringCellValue();

                                cell = row.getCell(5);
                                tmpBorrower.Address = cell.getStringCellValue();

                                cell = row.getCell(6);
                                tmpBorrower.IssueDate = cell.getDateCellValue();

                                cell = row.getCell(7);
                                tmpBorrower.DueDate = cell.getDateCellValue();

                                cell = row.getCell(8);
                                tmpBorrower.Status = cell.getStringCellValue();

                                cell = row.getCell(9);
                                tmpBorrower.IssueCode = cell.getStringCellValue();

                                cell = row.getCell(10);
                                tmpBorrower.ReturnDate = cell.getDateCellValue();
                                list.add(tmpBorrower);
                            }

                        }
                    }
                }
                break;
            case "Status":
                if (workbook.getNumberOfSheets() != 0) {

                    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                        if(_opID.matches("0")) {
                            sheet = workbook.getSheetAt(i);
                        }
                        else{
                            sheet = workbook.getSheet(_opID);
                        }

                            for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                                Row row = sheet.getRow(j);
                                Cell cell = row.getCell(8);

                                Borrower tmpBorrower = new Borrower();
                                if (cell.getStringCellValue().matches(_input)) {

                                    cell = row.getCell(0);
                                    tmpBorrower.BookID = cell.getStringCellValue();

                                    cell = row.getCell(1);
                                    tmpBorrower.Name = cell.getStringCellValue();

                                    cell = row.getCell(2);
                                    tmpBorrower.ID = cell.getStringCellValue();

                                    cell = row.getCell(3);
                                    tmpBorrower.Phone = cell.getStringCellValue();

                                    cell = row.getCell(4);
                                    tmpBorrower.Email = cell.getStringCellValue();

                                    cell = row.getCell(5);
                                    tmpBorrower.Address = cell.getStringCellValue();

                                    cell = row.getCell(6);
                                    tmpBorrower.IssueDate = cell.getDateCellValue();

                                    cell = row.getCell(7);
                                    tmpBorrower.DueDate = cell.getDateCellValue();

                                    cell = row.getCell(8);
                                    tmpBorrower.Status = cell.getStringCellValue();

                                    cell = row.getCell(9);
                                    tmpBorrower.IssueCode = cell.getStringCellValue();

                                    cell = row.getCell(10);
                                    tmpBorrower.ReturnDate = cell.getDateCellValue();
                                    list.add(tmpBorrower);
                                }

                            }
                        if(_opID.matches("0")) {
                            continue;
                        }
                        else{
                            break;
                        }
                    }

                }
                break;

        }
        excelFile.close();
        return list;

    }

    public static boolean resetBorrowerList(String _BookID)throws Exception {
        String DBpath = "/Home/File/Borrower_Database.xlsx";

        FileInputStream excelFile = null;
        XSSFWorkbook workbook = null;
        // excel files



        boolean exist = new File(DBpath).exists();
        if (exist) {
            File file = new File(DBpath);

            excelFile = new FileInputStream(file);
            workbook = new XSSFWorkbook(excelFile);

        } else {
            return false;
        }

        // Create Workbook instance holding reference to .xlsx file


        // Get desired sheet from the workbook
        XSSFSheet sheet = null;

        //Check if the sheet exist
        boolean sheet_exist = false;
        if (workbook.getNumberOfSheets() !=0) {
            if(workbook.getNumberOfSheets() == 1)
                workbook.createSheet();
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                if (workbook.getSheetAt(i).getSheetName().matches(_BookID)) {
                    workbook.removeSheetAt(i);

                    File file = new File(DBpath);
                    FileOutputStream output = new FileOutputStream(file);
                    workbook.write(output);
                    output.close();
                    return true;
                }
            }
        }
        File file = new File(DBpath);
        FileOutputStream output = new FileOutputStream(file);
        workbook.write(output);
        output.close();
        return false;


    }
    
}








