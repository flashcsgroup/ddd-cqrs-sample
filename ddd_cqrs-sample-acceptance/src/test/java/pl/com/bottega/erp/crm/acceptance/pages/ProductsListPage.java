package pl.com.bottega.erp.crm.acceptance.pages;

import java.util.List;

import javax.inject.Inject;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.WebElement;

import pl.com.bottega.erp.crm.acceptance.spring.Page;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Page
public class ProductsListPage extends BasePage {

    public static enum ProductColumn {
        NAME("td.productNameColumn"), PRICE("td.productNameColumn");

        protected final String columnSelector;

        private ProductColumn(String columnSelector) {
            this.columnSelector = columnSelector;
        }
    }

    @Inject
    public ProductsListPage(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    public void goTo() {
        navigateTo("");
    }

    public void addAnyProductToBasket() {
        element("tbody td.buttonsColumn input[type=button]").click();
    }

    public int getBasketItemsCount() {
        return elements("#basketItemsList>li").size();
    }

    public void checkout() {
        element(".basketContainer input[type=submit]").click();
    }

    public void filterProductsByName(String keyword) {
        element(".filterProductsForm input[name=containsText]").sendKeys(keyword);
        element(".filterProductsForm input[type=submit]").click();
    }

    public boolean isCurrent() {
        return getCurrentPagePath().equalsIgnoreCase("sales/products/list");
    }

    public void sortBy(ProductColumn column) {
        element("#productsTable thead " + column.columnSelector).click();
    }

    public List<String> getProductsColumn(ProductColumn column) {
        return Lists.transform(elements("#productsTable tbody " + column.columnSelector), extractText());
    }

    private Function<WebElement, String> extractText() {
        return new Function<WebElement, String>() {
            @Override
            public String apply(WebElement input) {
                return input.getText();
            }
        };
    }

}
