package pl.com.bottega.erp.crm.acceptance.agents.browser;

import org.apache.commons.lang.StringUtils;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import pl.com.bottega.erp.crm.acceptance.spring.BrowserAgent;

@BrowserAgent
public class BrowserAgentDriverProvider extends PropertyWebDriverProvider {

    @Override
    protected InternetExplorerDriver createInternetExplorerDriver() {
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        return new InternetExplorerDriver(capabilities);
    }

    @Override
    protected ChromeDriver createChromeDriver() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        String chromeBinary = System.getProperty("chrome.binary");
        if (StringUtils.isNotBlank(chromeBinary)) {
            capabilities.setCapability("chrome.binary", chromeBinary);
        }
        return new ChromeDriver(capabilities);
    }

    @BeforeStories
    public void beforeStories() throws Exception {
        initialize();
    }

    @AfterStories
    public void afterStories() throws Exception {
        get().quit();
    }

    @BeforeScenario
    public void beforeEachScenario() throws Exception {
        get().manage().deleteAllCookies();
    }
}
