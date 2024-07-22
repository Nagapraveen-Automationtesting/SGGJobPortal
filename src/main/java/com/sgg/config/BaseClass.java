package com.sgg.config;

import com.sgg.utils.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class BaseClass {

    public WebDriver driver;

    public static  String folderPath = System.getProperty("user.dir") + File.separator + "JobData";
    public static  String fileName = "";

    @BeforeSuite
    public void beforeSuite(){
        Path path = Paths.get(folderPath);
        if (Files.exists(path) && Files.isDirectory(path)) {
        }else{
            Util.createFolder(folderPath);
        }
        folderPath = folderPath + File.separator + Util.getFolderName();
        Path path1 = Paths.get(folderPath);

        if (Files.exists(path1) && Files.isDirectory(path1)) {
            System.out.println("Folder already exists!!!!!!!!!!!!!!!!!!!!!!!!!");
        }else{
            Util.createFolder(folderPath);
            System.out.println("Folder created.........................");
        }

    }

    @BeforeMethod
    public void beforeMethod(){
        fileName = Util.getTimeStamp() + ".xlsx";
        launchBrowser("chrome");
    }

    @AfterMethod
    public void afterMethod(){

        if(driver != null ){
            driver.quit();
        }

    }

    @AfterSuite
    public void afterSuite(){

    }

    public void launchBrowser(String browser){
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-gpu");
            options.addArguments("--start-maximized");
            options.addArguments("--test-type");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
//                options.setBinary("/usr/bin/google-chrome-stable");
//            WebDriverManager.chromedriver().setup();
//                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+File.separator+"Drivers/chromedriver.exe");
//                System.setProperty("webdriver.chrome.driver","C:\\Users\\hi\\Downloads\\chromedriver_win32\\chromedriver.exe");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions fo = new FirefoxOptions();
            driver = new FirefoxDriver(fo);
        } else if (browser.equalsIgnoreCase("edge")) {
//                EdgeOptions eo = new EdgeOptions();
//                System.setProperty("webdriver.edge.driver", "/Users/naga.kakanuru/Automation_Workspace/MyWorkSpace/JioHealthHub/Drivers/msedgedriver");
            driver = new EdgeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.spglobal.com/en/explore-s-p-global/careers");
    }

    public static void main(String[] args) {
        Path path = Paths.get(folderPath);
        if (Files.exists(path) && Files.isDirectory(path)) {

        }else{
            Util.createFolder(folderPath);
        }
        folderPath = folderPath + File.separator + Util.getFolderName();
        Path path1 = Paths.get(folderPath);

        if (Files.exists(path1) && Files.isDirectory(path1)) {

            System.out.println("Folder already exists!!!!!!!!!!!!!!!!!!!!!!!!!");
        }else{
            Util.createFolder(folderPath);
            System.out.println("Folder created.........................");

        }
    }
}
