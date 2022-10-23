package com.qa.factory;
import com.qa.util.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.concurrent.TimeUnit;

public class DriverFactory implements Constants {

	private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
	/**
	 * This method is used to initialize the threadlocal driver on the basis of given
	 * browser
	 *
	 * @param browser
	 * @return this will return tldriver.
	 */
	public WebDriver init_driver(String browser) {
		if (browser.equals("chrome")) {
			ChromeOptions chromeOptions=new ChromeOptions();
			chromeOptions.addArguments("--disable-notifications","--headless","--incognito");
			WebDriverManager.chromedriver().setup();
			tlDriver.set(new ChromeDriver(chromeOptions));
		} else if (browser.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			tlDriver.set(new FirefoxDriver());
		} else if (browser.equals("safari")) {
			tlDriver.set(new SafariDriver());
		} else {
			System.out.println("Please pass the correct browser value: " + browser);
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(Constants.timeout, TimeUnit.SECONDS);
		return getDriver();
	}
	/**
	 * this is used to get the driver with ThreadLocal
	 *
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
}
