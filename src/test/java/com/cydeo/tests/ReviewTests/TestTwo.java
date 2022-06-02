package com.cydeo.tests.ReviewTests;

import com.cydeo.utilities.ConfigurationReader;
import com.cydeo.utilities.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class TestTwo {
    public WebDriver driver;

    @BeforeMethod
    public void setupMethod() {
        driver = WebDriverFactory.getDriver(ConfigurationReader.getProperty("browser"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://moneygaming.qa.gameaccount.com/");
    }

    @Test
    public void Test1(){
        driver.findElement(By.xpath("//a[@href=\"/sign-up.shtml\"]")).click();
        Select select=new Select(driver.findElement(By.xpath("//select[@id=\"title\"]")));
        select.selectByVisibleText("Mr");
        driver.findElement(By.xpath("//input[@id=\"forename\"]")).sendKeys("Yusuf");
        driver.findElement(By.xpath("//input[@name=\"map(lastName)\"]")).sendKeys("OLGUN");
        driver.findElement(By.xpath("//input[@id=\"checkbox\"][3]")).click();
        driver.findElement(By.xpath("//input[@id=\"form\"]")).click();
        String alert = driver.findElement(By.xpath("//label[@for=\"dob\"]")).getText();
        String actual="This field is required";
        Assert.assertEquals(actual,alert);



    }
}
