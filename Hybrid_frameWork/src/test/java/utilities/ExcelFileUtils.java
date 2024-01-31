package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtils {
Workbook wb;
//creating constructor to invoke excel sheet in every method.
public ExcelFileUtils(String excelpath) throws Throwable
	{
		FileInputStream fi=new FileInputStream(excelpath);
		wb=WorkbookFactory.create(fi);
		
	}
public int rowCount(String sheetName) 
{
    return wb.getSheet(sheetName).getLastRowNum();   
}
public String getCellData(String sheetName,int row,int column)
{
	String data="";
	if(wb.getSheet(sheetName).getRow(row).getCell(column).getCellType()==CellType.NUMERIC) 
	{
		int celldata=(int)wb.getSheet(sheetName).getRow(row).getCell(column).getNumericCellValue();
		data=String.valueOf(celldata);
	}else {
		data=wb.getSheet(sheetName).getRow(row).getCell(column).getStringCellValue();
		
	}
	return data;
}
public void setcelldata(String sheetName,int row,int column,String status,String restultpath) throws Throwable
{
	Sheet ws=wb.getSheet(sheetName);
	Row rowNUm=ws.getRow(row);
	Cell cell=rowNUm.getCell(column);
	cell.setCellValue(status);
     if(status.equalsIgnoreCase("pass")) 
     {
    	 CellStyle style=wb.createCellStyle();
    	 Font font=wb.createFont();
    	 font.setColor(IndexedColors.GREEN.getIndex());
    	  font.setBold(true);
    	  style.setFont(font);
    	  ws.getRow(row).getCell(column).setCellStyle(style);
     }
     else if(status.equalsIgnoreCase("fail")) 
     {
    	 CellStyle style=wb.createCellStyle();
    	 Font font=wb.createFont();
    	 font.setColor(IndexedColors.RED.getIndex());
    	  font.setBold(true);
    	  style.setFont(font);
    	  ws.getRow(row).getCell(column).setCellStyle(style);
     }
     else if(status.equalsIgnoreCase("blocked")) 
     {
    	 CellStyle style=wb.createCellStyle();
    	 Font font=wb.createFont();
    	 font.setColor(IndexedColors.PINK.getIndex());
    	  font.setBold(true);
    	  style.setFont(font);
    	  ws.getRow(row).getCell(column).setCellStyle(style);
     }
     FileOutputStream fo=new FileOutputStream(restultpath);
       wb.write(fo);      
}}
/*public static void main(String[] args) throws Throwable  {
	ExcelFileUtils xl=new ExcelFileUtils("E:/sample.xlsx");
	int rc=xl.rowCount("PLR");
	System.out.println(rc);
	
	for(int i=1;i<=rc;i++) 
	{
	xl.setcelldata("PLR", i, 4, "blocked", "E:/Result.xlsx");
	}
	for(int i=1;i<=rc;i++) 
	{
		String fname=xl.getCellData("PLR", i, 0);
		String mname=xl.getCellData("PLR", i, 2);
		String lname=xl.getCellData("PLR", i, 3);
		String empid=xl.getCellData("PLR", i, 4);
		System.out.println("{"+fname+"   "+mname+"   "+lname+"   "+empid+"}");
	}
	}

}*/
