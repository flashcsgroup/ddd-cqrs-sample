package pl.com.bottega.erp.crm.acceptance.pages;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.jbehave.web.selenium.WebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;

public abstract class BasePage extends WebDriverPage {

    @Value("${contextPath}")
    private String contextPath;

    @Value("${serverUrl}")
    private String serverUrl;

    public BasePage(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    // helper functions and shortcuts
    public WebElement element(String cssSelector) {
        return findElement(By.cssSelector(cssSelector));
    }

    public List<WebElement> elements(String cssSelector) {
        return findElements(By.cssSelector(cssSelector));
    }

    public void navigateTo(String path) {
        navigate().to(serverUrl + "/" + contextPath + "/" + path);
    }

    public String getCurrentPagePath() {
        String currentPath = getCurrentPageURI().getPath();
        if (currentPath.toUpperCase().startsWith("/" + contextPath.toUpperCase() + "/")) {
            return currentPath.substring(contextPath.length() + 2);
        } else {
            throw new RuntimeException("current page is outside of the context path");
        }
    }

    private URI getCurrentPageURI() {
        try {
            return new URI(getCurrentUrl());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}