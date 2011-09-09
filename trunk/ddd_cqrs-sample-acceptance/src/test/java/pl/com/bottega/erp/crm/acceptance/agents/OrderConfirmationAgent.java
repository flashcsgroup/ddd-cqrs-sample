package pl.com.bottega.erp.crm.acceptance.agents;

public interface OrderConfirmationAgent {

    int getProductsCount();

    void submit();

    boolean isSubmitted();
}
