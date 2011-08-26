package pl.com.bottega.erp.crm.acceptance;

import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class FlakyIePropertyWebDriverProvider extends PropertyWebDriverProvider {

    @Override
    protected InternetExplorerDriver createInternetExplorerDriver() {
        DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
        ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        return new InternetExplorerDriver(ieCapabilities);
    }
}
