package Home.Controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.aspose.cells.Cells;
import com.aspose.cells.DataSorter;
import com.aspose.cells.SortOrder;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;


public class ReportController implements Initializable {
	@FXML
	TextField BT,A;
	@FXML
	Pane BR,GR;
	@FXML
	StackPane Com,ComGen,Fail;
	@FXML
	ComboBox<String> cbSort;
	@FXML
	ComboBox<Number> cbNumber;

	@FXML
	TextField txtSearch;

	@FXML
	ImageView imgReturn;

	@FXML
	ImageView imgHome;

	@FXML
	AnchorPane rootanchor;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		BR.setVisible(false);
		Com.setVisible(false);
		Fail.setVisible(false);
		ComGen.setVisible(false);
		GR.setVisible(false);
		cbSort.getItems().clear();
		cbNumber.getItems().clear();
		cbNumber.getItems().addAll(
				10,20,30
		);
		cbSort.getItems().addAll(
				"Book Title",
				"Author",
				"Number"
		);
		
	}

	//Book Request
	private void inputData(String BookTitle, String Author) throws IOException{
		File excelFile = new File("/Home/File/database.xlsx");
		FileInputStream fis = new FileInputStream(excelFile);
		
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		if (!rowIterator.hasNext()) {
			XSSFRow row = sheet.createRow(0);		//new row
			
			XSSFCell cellBT = row.createCell(0);
			cellBT.setCellValue(BookTitle);
			XSSFCell cellA = row.createCell(1);
			cellA.setCellValue(Author);
			XSSFCell cellNum = row.createCell(2);
			cellNum.setCellValue(1);
		}
		else {
			int numRow = 0;
			boolean fought = false;
			while (rowIterator.hasNext()) {
				XSSFRow oldrow = (XSSFRow) rowIterator.next();
				Iterator<Cell> cellIterator = oldrow.cellIterator();
				XSSFCell oldcell = (XSSFCell) cellIterator.next();
				if (oldcell.getStringCellValue().compareTo(BookTitle) != 0) {numRow++ ; continue;}
				oldcell = (XSSFCell) cellIterator.next();
				if (oldcell.getStringCellValue().compareTo(Author) != 0)  {numRow++; continue; }
				oldcell = (XSSFCell) cellIterator.next();
				int num = (int)oldcell.getNumericCellValue();
				num++;
				oldcell.setCellValue(num);
				fought = true;
			}
			if (!fought) {
				XSSFRow row = sheet.createRow(numRow);
			
				XSSFCell cellBT = row.createCell(0);
				cellBT.setCellValue(BookTitle);
				XSSFCell cellA = row.createCell(1);
				cellA.setCellValue(Author);
				XSSFCell cellNum = row.createCell(2);
				cellNum.setCellValue(1);
			}
		}
		fis.close();
		FileOutputStream fos = new FileOutputStream(excelFile);
		workbook.write(fos);
		fos.close();
		workbook.close();
	}
	public void btnSum(ActionEvent event) throws Exception {
		if (!BT.getText().isEmpty() && !A.getText().isEmpty()) {
			inputData(BT.getText(),A.getText());
			Com.setVisible(true);
		}
		else Fail.setVisible(true);
		BT.clear(); A.clear();
	}
	public void btnComSum(ActionEvent event) {
		Com.setVisible(false);
	}
	public void btnFailSum(ActionEvent event) {
		Fail.setVisible(false);
	}
	
	//Generate report
	private String FontFile= "/Home/File/vuTimes.ttf";
	private String FontBold= "/Home/File/vuTimesBold.ttf";
	private void format(Document report) throws Exception {
		String text1 = "Cong hoa Xa hoi Chu nghia Viet Nam";
		String text2 = "Doc lap - Tu do - Hanh phuc";
		String text3 = "DANH MUC SACH CAN NHAP";
		String text4 = "Bang danh sach nhung tua sach thu vien can nhap ve: ";
		
		Font vnFont = new Font(BaseFont.createFont(FontFile, BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
		Font vnBold = new Font(BaseFont.createFont(FontBold, BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
		
		Chunk chunk1 = new Chunk(text1);
		chunk1.setFont(vnFont);
		Paragraph para1 = new Paragraph(chunk1);
		para1.setAlignment(Paragraph.ALIGN_CENTER);
		report.add(para1);
		
		Chunk chunk2 = new Chunk(text2);
		chunk2.setFont(vnFont);
		Paragraph para2 = new Paragraph(chunk2);
		para2.setAlignment(Paragraph.ALIGN_CENTER);
		para2.setSpacingAfter(25);
		report.add(para2);
		
		Chunk chunk3 = new Chunk(text3);
		Font headerFont = new Font(BaseFont.createFont(FontFile, BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
		headerFont.setSize(20f);
		chunk3.setFont(headerFont);
		Paragraph para3 = new Paragraph(chunk3);
		para3.setAlignment(Paragraph.ALIGN_CENTER);
		para3.setSpacingAfter(50);
		report.add(para3);
		
		Chunk chunk4 = new Chunk(text4);
		chunk4.setFont(vnFont);
		Paragraph para4 = new Paragraph(chunk4);
		para4.setIndentationLeft(80f);
		para4.setSpacingAfter(10f);
		report.add(para4);
		
		Paragraph para5 = new Paragraph();
		para5.setIndentationLeft(75f);
		para5.setTabSettings(new TabSettings(150f));
		Chunk chunkBT = new Chunk("Ten sach");
		chunkBT.setFont(vnBold);
		para5.add(chunkBT);
		Chunk chunkA = new Chunk("Tac gia");
		chunkA.setFont(vnFont);
		para5.add(Chunk.TABBING);
		para5.add(chunkA);
		Chunk chunkN = new Chunk("So luong");
		chunkN.setFont(vnFont);
		para5.add(Chunk.TABBING);
		para5.add(chunkN);
		report.add(para5);
	}
	private void inputReport(Document report, XSSFRow row) throws DocumentException, IOException {
		Iterator<Cell> cellIterator = row.cellIterator();
		Paragraph para = new Paragraph();
		para.setIndentationLeft(75f);		
		para.setTabSettings(new TabSettings(150f));
		
		Font vnFont = new Font(BaseFont.createFont(FontFile,BaseFont.IDENTITY_H,BaseFont.EMBEDDED));
		
		String BT = cellIterator.next().getStringCellValue();
		Chunk chunkBT = new Chunk(BT);
		chunkBT.setFont(vnFont);
		para.add(chunkBT);
		
		String A = cellIterator.next().getStringCellValue();
		Chunk chunkA = new Chunk(A);
		chunkA.setFont(vnFont);
		para.add(Chunk.TABBING);
		para.add(chunkA);
		
		int Num = (int) cellIterator.next().getNumericCellValue();
		Chunk chunkNum = new Chunk(Integer.toString(Num));
		chunkNum.setFont(vnFont);
		para.add(Chunk.TABBING);
		para.add(chunkNum);
		
		report.add(para);
	}
	//  void sort() used to sort our database in order of SortBy
	private void sort(String SortBy) throws Exception  {
		Workbook workbook = new Workbook("/Home/File/database.xlsx");
		Worksheet sheet = workbook.getWorksheets().get(0);
		Cells cells = sheet.getCells();
		int numRow = cells.getLastCell().getRow();
		DataSorter sorter = workbook.getDataSorter();
		if (SortBy.compareTo("Number") == 0) {
			sorter.setOrder1(SortOrder.DESCENDING);
			sorter.setKey1(2);
			sorter.sort(cells, 0, 0, numRow , 2);
		}
		if (SortBy.compareTo("Book Title") == 0) {
			sorter.setOrder1(SortOrder.ASCENDING);
			sorter.setKey1(0);
			sorter.sort(cells, 0, 0, numRow, 2);
		}
		if (SortBy.compareTo("Author") == 0) {
			sorter.setOrder1(SortOrder.ASCENDING);
			sorter.setKey1(1);
			sorter.sort(cells, 0, 0, numRow, 2);
		}
		
		workbook.save("/Home/File/database.xlsx");
	}
	//format Report after input Data
	private void footer(Document report) throws DocumentException, IOException {
		Font vnFont = new Font(BaseFont.createFont(FontFile, BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
		//Font vnBold = new Font(BaseFont.createFont(FontBold, BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
		
		Paragraph space = new Paragraph();
		space.setSpacingAfter(25f);
		report.add(space);
		
		Paragraph para1 = new Paragraph();
		para1.setIndentationLeft(300f);
		para1.setTabSettings(new TabSettings(30f));
		
		Chunk day = new Chunk("Ngay");
		day.setFont(vnFont);
		para1.add(day);
		Chunk month = new Chunk("Thang");
		month.setFont(vnFont);
		para1.add(Chunk.TABBING);
		para1.add(Chunk.TABBING);
		para1.add(month);
		Chunk year = new Chunk("Nam");
		year.setFont(vnFont);
		para1.add(Chunk.TABBING);
		para1.add(year);
		
		report.add(para1);
		
		Paragraph para2 = new Paragraph();
		para2.setIndentationLeft(350f);
		Chunk signature = new Chunk("Thu thu:");
		signature.setFont(vnFont);
		para2.add(signature);
		report.add(para2);
	}
	private void makeReport() throws IOException, Exception {
		File excelFile = new File("/Home/File/database.xlsx");
		FileInputStream fis = new FileInputStream(excelFile);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row>  rowIterator = sheet.iterator();
		
		Document report = new Document();
		PdfWriter.getInstance(report,new FileOutputStream("D:\\BookOrder.pdf"));
		report.open();
		format(report);
		String SortBy = cbSort.getValue();
		int num = cbNumber.getValue().intValue();
		sort(SortBy);
		while(rowIterator.hasNext() && num>0) {
			XSSFRow row = (XSSFRow) rowIterator.next();
			inputReport(report,row);
			num--;
		}
		footer(report);
		report.close();
		workbook.close();
		fis.close();
	}
	//Book Request
	public void btnBR(ActionEvent event) {
		BR.setVisible(true);
		GR.setVisible(false);
	}
	
	//Generate Report
	@FXML
	public void btnGR(ActionEvent event) {
		GR.setVisible(true);
	}

	@FXML
	public void btnG(ActionEvent event) throws IOException, Exception {
		makeReport();
		makeReport();
		ComGen.setVisible(true);
	}

	@FXML
	public void btnComG(ActionEvent event) {
		ComGen.setVisible(false);
		GR.setVisible(false);
	}

	@FXML
	Button btnHome;
	
	@FXML
	public void btnH(MouseEvent event) throws Exception {
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
            inputController.transferPermit("true");

            //Show scene 2 in new window
            //stage.setScene(scene);
            //stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }
		
	}
	@FXML
	public void onbtnEnter(MouseEvent e) {
		Button button = (Button) e.getSource();
		button.setStyle("-fx-background-color: #009999");
	}


	@FXML
	public void onbtnLeave(MouseEvent e) {
		Button button = (Button) e.getSource();
		button.setStyle("-fx-background-color:  #0373BD");

	}
}
