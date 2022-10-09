package com.pages;

import com.qa.factory.DriverFactory;
import com.qa.util.Constants;
import com.qa.util.CurrencyType;
import com.qa.util.GetAmount;
import com.qa.util.SeleniumUtilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import java.util.HashMap;
import java.util.List;
/**
 * <h1>Subscription Page Elements and Actions</h1>
 * First Page of the application
 * Select the desired country and verify if package amount and currency is correct
 *
 * @author  Rahul Mahajan
 * @version 1.0
 * @since   08/10/2022
 */
public class StvSubscription implements Constants {
    private WebDriver driver;
    public static String country;
    public static String planType;
    public static String planAmount;
    public static String currency;
    public static String countryCurrencyFrequency;
    public static HashMap<String, String> orderDetails;
    SeleniumUtilities utilities=new SeleniumUtilities();

    @FindBy(id = "changeLanguageButton")
    public WebElement changeLanguageButton;
    @FindBy(xpath = "//a[@class='header-btns-country hide-mobile']")
    public WebElement changeCountryDropDown;
    @FindBy(xpath = "//a[@href='https://subscribe.stctv.com/bh-en']")
    public WebElement Bahrain;
    @FindBy(xpath = "//a[@href='https://subscribe.stctv.com/sa-en']")
    public WebElement KSA;
    @FindBy(xpath = "//a[@href='https://subscribe.stctv.com/kw-en']")
    public WebElement Kuwait;
    @FindBy(xpath = "(//div[@class='price'])[1]/span[@class='amount']")
    public WebElement liteAmount;
    @FindBy(xpath = "(//div[@class='price'])[2]/span[@class='amount']")
    public WebElement classicAmount;
    @FindBy(xpath = "(//button[text()='Start your trial'])[1]")
    public WebElement startTrialForLite;
    @FindBy(xpath = "(//button[text()='Start your trial'])[2]")
    public WebElement startTrialForClassic;
    @FindBy(xpath = "(//button[text()='Start your trial'])[3]")
    public WebElement startTrialForPremium;
    @FindBy(xpath = "//div[@class='price']")
    public List<WebElement> priceList;

    public StvSubscription(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * This method is used to select country
     * @param country to select desired country
     */
    public void selectCountry(String country) {
        StvSubscription.country = country;
        StvSubscription.currency = CurrencyType.valueOf(country).getCurrencyType();
        utilities.click(changeLanguageButton);
        utilities.click(changeCountryDropDown);

        if (country.equalsIgnoreCase(Constants.KSA))
            utilities.click(KSA);
        else if (country.equalsIgnoreCase(Constants.Bahrain))
            utilities.click(Bahrain);
        else if (country.equalsIgnoreCase(Constants.Kuwait))
            utilities.click(Kuwait);
    }

    /**
     * This method is used to select the plan and verify plan amount, currency and Frequency is correct
     * After verification it stores plan details in map to use to comparison on checkout page
     * @param planType to select desired plan
     */
    public GetStarted trialPlanSelection(String planType)  {
        StvSubscription.planType = planType;
        planAmount = String.valueOf(GetAmount.valueOf(country + StvSubscription.planType).getPrice());
        orderDetails = new HashMap<>();
        SeleniumUtilities.wait(2);
        switch (planType) {
            case Constants.Lite:
                countryCurrencyFrequency = df.format(GetAmount.valueOf(country + planType).getPrice()) + CurrencyType.valueOf(country).getCurrencyType() + FREQUENCY;
                Assert.assertEquals(utilities.stringForming(priceList.get(0).getText().split(System.lineSeparator())), countryCurrencyFrequency);
                utilities.javaScriptClick(driver,startTrialForLite);

                break;
            case Constants.Classic:
                countryCurrencyFrequency = df.format(GetAmount.valueOf(country + planType).getPrice()) + CurrencyType.valueOf(country).getCurrencyType() + FREQUENCY;
                Assert.assertEquals(utilities.stringForming(priceList.get(1).getText().split(System.lineSeparator())), countryCurrencyFrequency);
                utilities.javaScriptClick(driver,startTrialForClassic);

                break;
            case Constants.Premium:
                countryCurrencyFrequency = df.format(GetAmount.valueOf(country + planType).getPrice()) + CurrencyType.valueOf(country).getCurrencyType() + FREQUENCY;
                Assert.assertEquals(utilities.stringForming(priceList.get(2).getText().split(System.lineSeparator())), countryCurrencyFrequency);
                utilities.javaScriptClick(driver,startTrialForPremium);
                break;
        }
        orderDetails.put(Constants.Plan+": " + StvSubscription.planType, countryCurrencyFrequency);
        return new GetStarted(driver);
    }
}
