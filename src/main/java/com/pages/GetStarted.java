package com.pages;

import com.qa.factory.DriverFactory;
import com.qa.util.SeleniumUtilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
/**
 * <h1>GetStarted Page Elements and Actions</h1>
 * Login to application and start trial period
 *
 * @author  Rahul Mahajan
 * @version 1.0
 * @since   08/10/2022
 */

public class GetStarted {
    private WebDriver driver;
    @FindBy(id = "email")
    public WebElement emailTextBox;
    @FindBy(id = "passwordInput")
    public WebElement passwordBox;
    @FindBy(xpath = "//span[text()='Start your trial']")
    public WebElement startYourTrial;
    SeleniumUtilities utilities=new SeleniumUtilities();

    public GetStarted(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(this.driver, this);
    }

    /**
     * This method is used to start trial period
     * @param userEmail
     * @param password
     */
    public CheckOut StartTrailAfterEnteringDetails(String userEmail, String password) {
        utilities.writeText(driver,emailTextBox,userEmail);
        utilities.writeText(driver,passwordBox,password);
        utilities.click(startYourTrial);
        return new CheckOut(driver);
    }
}
