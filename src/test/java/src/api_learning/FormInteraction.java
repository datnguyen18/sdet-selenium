package src.api_learning;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import src.driver.DriverFactory;

import java.time.Duration;
import java.util.List;

public class FormInteraction {
    private static final String USER_NAME = "tomsmith";
    private static final String PASSWORD = "SuperSecretPassword!";

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();
        By usernameSelector = By.id("username");
        By passwordSelector = By.name("password");
        By loginBtnSelector = By.cssSelector("button i[class*=sign-in]");

        try {
            driver.get("https://the-internet.herokuapp.com/login");
            WebElement userNameElement = driver.findElement(usernameSelector);
            WebElement passwordElement = driver.findElement(passwordSelector);
            WebElement loginBtnElement = driver.findElement(loginBtnSelector);

            userNameElement.sendKeys(USER_NAME);
            passwordElement.sendKeys(PASSWORD);
            loginBtnElement.click();

            By headingTitle = By.tagName("h2");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(headingTitle)));
            String titleText = driver.findElement(headingTitle).getText();
            System.out.println("Heading title: " + titleText);

            //link Text, partially link text
            System.out.println(driver.findElement(By.linkText("Elemental Selenium")).getText());
            System.out.println(driver.findElement(By.partialLinkText("Elemental")).getText());
            System.out.println(driver.findElement(By.partialLinkText("Elemental")).getAttribute("href"));
            System.out.println(driver.getCurrentUrl());
            System.out.println(driver.getTitle());
            driver.findElement(By.linkText("Logout")).click();
            //handle multiple matching
            List<WebElement> inputElements = driver.findElements(By.tagName("input"));

            if (inputElements.isEmpty()) {
                throw new RuntimeException("[ERR] No input fields found");
            }

            inputElements.get(0).sendKeys(USER_NAME);
            inputElements.get(1).sendKeys(PASSWORD);
            loginBtnElement = driver.findElement(loginBtnSelector);
            loginBtnElement.click();

            titleText = driver.findElement(headingTitle).getText();
            System.out.println("Heading title: " + titleText);
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.quit();
    }
}
