package pl.com.bottega.erp.crm.acceptance.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import pl.com.bottega.erp.crm.acceptance.pages.OrderConfirmationPage;
import pl.com.bottega.erp.crm.acceptance.pages.ProductsListPage;
import pl.com.bottega.erp.crm.acceptance.pages.ProductsListPage.ProductColumn;
import pl.com.bottega.erp.crm.acceptance.spring.Steps;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;

@Steps
public class ProductsOrderingSteps {

    @Inject
    private ProductsListPage homePage;

    @Inject
    private OrderConfirmationPage orderConfirmationPage;

    @Given("I am on home page")
    public void onHomePage() {
        homePage.goTo();
    }

    @When("I add some product to basket")
    public void addSomeProductToBasket() {
        homePage.addAnyProductToBasket();
    }

    @When("Wait")
    public void waitForAjax() throws Exception {
        Thread.sleep(500);
    }

    @Then("Basket should have $number items")
    @Alias("Basket should have $number item")
    public void shouldBasketHaveItems(int itemsCount) {
        assertEquals(itemsCount, homePage.getBasketItemsCount());
    }

    @When("I checkout")
    public void checkout() {
        homePage.checkout();
    }

    @Then("I should be on the order confirmation page with $number product")
    @Alias("I should be on the order confirmation page with $number products")
    public void shouldBeOnOrderPageWithProducts(int productsCount) throws Exception {
        assertTrue(orderConfirmationPage.isCurrentPage());
        assertEquals(productsCount, orderConfirmationPage.getProductsCount());
    }

    @When("I confirm the order")
    public void confirmOrder() {
        orderConfirmationPage.confirm();
    }

    @Then("I should be on the home page")
    public void shouldBeOnHomePage() {
        assertTrue(homePage.isCurrent());
    }

    @When("I search for products with '$keyword' keyword")
    public void searchForProduct(String keyword) {
        homePage.filterProductsByName(keyword);
    }

    @Then("All visible products should have '$keyword' in their name")
    public void productsNamesShouldContain(String keyword) {
        List<String> productsNames = homePage.getProductsColumn(ProductColumn.NAME);
        assertTrue(Iterables.all(productsNames, contain(keyword)));
    }

    private Predicate<String> contain(final String keyword) {
        return new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return input.toUpperCase().contains(keyword.toUpperCase());
            }
        };
    }

    @When("I sort products by name")
    public void sortProductsByPrice() {
        homePage.sortBy(ProductColumn.NAME);
    }

    @Then("All visible products should be sorted ascending by name")
    public void visibleProductsShouldBeSortedByName() {
        List<String> productsNames = homePage.getProductsColumn(ProductColumn.NAME);
        assertTrue(Ordering.natural().reverse().isOrdered(productsNames));
    }
}
