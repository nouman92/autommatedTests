package example;

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.phantomjs.PhantomJSDriver;
//import org.openqa.selenium.phantomjs.PhantomJSDriverService;
//import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.BeforeClass;

public class NewTestXvfb {
	WebDriver driver;
	boolean outOfStock = false;
	private static ChromeDriverService service;
	@BeforeTest
	public void beforeTest() {	

		String Xport = System.getProperty("lmportal.xvfb.id", ":1");
		System.setProperty("firefox.gecko.driver", "geckodriver");
		// /usr/bin/firefox
		final File firefoxPath = new File(System.getProperty("lmportal.deploy.firefox.path", "/usr/bin/firefox"));
		FirefoxBinary firefoxBinary = new FirefoxBinary(firefoxPath);
		firefoxBinary.setEnvironmentProperty("DISPLAY", Xport);
		driver = new FirefoxDriver(firefoxBinary, null);
		
// 		System.setProperty("webdriver.chrome.driver", "chromedriver");
// 		service = new ChromeDriverService.Builder()
// 		        .usingDriverExecutable(new File("/usr/bin/google-chrome"))
// 		        .usingAnyFreePort()
// 		        .withEnvironment(ImmutableMap.of("DISPLAY",":99"))
// 		        .build();
// 		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS); 
	}

	@Test				
	public void testEasy() {

		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);

		//OPEN BEACHTREE WEBSITE
		driver.get("http://www.beechtree.pk");
		//driver.get("http://www.beechtree.pk");

		System.out.println("Page title is: " + driver.getTitle());

		//RANDOMLY SELECT CATEGORY
		List<WebElement> allCategories = driver.findElements(By.cssSelector("a.level0"));
		Random random1 = new Random();
		//WebElement randomCategory = allCategories.get(random1.nextInt(allCategories.size()));

		WebElement randomCategory = allCategories.get(1);

		//prints on console which category is chosen
		String temp = randomCategory.getText(); //driver.findElement(By.xpath("//*[@id='nav']/ol/li[2]/a")).getText();

		//CLICK ON RANDOMLY SELECTED CATEGORY
		randomCategory.click();
		System.out.println("print the selected tab "+temp);


		//SELECT A RANDOM PRODUCT
		//List<WebElement> allProducts = driver.findElements(By.cssSelector("a.product-image"));
		//Random random2 = new Random();
		//WebElement randomProduct = allProducts.get(random2.nextInt(allProducts.size()));
		//System.out.println("print the selected product "+randomProduct);
		//randomProduct.click();

