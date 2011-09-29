package pl.com.bottega.erp.crm.acceptance.agents.browser;

import javax.inject.Inject;

import pl.com.bottega.erp.crm.acceptance.agents.ProductsListAgent;
import pl.com.bottega.erp.crm.acceptance.spring.BrowserAgent;

@BrowserAgent
public class ProductsListPage implements ProductsListAgent {

    @Inject
    private BrowserAgentDriver driver;

    @Override
    public boolean hasProducts() {
        ensureOnCurrentPage();
        return !driver.elements("#productsTable tbody tr").isEmpty();
    }

    @Override
    public void addAnyProductToBasket() {
        ensureOnCurrentPage();
        driver.element("tbody td.buttonsColumn input[type=button]").click();
    }

    @Override
    public int getBasketItemsCount() {
        ensureOnCurrentPage();
        return driver.elements("#basketItemsList>li").size();
    }

    @Override
    public void checkout() {
        ensureOnCurrentPage();
        driver.element(".basketContainer input[type=submit]").click();
    }

    private void ensureOnCurrentPage() {
        if (!thisIsCurrentPage()) {
            navigateToThisPage();
        }
    }

    private boolean thisIsCurrentPage() {
        return "sales/products/list".equalsIgnoreCase(driver.getCurrentPagePath());
    }

    private void navigateToThisPage() {
        driver.navigateTo("sales/products/list");
    }
}
