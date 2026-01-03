package com.travolgo.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPOM {

    WebDriver driver;
    WebDriverWait wait;
   
    // ===== Constructor =====
    public CheckoutPOM(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }


    // ===== Locators =====
    private By cartButton = By.xpath("/html/body/div[1]/header/div[1]/div[1]/div[3]/div/a[2]/div[1]/span[2]");
    private By proceedToBuyBtn = By.name("proceedToRetailCheckout");

    private By addressContinueBtn = By.xpath(
            "//span[@id='shipToThisAddressButton'] | //a[contains(@class,'a-button-text')]");

    private By upiRadioBtn = By.xpath("/html/body/div[5]/div[1]/div/div/div[2]/div/div[9]/div[2]/div[2]/div/div/div[1]/form/div/div/div/div[2]/div[5]/div/div/div/div/div[1]/div/label/input");
    private By upiInput = By.xpath("//input[@placeholder='Enter UPI ID']");
    private By verifyUpiBtn = By.name("ppw-widgetEvent:ValidateUpiIdEvent");

    private By successMsg = By.xpath(
            "//span[contains(text(),'Please press continue to complete the purchase')]");

    private By continueBtn1 = By.id("checkout-secondary-continue-button-id");
    private By finalContinueBtn = By.xpath("//input[@name='placeYourOrder1']");

   
    // ===== Actions =====

    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartButton)).click();
    }

    public void proceedToBuy() {
        wait.until(ExpectedConditions.elementToBeClickable(proceedToBuyBtn)).click();
    }

    public void continueAddress() {
        wait.until(ExpectedConditions.elementToBeClickable(addressContinueBtn)).click();
    }

    public void selectUPIAndVerify(String upiId) {
        wait.until(ExpectedConditions.elementToBeClickable(upiRadioBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(upiInput)).sendKeys(upiId);
        wait.until(ExpectedConditions.elementToBeClickable(verifyUpiBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMsg));
    }

    public void continuePayment() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(continueBtn1)).click();
        Thread.sleep(4000);
        wait.until(ExpectedConditions.elementToBeClickable(finalContinueBtn)).click();
    }
}