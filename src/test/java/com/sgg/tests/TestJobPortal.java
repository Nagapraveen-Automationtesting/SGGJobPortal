package com.sgg.tests;

import com.sgg.config.BaseClass;
import com.sgg.pages.HomePage;
import com.sgg.testdata.GlobalTestData;
import com.sgg.utils.ExcelUtil;
import com.sgg.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class TestJobPortal extends BaseClass {


    @Test
    public void validateJobPortal () {
        HomePage home = new HomePage(driver);
        home.clickOnApplyNow();
        home.getJobData(5);
    }


}
