package example;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
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
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import java.net.MalformedURLException;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

public class NewTestXvfb {
	
	WebDriver driver;
	boolean outOfStock = false;
	public static final String KEY = "1aa8518808db386059c92274ed4466c2";
	public static final String SECRET = "f1359f37086a31e9d6304f52b27d2e8e";
	public static final String URL = "http://" + KEY + ":" + SECRET + "@hub.testingbot.com/wd/hub";

	@BeforeTest
	public void beforeTest() throws MalformedURLException {	
		
		//Firefox Driver
//		String Xport = System.getProperty("lmportal.xvfb.id", ":1");
//		System.setProperty("firefox.gecko.driver", "geckodriver");
//		final File firefoxPath = new File(System.getProperty("lmportal.deploy.firefox.path", "/usr/bin/firefox"));
//		FirefoxBinary firefoxBinary = new FirefoxBinary(firefoxPath);
//		firefoxBinary.setEnvironmentProperty("DISPLAY", Xport);
//		driver = new FirefoxDriver(firefoxBinary, null); 
		
		//chrome remote Driver
		//System.setProperty("webdriver.chrome.driver", "chromedriver");
		//driver=new ChromeDriver();
//		DesiredCapabilities caps = new DesiredCapabilities();
//		caps.setCapability("platform", "LINUX");
//		//caps.setCapability("version", "54");
//		caps.setCapability("browserName", "chrome");
//		driver = new RemoteWebDriver(new URL(URL), caps);
		
		//PhantomJs Driver
		System.setProperty("phantomjs.binary.path", "phantomjs");
		String[] cli_args = new String[]{ "--ignore-ssl-errors=true" };
		DesiredCapabilities caps = DesiredCapabilities.phantomjs();
		caps.setCapability("takeScreenshot", "false");
		caps.setCapability( PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cli_args );
		caps.setCapability( PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "phantomjs");
		driver =  new PhantomJSDriver( caps );

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		//driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS); 
	}
	
	@AfterTest
	public void afterTest(){
	
		driver.quit();

	}
	
	@Test				
	public void BeechTree() {

		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);

		//OPEN BEACHTREE WEBSITE
		driver.get("http://www.beechtree.pk");
		//driver.get("http://www.beechtree.pk");

		System.out.println("Page title is: " + driver.getTitle());

		 //RANDOMLY SELECT CATEGORY
        List<WebElement> allCategories = driver.findElements(By.cssSelector("a.level0"));
        Random random1 = new Random();
        WebElement randomCategory = allCategories.get(random1.nextInt(allCategories.size()));

        //prints on console which category is chosen
        String temp = randomCategory.getText(); //driver.findElement(By.xpath("//*[@id='nav']/ol/li[2]/a")).getText();
        System.out.println("print the selected tab "+temp);

