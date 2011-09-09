package pl.com.bottega.erp.crm.acceptance.agents.browser;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import pl.com.bottega.erp.crm.acceptance.spring.BrowserAgent;

@BrowserAgent
public class FlakyIePropertyWebDriverProvider extends PropertyWebDriverProvider {

    @Override
    protected InternetExplorerDriver createInternetExplorerDriver() {
        DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
        ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        return new InternetExplorerDriver(ieCapabilities);
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
