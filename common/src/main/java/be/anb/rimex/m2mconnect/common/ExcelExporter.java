package be.anb.rimex.m2mconnect.common;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter {
	
	private static final Logger logger = LogManager.getLogger(ExcelExporter.class);
	public static void exportToExcel(List<String[]> data, File file) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Invoice_Subscriptions");
		
		int rowCount = 0;
		for (String[] rowData : data) {
			XSSFRow row = sheet.createRow(rowCount++);
			int columnCount = 0;
			for (String cellData : rowData) {
				XSSFCell cell = row.createCell(columnCount++);
				if(columnCount > 2) {
					try {
						double numericValue = Double.parseDouble(cellData);
						cell.setCellValue(numericValue);
						
					} catch (NumberFormatException e) {
						cell.setCellValue(cellData);
					}
				}
				else{
					cell.setCellValue(cellData);
				}
			}
		}
		
		for (int i = 0; i < data.get(0).length; i++) {
			sheet.setColumnWidth(i, 5000); //Size column
		}
		saveDataInFile(workbook, file);
	}
	
	
	private static void saveDataInFile(XSSFWorkbook workbook, File file) throws IOException{
		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			workbook.write(outputStream);
			logger.info("File created with success !");
		} catch (IOException e) {
			logger.error("IOException..." + e.getMessage(), e);
			throw e;
		}
	}

}