        if(!(temp.equals("LOOKBOOK")))
        {
            //CLICK ON RANDOMLY SELECTED CATEGORY
            randomCategory.click();


            //SELECT A RANDOM PRODUCT
            List<WebElement> allProducts = driver.findElements(By.cssSelector("a.product-image"));
            System.out.println("print the allProducts "+allProducts);
           // System.out.println("print the allProducts.size() "+allProducts.size());
            Random random2 = new Random();
            WebElement randomProduct = allProducts.get(random2.nextInt(allProducts.size()));
            randomProduct.click();
            System.out.println("Random product is clicked");

            if(!(temp.equals("ACCESSORIES")||temp.equals("BT LAWN '16")))
            {
                //RANDOMLY SELECT THE SIZE
                //this will only make a list of available sizes/clickable size buttons on the website so the issue of availabe sizes will be handled
                List<WebElement> allsizes = driver.findElements(By.cssSelector("span[class='swatch']"));
                Random random3 = new Random();
                WebElement randomSize = allsizes.get(random3.nextInt(allsizes.size()));
                if(!allsizes.isEmpty())//if the size is availabe,click/select it
                {
                    randomSize.click();
                    System.out.println("Random size is clicked");
                }
                else//if the sizes are not available, print on console that the product is out of stock
                {
                    System.out.println("the item selected is out of stock");
                    outOfStock = true;
                }
            }
            if(outOfStock == false)//continue testing only if product is in stock
            {

                //SELECT QUANTITY = 1
                Select oSelect = new Select(driver.findElement(By.xpath("//*[@id='qty']")));
                oSelect.selectByVisibleText("1");

                //ADD TO CART
                WebElement addCart;
                if(temp.equals("ACCESSORIES"))
                {
                    addCart = driver.findElement(By.xpath("//*[@id='product_addtocart_form']/div[4]/div[6]/div/div/div[2]/button/span/span"));
                }
                else if(temp.equals("BT LAWN '16"))
                {
                    addCart = driver.findElement(By.xpath("//*[@id='product_addtocart_form']/div[4]/div[6]/div/div/div[2]/button/span/span"));
                }
                else if(temp.equals("SALE"))
                {
                    addCart = driver.findElement(By.xpath("//*[@id='product_addtocart_form']/div[4]/div[6]/div[2]/div[2]/button/span/span"));
                }
                else //temp = summer sale/pret
                {
                    addCart = driver.findElement(By.xpath("//*[@id='product_addtocart_form']/div[4]/div[6]/div[2]/div[2]/button"));
                }



                //CHECK IF THE ADD TO CART BUTTON IS ENABLED + DISPLAYED ON WEBPAGE
                if(!(addCart.isDisplayed()&& addCart.isEnabled()))
                {
                    if(!addCart.isDisplayed())
                    {
                        System.out.println("Add to Cart button is not displayed on the webpage");
                    }
                    if(!addCart.isEnabled())
                    {
                        System.out.println("Add to Cart button is disabled on webpage");
                    }
                }
                else
                {
                    addCart.click();
                    System.out.println("add to Cart button is clicked");

                    //CHECKOUT

                    //checks if checkout button is enabled + displayed on webpage

                    WebDriverWait wait = new WebDriverWait(driver, 100);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"header-cart\"]/div[3]/div[3]/div/a/span")));
                    WebElement CheckOut = driver.findElement(By.xpath("//*[@id='header-cart']/div[3]/div[3]/div/a/span"));
                    if(!(CheckOut.isDisplayed()&& CheckOut.isEnabled()))
                    {
                        if(!CheckOut.isDisplayed())
                        {
                            System.out.println("CHECKOUT button is not displayed on the webpage");
                        }
                        if(!CheckOut.isEnabled())
                        {
                            System.out.println("CHECKOUT button is disabled on webpage");
                        }
                    }
                    else
                    {
                        CheckOut.click();
                        System.out.println("Checkout Button is clicked");

                        //FILL IN THE BILLING INFORMATION
                        driver.findElement(By.xpath("//*[@id='billing:firstname']")).sendKeys("test");
                        System.out.println("First Name is Enterd");
                        driver.findElement(By.xpath("//*[@id='billing:lastname']")).sendKeys("test");
                        System.out.println("Last Name is Enterd");
                        driver.findElement(By.xpath("//*[@id='bill_form']/div[2]/div[1]/input")).sendKeys("iftikharsi@gmail.com");
                        System.out.println("Email is Enterd");
                        driver.findElement(By.xpath("//*[@id='billing:confirm_email']")).sendKeys("iftikharsi@gmail.com");
                        System.out.println("Email is confirmed");
                        driver.findElement(By.xpath("//*[@id='billing:street1']")).sendKeys("test");
                        System.out.println("Street 1 is Enterd");
                        driver.findElement(By.xpath("//*[@id='billing:region']")).sendKeys("test");
                        System.out.println("Region is Enterd");
                        driver.findElement(By.xpath("//*[@id='billing:postcode']")).sendKeys("test");
                        System.out.println("Billing postcode is Enterd");
                        //Select oSelect2 = new Select(driver.findElement(By.xpath("//*[@id='billing:country_id']")));
                        //oSelect2.selectByVisibleText("PAKISTAN");
                        Select oSelect3 = new Select(driver.findElement(By.xpath("//*[@id='billing:city']")));
                        oSelect3.selectByIndex(3);
                        System.out.println("City is Enterd");
                        driver.findElement(By.xpath("//*[@id='billing:telephone']")).sendKeys("03001234567");
                        System.out.println("Telephone is Enterd");
                        
                        //driver.findElement(By.xpath("//*[@id='tel2']")).sendKeys("03001234567");



                        //SELECT CASH ON DELEIVERY
                        //driver.findElement(By.id("p_method_cashondelivery")).click();

                        //PLACE ORDER
                        WebDriverWait wait3 = new WebDriverWait(driver, 200);
                        wait3.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='review-buttons-container']/button")));
                        driver.findElement(By.xpath("//*[@id='review-buttons-container']/button")).click();
                        System.out.println("Place Order Now Button is Clicked");
                    }
                }
            }
        }

        //CLOSE THE BROWSER
//        WebDriverWait wait = new WebDriverWait(driver, 10);
//        WebElement VerifyCode = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='test']")));
        //driver.close();
    }


}