		WebDriverWait wait = new WebDriverWait(driver, 100);
		//*[@id="product-collection-image-4326"]
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='product-collection-image-4326']")));
		driver.findElement(By.xpath(".//*[@id='product-collection-image-4326']")).click();
		System.out.println("Selected Product .//*[@id='product-collection-image-4326']");

		WebDriverWait waitSwatch = new WebDriverWait(driver, 100);
		waitSwatch.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='showChart']/span")));
		List<WebElement> allsizes = driver.findElements(By.cssSelector("span[class='swatch']"));
		Random random3 = new Random();
		WebElement randomSize = allsizes.get(random3.nextInt(allsizes.size()));
		randomSize.click();
	
		//SELECT QUANTITY = 1
		Select oSelect = new Select(driver.findElement(By.xpath("//*[@id='qty']")));
		oSelect.selectByVisibleText("1");

		//ADD TO CART
		WebElement addCart;

		addCart = driver.findElement(By.xpath("//*[@id='product_addtocart_form']/div[4]/div[6]/div[2]/div[2]/button/span/span"));

		addCart.click();

		System.out.println("Add to Cart button is clicked");
		
		//CHECKOUT
		WebDriverWait waitCheckOut = new WebDriverWait(driver, 100);
		waitCheckOut.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"header-cart\"]/div[3]/div[3]/div/a/span")));
		WebElement CheckOut = driver.findElement(By.xpath("//*[@id='header-cart']/div[3]/div[3]/div/a/span"));

		CheckOut.click();
		System.out.println("CheckOut button is clicked");
		
		//FILL IN THE BILLING INFORMATION
		WebDriverWait waitt = new WebDriverWait(driver, 100);
		waitt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='billing:firstname']"))).sendKeys("test");

		System.out.println("FirstName is Enterd");

		//driver.findElement(By.xpath("//*[@id='billing:firstname']")).sendKeys("test");

		//WebDriverWait waitt2 = new WebDriverWait(driver, 10);
		//waitt2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='billing:lastname']"))).sendKeys("test");

		driver.findElement(By.xpath("//*[@id='billing:lastname']")).sendKeys("test");
		System.out.println("LastName is Enterd");

		//WebDriverWait waitt3 = new WebDriverWait(driver, 10);
		//waitt3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='bill_form']/div[2]/div[1]/input"))).sendKeys("sara.iftikharsi@gmail.com");

		driver.findElement(By.xpath("//*[@id='bill_form']/div[2]/div[1]/input")).sendKeys("sara.iftikharsi@gmail.com");
		System.out.println("Email is Enterd");
		//WebDriverWait waitt4 = new WebDriverWait(driver, 10);
		//waitt4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='billing:confirm_email']"))).sendKeys("sara.iftikharsi@gmail.com");

		driver.findElement(By.xpath("//*[@id='billing:confirm_email']")).sendKeys("sara.iftikharsi@gmail.com");
		System.out.println("Email is Confirmed");
		//WebDriverWait waitt5 = new WebDriverWait(driver, 10);
		//waitt5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='billing:street1']"))).sendKeys("test");

		driver.findElement(By.xpath("//*[@id='billing:street1']")).sendKeys("test");
		System.out.println("Street is Enterd");
		//WebDriverWait waitt6 = new WebDriverWait(driver, 10);
		//waitt6.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='billing:region']"))).sendKeys("test");

		driver.findElement(By.xpath("//*[@id='billing:region']")).sendKeys("test");
		System.out.println("Region is Enterd");
		//WebDriverWait waitt7 = new WebDriverWait(driver, 10);
		//waitt7.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='billing:postcode']"))).sendKeys("test");

		driver.findElement(By.xpath("//*[@id='billing:postcode']")).sendKeys("test");
		System.out.println("PostCode is Enterd");
		//Select oSelect2 = new Select(driver.findElement(By.xpath("//*[@id='billing:country_id']")));
		//oSelect2.selectByVisibleText("PAKISTAN");

		Select oSelect3 = new Select(driver.findElement(By.xpath("//*[@id='billing:city']")));
		oSelect3.selectByIndex(3);
		System.out.println("City is Enterd");

		//WebDriverWait waitt8 = new WebDriverWait(driver, 10);
		//waitt8.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='billing:telephone']"))).sendKeys("03364054186");

		driver.findElement(By.xpath("//*[@id='billing:telephone']")).sendKeys("03364054186");
		System.out.println("Phone number is Enterd");

		WebDriverWait waitt9 = new WebDriverWait(driver, 100);
		waitt9.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tel2']")));
		//.sendKeys("03364054186");

		//driver.findElement(By.xpath("//*[@id='tel2']")).sendKeys("03364054186");

		WebElement ConfirmMobileNumber = driver.findElement(By.id("tel2"));

		ConfirmMobileNumber.sendKeys("03364054186");
		System.out.println("Phone number is confirmed");


		//SELECT CASH ON DELEIVERY
		//WebDriverWait waitt10 = new WebDriverWait(driver, 10);
		//waitt10.until(ExpectedConditions.visibilityOfElementLocated(By.id("p_method_cashondelivery"))).click();

		driver.findElement(By.id("p_method_cashondelivery")).click();
		System.out.println("Cash delivery method is Enterd");

		System.out.println("Before Review Button is reached");

		//PLACE ORDER
		WebDriverWait wait3 = new WebDriverWait(driver, 200);
		wait3.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='review-buttons-container']/button")));
		driver.findElement(By.xpath("//*[@id='review-buttons-container']/button")).click();

		System.out.println("Review Button is clicked");
		System.out.println("after the Review Button is clicked");


		//CLOSE THE BROWSER
		WebDriverWait waitForOrder = new WebDriverWait(driver, 300);
		WebElement VerifyCode = waitForOrder.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='test']")));
		System.out.println("Title of the page is After Reviw Button Clicked " + driver.getTitle());
		System.out.println("Order is Successfully placed "+VerifyCode);
		//		//File scrFile3 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//		//FileUtils.copyFile(scrFile3, new File("beechtree3.png"), true);
		//		//driver.quit();

		driver.quit();
	}
	
}
