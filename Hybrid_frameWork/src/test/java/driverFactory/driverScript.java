package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.functionLibrary;
import utilities.ExcelFileUtils;

public class driverScript {
public static WebDriver driver;
String msheet="MasterTestCases";
String inputpath="./FileInput/controller.xlsx";
String outputpath="./FileOutput/HybridResult.xlsx";
ExtentReports report;
ExtentTest logger;
public  void startTest() throws Throwable {
	String module_status="";
	//creating obj for excelfile
	ExcelFileUtils xl=new ExcelFileUtils(inputpath);
	//iterating msheet
	for(int i=1;i<=xl.rowCount(msheet);i++) 
	{
		if(xl.getCellData(msheet, i, 2).equalsIgnoreCase("y")) 
		{
		  	//storing all sheets into one variable
			String Tcmodule=xl.getCellData(msheet, i, 1);
			report=new ExtentReports("./target/Reports/"+Tcmodule+functionLibrary.generateDate()+"...."+".html");
			logger=report.startTest(Tcmodule);
			//iterat all rows in tcmodule
			for(int j=1;j<=xl.rowCount(Tcmodule);j++)
			{
				//read each cell from tcmodule 
				String discription=xl.getCellData(Tcmodule, j, 0);
				String object_type=xl.getCellData(Tcmodule, j, 1);
				String Locator_type=xl.getCellData(Tcmodule, j, 2);
				String Locator_value=xl.getCellData(Tcmodule, j, 3);
				String test_data=xl.getCellData(Tcmodule, j, 4);
				try {
					if(object_type.equalsIgnoreCase("startBrowser"))
					{
						driver=functionLibrary.startBrowser();
						logger.log(LogStatus.INFO, discription);
					}
					if(object_type.equalsIgnoreCase("openUrl"))
					{
					      functionLibrary.openUrl();
					      logger.log(LogStatus.INFO, discription);
					}
					if(object_type.equalsIgnoreCase("waitForElement"))
					{
					      functionLibrary.waitForElement(Locator_type, Locator_value, test_data);
					      logger.log(LogStatus.INFO, discription);
					}
					if(object_type.equalsIgnoreCase("typeAction"))
					{
					      functionLibrary.typeAction(Locator_type, Locator_value, test_data);
					      logger.log(LogStatus.INFO, discription);
					}
					
					if(object_type.equalsIgnoreCase("clickAction"))
					{
					      functionLibrary.clickAction(Locator_type, Locator_value);
					      logger.log(LogStatus.INFO, discription);
					}
					
					if(object_type.equalsIgnoreCase("vailidateTitle"))
					{
					      functionLibrary.vailidateTitle(test_data);
					      logger.log(LogStatus.INFO, discription);
					}
					
				     if(object_type.equalsIgnoreCase("closeBrowser"))
					{
					      functionLibrary.closeBrowser();
					      logger.log(LogStatus.INFO, discription);
					}
				     if(object_type.equalsIgnoreCase("dropDownAction"))
						{
						      functionLibrary.dropDownAction(Locator_type, Locator_value, test_data);
						      logger.log(LogStatus.INFO, discription);
						}
				     if(object_type.equalsIgnoreCase("stockCapture"))
						{
						      functionLibrary.stockCapture(Locator_type, Locator_value);
						      logger.log(LogStatus.INFO, discription);
						}
				     if(object_type.equalsIgnoreCase("captureSupplier"))
						{
						      functionLibrary.captureSupplier(Locator_type, Locator_value);
						      logger.log(LogStatus.INFO, discription);
						}
				     if(object_type.equalsIgnoreCase("supplierTable"))
						{
						      functionLibrary.supplierTable();
						      logger.log(LogStatus.INFO, discription);
						}
				     if(object_type.equalsIgnoreCase("customerNumber"))
						{
						      functionLibrary.customerNumber(Locator_type, Locator_value);
						      logger.log(LogStatus.INFO, discription);
						}
				     if(object_type.equalsIgnoreCase("customerTable"))
						{
						      functionLibrary.customerTable();
						      logger.log(LogStatus.INFO, discription);
						}
				     
				     
				     //write pass in tcmodule sheet
				     xl.setcelldata(Tcmodule, j, 5, "Pass", outputpath);
				     logger.log(LogStatus.PASS, discription);
				     module_status="True";
					
				}catch(Exception e) 
				{
					System.out.println(e.getMessage());
					//write fail in tcmodule sheet
				     xl.setcelldata(Tcmodule, j, 5, "Fail", outputpath);
				     logger.log(LogStatus.FAIL, discription);
				     module_status="False";
				}
				if(module_status.equalsIgnoreCase("True")) 
				{
					//write pass in msheet
					xl.setcelldata(msheet, i, 3, "Pass", outputpath);
				}
				else
				{
					//write fail in msheet
					xl.setcelldata(msheet, i, 3, "Fail", outputpath);
				}
				report.endTest(logger);
				report.flush();
			}
		}
		else {
			//writing block in msheet where flage is N
			xl.setcelldata(msheet, i, 3, "Blocked", outputpath);
		}
	}
}
}
