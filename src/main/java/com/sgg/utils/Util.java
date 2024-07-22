package com.sgg.utils;

import com.sgg.testdata.GlobalTestData;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Util {

    static WebDriverWait owait;
    public static String getFolderName() {
        int myDay = LocalDateTime.now().getDayOfMonth();
        int myYear = LocalDateTime.now().getYear();
        int myMonth = LocalDateTime.now().getMonthValue();
        final String currentDate = String.valueOf(myMonth) +  String.valueOf(myDay) +  String.valueOf(myYear);
        return currentDate;
    }

    /**
     * To get the time stamp as a string
     *
     * @return
     */
    public static String getTimeStamp() {
        DateFormat dateF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateF.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
        String format = dateF.format(new Date());
        String formatN = format.replaceAll("[^0-9]", "");
        return formatN;
    }

    public static boolean createFolder(String folderPath) {
        boolean result = false;
        try {
            File directory = new File(folderPath);
            if (!directory.exists()) {
                result = directory.mkdir();
                System.out.println("Folder exists : "+result);
            }
        } catch (Exception e) {
            System.out.println("Error while creating the specific folder. Location " + folderPath + ". Error message "
                    + e.getMessage());
        }
        return result;
    }

    /**
     * To get the value from property file
     * @param key
     * @return
     * @author NagaPraveen
     */
    public static String getProperty(String key) {
        String pathname = System.getProperty("user.dir") + File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"config.properties";
        Properties prop = new Properties();
        try {
            FileInputStream fi = new FileInputStream(new File(pathname));
            prop.load(fi);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }

    public static WebElement waitForElement(WebDriver driver, By locator) {
        owait = new WebDriverWait(driver, Duration.ofSeconds(GlobalTestData.wait));
        return owait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static WebElement waitForElement(WebDriver driver, WebElement element) {
        owait = new WebDriverWait(driver, Duration.ofSeconds(GlobalTestData.wait));
        return owait.until(ExpectedConditions.visibilityOf(element));
    }

    public static String getText(WebDriver driver, By locator) {
        WebElement element = waitForElement(driver, locator);
        return element.getText();
    }

    public static String getAttributeValue(WebDriver driver, By locator, String attribute) {
        WebElement element = waitForElement(driver, locator);
        return element.getAttribute(attribute);
    }

    public static List<String> getTextFromList(WebDriver driver, By locator) {
        List<String> values = new LinkedList<>();
        owait = new WebDriverWait(driver, Duration.ofSeconds(GlobalTestData.wait));
        List<WebElement> list = owait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        for(WebElement each : list){
//            Util.scroll(driver, each);
            values.add(each.getText());
        }
        return values;
    }

    public static List<WebElement> getElementsList(WebDriver driver, By locator) {
        owait = new WebDriverWait(driver, Duration.ofSeconds(GlobalTestData.wait));
        List<WebElement> list = owait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        return list;
    }

    public static void hardWait(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void sendKeys(WebDriver driver, By locator, String value, String desc) {
        waitForElement(driver, locator).sendKeys(value);
        System.out.println("User enters '" + value + "' into '" + desc + "'");
    }

    public static void sendKeysJS(WebDriver driver, By locator, String value, String desc) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        owait = new WebDriverWait(driver, Duration.ofSeconds(GlobalTestData.wait));
        WebElement element = owait.until(ExpectedConditions.elementToBeClickable(locator));
        element.clear();
        element.sendKeys(value);
        executor.executeScript("arguments[0].value='"+value+"';",element);
//        waitForElement(driver, locator).sendKeys(value);
        System.out.println("User enters '" + value + "' into '" + desc + "'");
    }

    public static void click(WebDriver driver, By locator, String desc) {
        owait = new WebDriverWait(driver, Duration.ofSeconds(GlobalTestData.wait));
        try {

            owait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        }catch (StaleElementReferenceException se){
            owait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        }catch (ElementClickInterceptedException eci){
            System.out.println("Trying click again due to intercepted ");
            Util.hardWait(5);
            Util.scroll(driver, locator);
            owait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        }
        System.out.println("User clicks on '" + desc + "'");
    }

    public static void click(WebDriver driver, WebElement element, String desc) {
        owait = new WebDriverWait(driver, Duration.ofSeconds(GlobalTestData.wait));
        try {
            owait.until(ExpectedConditions.elementToBeClickable(element)).click();
        }catch (StaleElementReferenceException se){
            owait.until(ExpectedConditions.elementToBeClickable(element)).click();
        }catch (ElementClickInterceptedException eci){
            System.out.println("Trying click again due to intercepted ");
            Util.scroll(driver, element);
            owait.until(ExpectedConditions.elementToBeClickable(element)).click();
        }
        System.out.println("User clicks on '" + desc + "'");
    }

    public static void clickJS(WebDriver driver, By locator, String desc){
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        owait = new WebDriverWait(driver, Duration.ofSeconds(GlobalTestData.wait));
        WebElement element = owait.until(ExpectedConditions.elementToBeClickable(locator));
        executor.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();}, 300)", element);
        System.out.println("User clicks on '" + desc + "'");
    }

    public static void clickJS(WebDriver driver, WebElement element, String desc){
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();}, 300)", element);
        System.out.println("User clicks on '" + desc + "'");
    }

    public static boolean isPresent(WebDriver driver, By locator) {
        boolean res = false;
        owait = new WebDriverWait(driver, Duration.ofSeconds(GlobalTestData.wait));
        List<WebElement> list;
        try {
            list = owait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (Exception e) {
            list = driver.findElements(locator);
        }
        if (list.size() > 0) {
            res = true;
            System.out.println(locator + " is visible.");
        } else {
            res = false;
            System.out.println(locator + " is not present in DOM.");
        }
        return res;
    }

    public static boolean isClickable(WebDriver driver, By locator){
        boolean status = false;
        owait = new WebDriverWait(driver, Duration.ofSeconds(GlobalTestData.wait));
        try {
            WebElement element = owait.until(ExpectedConditions.elementToBeClickable(locator));
            status = element.isEnabled();
        } catch (Exception e) {
            System.out.println("Element is not clickable!!!!!!!"+e.getMessage());
        }
        return status;
    }

    public static void scroll(WebDriver driver, By locator) {
        WebElement element = waitForElement(driver, locator);
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            // js.executeScript("window.scrollBy(0,-250)", "");
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println(element.toString() + " not found while scrolling to element.");
        }
    }

    public static void scroll(WebDriver driver, WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);

            // js.executeScript("window.scrollBy(0,-250)", "");
        } catch (NoSuchElementException e) {
            System.out.println(element.toString() + " not found while scrolling to element.");
        }
    }


    public static void scroll(WebDriver driver) {
        try {
    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    // Scroll down by 500 pixels
            jsExecutor.executeScript("window.scrollBy(0, 500)");
        } catch (NoSuchElementException e) {
            System.out.println(" not found while scrolling to element.");
        }
    }
}
