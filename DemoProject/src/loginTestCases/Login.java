package loginTestCases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import jxl.Sheet;
import jxl.Workbook;

public class Login {

	String[][] data=null;
	WebDriver driver;

	@DataProvider(name="loginData")
	public String[][] loginDataProvider() {
		data=getExcelData();
		return data;
	}

	public String[][] getExcelData() throws FileNotFoundException {
		FileInputStream excel= new FileInputStream("c:\\users\\Elcot\\Desktop\\TestData.xls");

		Workbook workbook=Workbook.getWorkbook("excel");
		Sheet sheet=workbook.getSheet(0);
		int rowCount=sheet.getRows();
		int columnCount=sheet.getColumns();

		String testData[][]=new String[rowCount-1][columnCount];

		for(int i=1; i<rowCount; i++) {
			for(int j=0;j<columnCount;j++) {

				testData[i-1][j]=sheet.getCell(j, i).getContents();

			}
		}

		return testData;


	}
	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.gecko.driver", "D:\\selenium training\\geckodriver-v0.31.0-win32\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
	}



	public void afterTest() {
		driver.quit();
	}

	@Test(dataProvider = "loginData")
	public void loginWithBothCorrect(String uName, String pWord) {


		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

		WebElement userName=driver.findElement(By.xpath("/html/body/div/div[1]/div/div[1]/div/div[2]/div[2]/form/div[1]/div/div[2]/input"));
		userName.sendKeys(uName);

		WebElement password=driver.findElement(By.xpath("/html/body/div/div[1]/div/div[1]/div/div[2]/div[2]/form/div[2]/div/div[2]/input"));
		password.sendKeys(pWord);

		WebElement loginButton=driver.findElement(By.className("oxd-button oxd-button--medium oxd-button--main orangehrm-login-button"));
		loginButton.click();

	}
}
