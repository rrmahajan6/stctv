package com.qa.factory;
import com.qa.util.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.HashMap;
import java.util.Map;
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
// 			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/src/main/java/com/qa/factory/chromedriver");
			ChromeOptions chromeOptions=new ChromeOptions();
			chromeOptions.addArguments("--disable-notifications","--headless","--incognito");
			WebDriverManager.chromedriver().setup();
			tlDriver.set(new ChromeDriver(chromeOptions));
		} else if (browser.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
//			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"/src/main/java/com/qa/factory/geckodriver");
			tlDriver.set(new FirefoxDriver());
		} else if (browser.equals("safari")) {
			SafariOptions browserOptions = new SafariOptions();
			browserOptions.setCapability("platformName", "macOS 10.15");
			browserOptions.setCapability("browserVersion", "latest");
			Map<String, Object> sauceOptions = new HashMap<>();
			Map<String, Object> pref = new HashMap<>();
			pref.put("permissions.default.desktop-notification",0);
			sauceOptions.put("screenResolution", "1024x768");
			sauceOptions.put("customData",pref);
			browserOptions.setCapability("sauce:options", sauceOptions);
			browserOptions.setCapability("--disable-notifications", true);
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
