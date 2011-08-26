package pl.com.bottega.erp.crm.acceptance.pages;

import javax.inject.Inject;

import org.jbehave.web.selenium.WebDriverProvider;

import pl.com.bottega.erp.crm.acceptance.spring.Page;

@Page
public class OrderConfirmationPage extends BasePage {

    private static final String PAGE_URL_REGEXP = "^sales/confirmOrder/\\d+$";

    @Inject
    public OrderConfirmationPage(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    public boolean isCurrentPage() {
        return getCurrentPagePath().matches(PAGE_URL_REGEXP);
    }

    public int getProductsCount() {
        return elements(".ordersTable tbody>tr").size();
    }

    public void confirm() {
        element("#orderConfirmationForm input[type=submit]").click();
    }
}
