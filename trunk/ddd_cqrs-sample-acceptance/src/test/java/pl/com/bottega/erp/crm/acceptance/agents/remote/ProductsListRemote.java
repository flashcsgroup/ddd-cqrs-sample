package pl.com.bottega.erp.crm.acceptance.agents.remote;

import javax.inject.Inject;

import pl.com.bottega.erp.crm.acceptance.agents.ProductsListAgent;
import pl.com.bottega.erp.crm.acceptance.spring.RemoteAgent;

/**
 * @author Rafał Jamróz
 */
@RemoteAgent
public class ProductsListRemote implements ProductsListAgent {

    /**
     * Order in the scenario context. Order is created after successful checkout
     * and its id is stored here.
     */
    @Inject
    private CurrentOrder currentOder;

    @Override
    public void checkout() {
        // send(new CreateOrderCommand(...))
        // currentOrder = received order id
    }

    @Override
    public int getBasketItemsCount() {
        // query for basket items
        return 1;
    }

    @Override
    public void addAnyProductToBasket() {
        // query products and add something to basket
    }

    @Override
    public boolean hasProducts() {
        // query for products
        return true;
    }
}
