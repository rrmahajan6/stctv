package com.pages;

import com.qa.util.Constants;
import com.qa.util.CurrencyType;
import com.qa.util.GetAmount;
import com.qa.util.SeleniumUtilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import java.util.List;
import static com.pages.StvSubscription.*;
/**
 * <h1>Checkout Page Elements and Actions</h1>
 * CheckOut Class includes webElements and method for adding add ons to package
 * Calculate the total and verifying if amount and currency is correct
 *
 * @author  Rahul Mahajan
 * @version 1.0
 * @since   08/10/2022
 */
public class CheckOut implements Constants {
    private WebDriver driver;
    SeleniumUtilities utilities=new SeleniumUtilities();
    public float bill = 0.00f;
    String discoveryText;
    private boolean discovery;
    private boolean sports;
    @FindBy(xpath = "(//img[@class='operator-logo'])[1]")
    public WebElement stcPaymentMethod;
    @FindBy(id = "monthly-discovery_plus_subscription")
    public WebElement discoveryAddOn;
    @FindBy(id = "monthly-fight_subscription")
    public WebElement sportsAddon;
    @FindBy(xpath = "//div[@class='summary']/ul/li/div/div")
    public List<WebElement> orderSummary;
    @FindBy(xpath = "//div[@class='packageDetails-top']")
    public List<WebElement> planAndAddOn;
    @FindBy(xpath = "//section[@class='paymentMethod-sticky']")
    public WebElement footerDetails;
    @FindBy(xpath = "(//em[@class='package-price'])[1]")
    public WebElement stcAmountCurrency;
    public static String countryAndCurrency;
    public CheckOut(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(this.driver, this);
    }


    /**
     * This method is used to include add ons to package.
     * @param discovery if true then include the discovery to package else remote from package if selected already
     * @param sports  if true then include the discovery to package else remote from package if selected already
     */
    public void addOns(boolean discovery, boolean sports) {
        this.discovery=discovery;
        this.sports=sports;
        bill += Float.parseFloat(StvSubscription.planAmount);
        SeleniumUtilities.wait(3);
        if (discovery && !planType.equalsIgnoreCase(Constants.Premium)) {
            if (discoveryAddOn.getAttribute("class").contains("selected")) {
            } else utilities.click(discoveryAddOn);
            bill += GetAmount.valueOf(StvSubscription.country + StvSubscription.planType + Constants.Discovery).getPrice();
            orderDetails.put(Constants.addOnDiscovery, df.format(GetAmount.valueOf(StvSubscription.country + StvSubscription.planType + Constants.Discovery).getPrice()) + StvSubscription.currency + FREQUENCY);
        } else if(planType.equalsIgnoreCase(Constants.Premium)) {
            orderDetails.put(Constants.includedDiscovery, df.format(GetAmount.valueOf(StvSubscription.country + StvSubscription.planType + Constants.Discovery).getPrice()) + StvSubscription.currency + FREQUENCY);
        }
            else if(!discovery && !planType.equalsIgnoreCase(Constants.Premium)){
                if(discoveryAddOn.getAttribute("class").contains("selected")) utilities.click(discoveryAddOn);
        }
        if (sports) {
            if (sportsAddon.getAttribute("class").contains("selected")) {

            } else utilities.javaScriptClick(driver,sportsAddon);
            bill += GetAmount.valueOf(StvSubscription.country + StvSubscription.planType + Constants.Sports).getPrice();
            orderDetails.put(Constants.addOnFightSports, df.format(GetAmount.valueOf(StvSubscription.country + StvSubscription.planType + Constants.Sports).getPrice()) + StvSubscription.currency + FREQUENCY);
        } else {
            if (sportsAddon.getAttribute("class").contains("selected")) utilities.click(sportsAddon);
        }
    }


    /**
     * This method is used to verify order summary.
     * Compare the results between order details shown on the page and map created for storing package and add on during selection
     * it verifies Package, amount and currency
     * Selected add ons, their amount and currency
     * Total of package amount and add ons amount + currency
     */
    public void orderDetailsVerification() {
        orderDetails.put(Constants.Total, df.format(bill) + StvSubscription.currency + FREQUENCY);
        for (int i = 0; i < orderSummary.size(); i = i + 2) {
            Assert.assertTrue(orderDetails.get(orderSummary.get(i).getText()).equalsIgnoreCase(utilities.stringForming(orderSummary.get(i + 1).getText().split(System.lineSeparator()))));
        }
        footerDetailsValidations();
      }
    /**
     * This method is used to verify package name and price, add on name and price, and currency
     * Compare the results between price and currency shown on the page and expected value fetched from enum
     * it verifies Package, amount and currency
     * add ons, amount and currency
     */
    public void addOnPageFieldsVerification() {
        countryAndCurrency = df.format(GetAmount.valueOf(StvSubscription.country + StvSubscription.planType).getPrice()) + CurrencyType.valueOf(StvSubscription.country).getCurrencyType();
        Assert.assertTrue(utilities.stringForming(stcAmountCurrency.getText().split(":")).equalsIgnoreCase("Starting"+countryAndCurrency));
        utilities.click(stcPaymentMethod);
        SeleniumUtilities.wait(3);
        System.out.println(utilities.stringForming(planAndAddOn.get(0).getText().split(System.lineSeparator())));
        System.out.println("Planstctv" + StvSubscription.planType + countryAndCurrency + FREQUENCY);
        Assert.assertTrue(utilities.stringForming(planAndAddOn.get(0).getText().split(System.lineSeparator())).equalsIgnoreCase("Planstctv" + StvSubscription.planType + countryAndCurrency + FREQUENCY));
        if (StvSubscription.planType.equalsIgnoreCase(Constants.Premium)) {
            discoveryText = Constants.includedDiscoveryText;
        } else {
            discoveryText =Constants.addOnDiscoveryText + df.format(GetAmount.valueOf(StvSubscription.country + StvSubscription.planType + Constants.Discovery).getPrice()) + CurrencyType.valueOf(StvSubscription.country).getCurrencyType() + FREQUENCY;
        }
        Assert.assertTrue(utilities.stringForming(planAndAddOn.get(1).getText().split(System.lineSeparator())).equalsIgnoreCase(discoveryText));
        String sportsText = Constants.sportsText + df.format(GetAmount.valueOf(StvSubscription.country + StvSubscription.planType + Constants.Sports).getPrice()) + CurrencyType.valueOf(StvSubscription.country).getCurrencyType() + FREQUENCY;
        Assert.assertTrue(utilities.stringForming(planAndAddOn.get(2).getText().split(System.lineSeparator())).equalsIgnoreCase(sportsText));
    }
    /**
     * This method is used to verify order summary at footer.
     * it checks the plan and add ons and form the string
     * it verifies Total of package amount and add-ons amount
     */
    public void footerDetailsValidations(){
        String s="";
        if(discovery && sports && !planType.equalsIgnoreCase(Constants.Premium)){
            s="2.00Add-ons";
        }else if(discovery && sports && planType.equalsIgnoreCase(Constants.Premium)){
            s=Constants.FightSports;
        }
        else if(!discovery && sports){
            s=Constants.FightSports;
        }else if(discovery && !sports && !planType.equalsIgnoreCase(Constants.Premium)){
            s=Constants.Discovery+"+";
        }
           Assert.assertTrue(utilities.stringForming(footerDetails.getText().split(System.lineSeparator())).equalsIgnoreCase(planType+s+Constants.Total+":"+df.format(bill)+currency+FREQUENCY+nextButtonText));
    }
}
