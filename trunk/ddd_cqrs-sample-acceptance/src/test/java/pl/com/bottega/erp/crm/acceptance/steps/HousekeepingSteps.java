package pl.com.bottega.erp.crm.acceptance.steps;

import javax.inject.Inject;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.web.selenium.WebDriverProvider;

import pl.com.bottega.erp.crm.acceptance.spring.Steps;

@Steps
public class HousekeepingSteps {

    @Inject
    private WebDriverProvider driverProvider;

    @BeforeStories
    public void beforeStories() throws Exception {
        driverProvider.initialize();
    }

    @AfterStories
    public void afterStories() throws Exception {
        driverProvider.get().quit();
    }

    @BeforeScenario
    public void beforeEachScenario() throws Exception {
        driverProvider.get().manage().deleteAllCookies();
    }
}
