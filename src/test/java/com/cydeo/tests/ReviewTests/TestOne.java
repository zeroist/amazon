//Review week task_01
package com.cydeo.tests.ReviewTests;

import com.cydeo.utilities.BrowserUtil;
import com.cydeo.utilities.ConfigurationReader;
import com.cydeo.utilities.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class TestOne {
    public WebDriver driver;

    @BeforeMethod
    public void setupMethod() {
        driver = WebDriverFactory.getDriver(ConfigurationReader.getProperty("browser"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.amazon.com");
    }

    @Test
    public void Test1() {
        // change adress issue
        WebElement searchBox;
        if (driver.findElement(By.xpath("//input[@class=\"a-button-input\"][1]")).isDisplayed()) {
            driver.findElement(By.xpath("//input[@class=\"a-button-input\"][1]")).click();
            searchBox = driver.findElement(By.cssSelector("input#twotabsearchtextbox"));
        } else {
            searchBox = driver.findElement(By.xpath("/input[@id=\"nav-bb-search\"]"));
        }
        //find product and click

        searchBox.sendKeys(ConfigurationReader.getProperty("searchValue") + Keys.ENTER);
        driver.findElement(By.xpath("//img[@class=\"s-image\"][1]")).click();
        //at product page ,select quantity of product where comes from properties-ConfigurationReader,it is also expected quantity
        int firstExpectedQuantity = Integer.parseInt(ConfigurationReader.getProperty("quantityForDropDown"));

        //actual each price
        double actualEachPrice = Double.parseDouble(driver.findElement(By.xpath("//span[@class=\"a-price a-text-price a-size-medium\"]")).getText().substring(1));
        double expectedTotalPrice = actualEachPrice * firstExpectedQuantity;
        //select dropdown menu and quantity and add to card
        Select quantityDropdown = new Select(driver.findElement(By.xpath("//select[@id=\"quantity\"]")));
        quantityDropdown.selectByVisibleText("2");
        BrowserUtil.waitFor(1);
        //after adding card verify actual price and quantity
        driver.findElement(By.cssSelector("input#add-to-cart-button")).click();
        double actualTotalPrice = Double.parseDouble(driver.findElement(By.xpath("//span[@class=\"ewc-subtotal-amount\"]/span")).getText().substring(1));
        int actualQuantity = Integer.parseInt(driver.findElement(By.xpath("//span[@id=\"nav-cart-count\"]")).getText());
        Assert.assertEquals(actualTotalPrice, expectedTotalPrice);
        Assert.assertEquals(actualQuantity, firstExpectedQuantity);
        //go to sepet for decreasing quantity by 1 with new dropdown menu
        driver.findElement(By.xpath("//span[@id=\"nav-cart-count\"]")).click();
        quantityDropdown = new Select(driver.findElement(By.xpath("//select[@id=\"quantity\"]")));
        int secondExpectedQuantity = firstExpectedQuantity - 1;
        quantityDropdown.selectByVisibleText("" + secondExpectedQuantity);
        BrowserUtil.waitFor(1);
        expectedTotalPrice = actualEachPrice * secondExpectedQuantity;
        //after updating quantity verify new actual price and quantity
        String checkPrice = driver.findElement(By.xpath("//span[@id=\"sc-subtotal-amount-buybox\"]")).getText().trim();
        if (!checkPrice.contains("\n")) {
            actualTotalPrice = Double.parseDouble(checkPrice.substring(1));
        } else {
            System.out.println(checkPrice.replace("\n", ""));
            actualTotalPrice = Double.parseDouble(checkPrice.replace("\n", "").substring(1)) / 100;
        }
        actualQuantity = Integer.parseInt(driver.findElement(By.xpath("//span[@class=\"a-dropdown-prompt\"]")).getText());
        Assert.assertEquals(actualTotalPrice, expectedTotalPrice);
        Assert.assertEquals(actualQuantity, secondExpectedQuantity);


    }


}
