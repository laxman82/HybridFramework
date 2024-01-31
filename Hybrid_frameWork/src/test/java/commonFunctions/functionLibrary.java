package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import utilities.propertyFileUtils;

public class functionLibrary {

	public static WebDriver driver;

	//method for launch browser
	public static WebDriver startBrowser() throws Throwable 
	{
		if(propertyFileUtils.getValueForKey("Browser").equalsIgnoreCase("chrome")) 
		{
			driver=new ChromeDriver();
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
		}
		else if(propertyFileUtils.getValueForKey("Browser").equalsIgnoreCase("firefox")) 
		{
			driver=new FirefoxDriver();
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
		}
		else {
			Reporter.log("browser is not matching",true);
		}
		return driver;
	}
	//method for launch url
	public static void openUrl() throws Throwable 
	{
		driver.get(propertyFileUtils.getValueForKey("url"));
	}
	//method for waitForElement
	public static void waitForElement(String locator_type,String locator_value,String test_data)
	{
		WebDriverWait mywait=new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(test_data)));
		if(locator_type.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator_value)));
		}
		else if(locator_type.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locator_value)));
		}
		else if(locator_type.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locator_value)));
		}
	}  
	//method for textboxes
	public static void typeAction(String locator_type,String locator_value,String test_data)
	{
		if(locator_type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(locator_value)).clear();
			driver.findElement(By.xpath(locator_value)).sendKeys(test_data);

		}
		else if(locator_type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(locator_value)).clear();
			driver.findElement(By.name(locator_value)).sendKeys(test_data);

		}
		else  if(locator_type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locator_value)).clear();
			driver.findElement(By.id(locator_value)).sendKeys(test_data);

		}
	}
	//Method for buttons,links,radiobutton,checkboxes,images
	public static void clickAction(String locator_type,String locator_value)
	{
		if(locator_type.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(locator_value)).click();
		}
		else if(locator_type.equalsIgnoreCase("name")) {
			driver.findElement(By.name(locator_value)).click();
		}
		if(locator_type.equalsIgnoreCase("id")) {
			driver.findElement(By.id(locator_value)).sendKeys(Keys.ENTER);
		}
	}
	//method for validating title
	public static void vailidateTitle(String Expected_title) {
		String Actual_title=driver.getTitle();
		try {
			Assert.assertEquals(Actual_title, Expected_title);
		}catch(AssertionError a) {
			System.out.println(a.getMessage());
		}
	}
	//method for closing browser
	public static void closeBrowser() {
		driver.quit();
	}
	//method for date generation
	public static String generateDate() {
		Date date=new Date();
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd  hh-mm-ss");
		return df.format(date);

	}
	//method for listboxes
	public static void dropDownAction(String locator_type,String locator_value,String test_data) 
	{
		if(locator_type.equalsIgnoreCase("id"))
		{	  
			int value=Integer.parseInt(test_data);
			WebElement element=driver.findElement(By.id(locator_value));
			Select select=new Select(element);
			select.selectByIndex(value);
		}
		else if(locator_type.equalsIgnoreCase("name"))
		{	  
			int value=Integer.parseInt(test_data);
			WebElement element=driver.findElement(By.name(locator_value));
			Select select=new Select(element);
			select.selectByIndex(value);
		}
		else if(locator_type.equalsIgnoreCase("xpath"))
		{	  
			int value=Integer.parseInt(test_data);
			WebElement element=driver.findElement(By.xpath(locator_value));
			Select select=new Select(element);
			select.selectByIndex(value);
		}
	}
	//method for stock capture
	public static void stockCapture(String locator_type,String locator_value) throws Throwable
	{
		String stocknum="";
		if(locator_type.equalsIgnoreCase("xpath"))
		{
			stocknum=driver.findElement(By.xpath(locator_value)).getAttribute("value");
		}
		else if(locator_type.equalsIgnoreCase("id"))
		{
			stocknum=driver.findElement(By.id(locator_value)).getAttribute("value");
		}
		else if(locator_type.equalsIgnoreCase("name"))
		{
			stocknum=driver.findElement(By.name(locator_value)).getAttribute("value");
		}
		//writting stock number into notepad
		FileWriter fw=new FileWriter("./CaptureData/stocknumber.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(stocknum);
		bw.flush();
		bw.close();
	}
	//method for stock table
	public static void stockTable() throws Throwable 
	{
		//reading stock number from notepad
		FileReader fr=new FileReader("./CaptureData/stocknumber.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_data=br.readLine();
		if(!driver.findElement(By.xpath(propertyFileUtils.getValueForKey("searchBox"))).isDisplayed())
			driver.findElement(By.xpath(propertyFileUtils.getValueForKey("searchpanal"))).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(propertyFileUtils.getValueForKey("searchBox"))).clear();
		driver.findElement(By.xpath(propertyFileUtils.getValueForKey("searchBox"))).sendKeys(Exp_data);
		Thread.sleep(2000);
		driver.findElement(By.xpath(propertyFileUtils.getValueForKey("searchButton"))).click();
		Thread.sleep(2000);
		String Act_data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Exp_data+"---"+Act_data,true);
		try {
			Assert.assertEquals(Exp_data, Act_data,"not matching");
		}catch(AssertionError a) {
			System.out.println(a.getMessage());
		}
	}
	//method for captuer supplier number
	public static void captureSupplier(String locator_type,String locator_value) throws Throwable
	{
		String suppliernum="";
		if(locator_type.equalsIgnoreCase("xpath"))
		{
			suppliernum=driver.findElement(By.xpath(locator_value)).getAttribute("value");
		}
		else if(locator_type.equalsIgnoreCase("id"))
		{
			suppliernum=driver.findElement(By.id(locator_value)).getAttribute("value");
		}
		else if(locator_type.equalsIgnoreCase("name"))
		{
			suppliernum=driver.findElement(By.name(locator_value)).getAttribute("value");
		}
		//writting stock number into notepad
		FileWriter fw=new FileWriter("./CaptureData/suppliernumber.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(suppliernum);
		bw.flush();
		bw.close();
	}
	//method for stock table
	public static void supplierTable() throws Throwable 
	{
		//reading stock number from notepad
		FileReader fr=new FileReader("./CaptureData/suppliernumber.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_data=br.readLine();
		if(!driver.findElement(By.xpath(propertyFileUtils.getValueForKey("searchBox"))).isDisplayed())
			driver.findElement(By.xpath(propertyFileUtils.getValueForKey("searchpanal"))).click();
		driver.findElement(By.xpath(propertyFileUtils.getValueForKey("searchBox"))).clear();
		driver.findElement(By.xpath(propertyFileUtils.getValueForKey("searchBox"))).sendKeys(Exp_data);
		Thread.sleep(2000);
		driver.findElement(By.xpath(propertyFileUtils.getValueForKey("searchButton"))).click();

		String Act_data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
		Reporter.log(Exp_data+"---"+Act_data,true);
		try {
			Assert.assertEquals(Exp_data, Act_data,"not matching");
		}catch(AssertionError a) {
			System.out.println(a.getMessage());
		}
	}	
	//method for captuer customerNumber
	public static void customerNumber(String locator_type,String locator_value) throws Throwable
	{
		String customernum="";
		if(locator_type.equalsIgnoreCase("xpath"))
		{
			customernum=driver.findElement(By.xpath(locator_value)).getAttribute("value");
		}
		else if(locator_type.equalsIgnoreCase("id"))
		{
			customernum=driver.findElement(By.id(locator_value)).getAttribute("value");
		}
		else if(locator_type.equalsIgnoreCase("name"))
		{
			customernum=driver.findElement(By.name(locator_value)).getAttribute("value");
		}
		//writting stock number into notepad
		FileWriter fw=new FileWriter("./CaptureData/customerrnumber.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(customernum);
		bw.flush();
		bw.close();
	}
	//method for customer table
	public static void customerTable() throws Throwable 
	{
		//reading stock number from notepad
		FileReader fr=new FileReader("./CaptureData/customerrnumber.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_data=br.readLine();
		if(!driver.findElement(By.xpath(propertyFileUtils.getValueForKey("searchBox"))).isDisplayed())
			driver.findElement(By.xpath(propertyFileUtils.getValueForKey("searchpanal"))).click();

		driver.findElement(By.xpath(propertyFileUtils.getValueForKey("searchBox"))).clear();
		driver.findElement(By.xpath(propertyFileUtils.getValueForKey("searchBox"))).sendKeys(Exp_data);
		Thread.sleep(2000);
		driver.findElement(By.xpath(propertyFileUtils.getValueForKey("searchButton"))).click();
		String Act_data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		Reporter.log(Exp_data+"---"+Act_data,true);
		try {
			Assert.assertEquals(Exp_data, Act_data,"not matching");
		}catch(AssertionError a) {
			System.out.println(a.getMessage());
		}
	}
}