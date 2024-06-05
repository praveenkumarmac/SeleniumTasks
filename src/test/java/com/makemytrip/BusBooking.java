package com.makemytrip;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BusBooking {
	
	public static void main(String[] args) throws IOException, InterruptedException, FileNotFoundException {		
		WebDriverManager.edgedriver().setup();
		EdgeOptions options = new EdgeOptions();		
		options.addArguments("disable-notifications");
		options.addArguments("disable-popups");
		options.addArguments("start-maximized");		
		EdgeDriver driver = new EdgeDriver();
		driver.get("https://www.makemytrip.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		Thread.sleep(4000);
		WebElement frame = driver.findElement(By.xpath("//iframe[@id='webklipper-publisher-widget-container-notification-frame']"));
		driver.switchTo().frame(frame);
		driver.findElement(By.xpath("//img[@id='second-img']")).click();
		String window = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		for(String eachWindow : windows) {
			if(!eachWindow.equals(windows)) {
				driver.switchTo().window(window);
			}
		}
		driver.findElement(By.xpath("//span[@class='commonModal__close']")).click();
		driver.findElement(By.xpath("//span[text()='Buses' and @class='headerIconTextAlignment chNavText darkGreyText']")).click();
		driver.findElement(By.xpath("//input[@id='fromCity']")).click();
		driver.findElement(By.xpath("//input[@placeholder='From']")).sendKeys("Trichy");
		driver.findElement(By.xpath("//span[text()='Tiruchirapalli (Trichy), Tamil Nadu']")).click();
		driver.findElement(By.xpath("//input[@placeholder='To']")).sendKeys("Bangalore");
		driver.findElement(By.xpath("//span[text()='Bangalore, Karnataka']")).click();
		driver.findElement(By.xpath("//div[@aria-label='Thu Jun 06 2024']")).click();
		driver.findElement(By.xpath("//button[@id='search_button']")).click();
		List<String> lst1 = new LinkedList<>();
		List<String> lst2 = new LinkedList<>();
		List<String> lst3 = new LinkedList<>();
		List<String> lst4 = new LinkedList<>();				
		File file = new File("C:\\Users\\PRAVEEN\\Desktop\\PRAVEEN1\\ProjectMakeMyTripTask\\ProjectMakeMyTripTask.xlsx");		
		XSSFWorkbook workBook = new XSSFWorkbook();
		XSSFSheet sheet = workBook.createSheet("Sheet1");
		XSSFRow excelRow = sheet.createRow(0);
		XSSFCell excelCell = excelRow.createCell(0);
		List<WebElement> buses= driver.findElements(By.xpath("//p[contains(@class,'makeFlex hrtlCenter appendBottom')]"));
		List<WebElement> deptime = driver.findElements(By.xpath("//span[contains(@class,'latoBlack blackText')]"));
		List<WebElement> arrtime = driver.findElements(By.xpath("//span[contains(@class,'latoRegular')]"));
		List<WebElement> farerate = driver.findElements(By.xpath("//span[@id='price']"));
		for (int i = 0; i < buses.size(); i++) {
			String bus = buses.get(i).getText();
			String depTime = deptime.get(i).getText();
			String arrTime = arrtime.get(i).getText();
			String fare = farerate.get(i).getText();
			lst1.add(bus);
			lst2.add(depTime);
			lst3.add(arrTime);
			lst4.add(fare);
			System.out.println("Bus Name: "+bus+"Departure Time: "+depTime+"Arrival Time: "+arrTime+"Fare rate: "+fare);
			}	
		for(int i=0; i<lst1.size(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				row = sheet.createRow(i);
			}
			Cell cell = row.createCell(0);
			cell.setCellValue(lst1.get(i));
		}
		for(int i=0; i<lst2.size(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				row = sheet.createRow(i);
			}
			Cell cell = row.createCell(1);
			cell.setCellValue(lst2.get(i));
		}
		for(int i=0; i<lst3.size(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				row = sheet.createRow(i);
			}
			Cell cell = row.createCell(2);
			cell.setCellValue(lst3.get(i));
		}
		for(int i=0; i<lst4.size(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				row = sheet.createRow(i);
			}
			Cell cell = row.createCell(3);
			cell.setCellValue(lst4.get(i));
		}
		FileOutputStream fileOUtput = new FileOutputStream(file);
		workBook.write(fileOUtput);
		fileOUtput.close();
		driver.findElement(By.xpath("//p[text()='Sri Renu Travels']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath("(//span[text()='₹1999'])[5]")).click();
		driver.findElement(By.xpath("//span[text()='Continue']")).click();
		driver.findElement(By.xpath("//input[@placeholder='Type here' and @type='text' and @id='fname']")).sendKeys("Praveen");
		driver.findElement(By.xpath("//input[@id='age']")).sendKeys("27");
		WebElement male = driver.findElement(By.xpath("//span[starts-with(@class,'listingSprite male')]"));
		male.click();
		Boolean value = male.isDisplayed();
		System.out.println(value);
		driver.findElement(By.xpath("//input[@id='dt_state_gst_info']")).click();
		driver.findElement(By.xpath("//li[text()='Tamil Nadu']")).click();
		driver.findElement(By.xpath("//p[text()='Confirm and save billing details to your profile']")).click();
		driver.findElement(By.xpath("//input[@name='email id']")).sendKeys("m.praveencivil@gmail.com");
		driver.findElement(By.xpath("//input[@name='Mobile Number']")).sendKeys("8124405747");
		JavascriptExecutor js = (JavascriptExecutor)driver;
		WebElement up = driver.findElement(By.xpath("//span[text()='Continue']"));
		js.executeScript("arguments[0].scrollIntoView(false)",up);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[text()='Continue']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));	
	}
}
