package Q1Answer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteTransactionToexcel {
	public static void writeToExcel(String fileName, Map<String,String> trannsactionData) {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet= workbook.createSheet(fileName);
		Set<String> keySet = trannsactionData.keySet();
		XSSFCellStyle style = workbook.createCellStyle();
		style.setWrapText(true);

		int rownum = 0;

		for (String key : keySet) {
			Row row = spreadsheet.createRow(rownum++);
			String value = trannsactionData.get(key);
			Cell keyCell = row.createCell(0);
			keyCell.setCellValue(key);
			Cell valueCell = row.createCell(1);
			valueCell.setCellValue(value);

		}

		try {
			//Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File(fileName.concat(".xlsx")));
			workbook.write(out);
			out.close();
			System.out.println("Excel with "+fileName+"  created successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				workbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
