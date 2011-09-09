package pl.com.bottega.erp.crm.acceptance.agents;

public interface ProductsListAgent {

    /**
     * checkout products from the basket
     */
    void checkout();
    
    int getBasketItemsCount();

    void addAnyProductToBasket();

    /**
     * @return true if no products are available
     */
    boolean hasProducts();
}
