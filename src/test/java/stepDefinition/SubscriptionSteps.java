package stepDefinition;

import com.pages.CheckOut;
import com.pages.GetStarted;
import com.pages.StvSubscription;
import com.qa.factory.DriverFactory;
import com.qa.util.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SubscriptionSteps {
    StvSubscription stvSubscription = new StvSubscription(DriverFactory.getDriver());
    GetStarted getStarted;
    ConfigReader configReader=new ConfigReader();
    CheckOut checkOut;

    @Given("user has logged in to application")
    public void user_has_logged_in_to_application() {
        DriverFactory.getDriver().get(configReader.init_prop().getProperty("url"));
    }
    @When("user selects country {string}")
    public void user_selects_country(String country) {
        stvSubscription.selectCountry(country);
    }

    @When("choose and verify trial plan type {string}")
    public void choose_and_verify_trial_plan_type(String planType) {
        getStarted=stvSubscription.trialPlanSelection(planType);
    }

    @When("user enters account credentials")
    public void user_enters_account_credentials() {
        checkOut=getStarted.StartTrailAfterEnteringDetails(configReader.init_prop().getProperty("userName"),configReader.init_prop().getProperty("passWord"));
    }
    @When("user includes the add ons {string} {string}")
    public void user_includes_the_add_ons(String discover, String sports) throws InterruptedException {
        checkOut.addOnPageFieldsVerification();
        checkOut.addOns(Boolean.parseBoolean(discover),Boolean.parseBoolean(sports));
    }
    @Then("verify checkout details are correct")
    public void verify_checkout_details_are_correct() {
        checkOut.orderDetailsVerification();
    }

}
