package com.qa.util;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DecimalFormat;
import java.util.List;

public class SeleniumUtilities {
    WebDriverWait wait;
    JavascriptExecutor jsDriver;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public WebElement waitForElementToClickableAndClick(WebDriver driver,WebElement w){
        wait=new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.elementToBeClickable(w));
        return w;
    }
    public static void wait(int time){
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void scrollToElement(WebDriver driver,WebElement w){
        jsDriver=(JavascriptExecutor)driver;
        jsDriver.executeScript("arguments[0].scrollIntoView(true);", w);
    }
    public void javaScriptClick(WebDriver driver,WebElement w){
        scrollToElement(driver,w);
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", w);
    }
    public void click(WebElement w){
        try{
            w.click();
        }catch (Exception e){
        }

    }
    public void writeText(WebDriver driver,WebElement w,String text){
        try{
            w.sendKeys(text);
        }catch (Exception e){
        }

    }
    public String stringForming(String[] str) {
        String s = "";
        for (String s1 : str) {
            if (s1.contains(" ")) {
                s1 = stringForming(s1.split(" "));
            }
            try {
                s1 = df.format(Float.valueOf(s1));
            } catch (NumberFormatException e) {
                //not float
            }
            s += s1;
        }
        return s;
    }
    public List<WebElement> waitTillAllElementsArePresent(WebDriver driver, By w){
         wait=new WebDriverWait(driver,10);
         wait.until(ExpectedConditions.presenceOfElementLocated(w));
         return driver.findElements(w);
    }
}
