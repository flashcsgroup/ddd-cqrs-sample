package pl.com.bottega.erp.crm.acceptance.agents.browser;

import javax.inject.Inject;

import pl.com.bottega.erp.crm.acceptance.agents.OrderConfirmationAgent;
import pl.com.bottega.erp.crm.acceptance.spring.BrowserAgent;

@BrowserAgent
public class OrderConfirmationPage implements OrderConfirmationAgent {

    private static final String PAGE_URL_REGEXP = "^sales/confirmOrder/\\d+$";

    @Inject
    private BrowserAgentDriver driver;

    public int getProductsCount() {
        ensureOnCurrentPage();
        return driver.elements(".ordersTable tbody>tr").size();
    }

    public void submit() {
        ensureOnCurrentPage();
        driver.element("#orderConfirmationForm input[type=submit]").click();
    }

    @Override
    public boolean isSubmitted() {
        ensureOnCurrentPage();
        return "SUBMITTED".equals(driver.element(".orderConfirmationStatus").getText());
    }

    private void ensureOnCurrentPage() {
        if (!isCurrentPage()) {
            throw new RuntimeException("not on order confirmation page");
        }
    }

    private boolean isCurrentPage() {
        return driver.getCurrentPagePath().matches(PAGE_URL_REGEXP);
    }
}
