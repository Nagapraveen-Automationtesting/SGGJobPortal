package com.sgg.pages;

import com.sgg.config.BaseClass;
import com.sgg.testdata.GlobalTestData;
import com.sgg.utils.ExcelUtil;
import com.sgg.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class HomePage {

    private WebDriver driver;
    public HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(@data-link-title, 'View All Open Positions')]")
    private WebElement btn_ApplyNow;


    public void clickOnApplyNow(){

//        int count = 0;
//        while(count<10){
//            Util.scroll(driver);
//            List<WebElement> list = Util.getElementsList(driver, By.xpath("//a[contains(@data-link-title, 'View All Open Positions')]"));
//            if(list.size()>0){
//                break;
//            }
//            Util.hardWait(2);
//            count++;
//        }
        try {
//            Util.hardWait(5);
//            Util.scroll(driver);
            Util.clickJS(driver, btn_ApplyNow, "Apply Now");
        }catch (ElementClickInterceptedException e){
            Util.hardWait(2);
            Util.scroll(driver);
            Util.clickJS(driver, btn_ApplyNow, "Apply Now");
        }


    }


    public void getJobData(int iterations){
        List<LinkedHashMap<String, String>> dataTable = new LinkedList<>();
        for(int itr=0;itr<iterations;itr++) {

            List<WebElement> jobs = Util.getElementsList(driver, By.xpath("//mat-panel-title[contains(@class, 'mat-expansion-panel-header-title')]/p/a/span"));
            WebDriverWait owait = new WebDriverWait(driver, Duration.ofSeconds(GlobalTestData.wait));
            for (int i = 0; i < jobs.size(); i++) {
                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                map.put("Job Title", owait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//search-job-cards//mat-expansion-panel[" + (i + 1) + "]//p/a/span"))).getText());
                map.put("Req ID", owait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//search-job-cards//mat-expansion-panel[" + (i + 1) + "]//p[2]/span"))).getText());
                map.put("Location", owait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//search-job-cards//mat-expansion-panel[" + (i + 1) + "]//div/span[1]/div/p/span[2]"))).getText());
                map.put("Categories", owait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//search-job-cards//mat-expansion-panel[" + (i + 1) + "]//div/span[2]/div/p/span[2]"))).getText());
                map.put("Posted On", owait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//search-job-cards//mat-expansion-panel[" + (i + 1) + "]//div/span[3]/div/p/span[2]"))).getText());

                owait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//search-job-cards//mat-expansion-panel[" + (i + 1) + "]//p/a/span"))).click();
                StringBuilder jobDesc = new StringBuilder();
                List<WebElement> descriptions = Util.getElementsList(driver, By.xpath("//article[@id='description-body']/p"));
                for (WebElement each : descriptions) {
                    if (each.getText() != "") {
                        jobDesc.append(each.getText());
                        jobDesc.append("\n");
                    }
                }
                map.put("Description", String.valueOf(jobDesc));
                Util.click(driver, By.xpath("//a[contains(text(), 'Back')]"), "Back");
                dataTable.add(map);
            }
            Util.click(driver, By.xpath("//button[contains(@aria-label, 'Next Page')]/span[1]"), "Next");
        }
        ExcelUtil.storeTableValuesIntoExcel(BaseClass.folderPath+ File.separator+BaseClass.fileName, dataTable);
    }

}
