package pl.com.bottega.erp.crm.acceptance.agents.remote;

import javax.inject.Inject;

import pl.com.bottega.erp.crm.acceptance.agents.OrderConfirmationAgent;
import pl.com.bottega.erp.crm.acceptance.spring.RemoteAgent;

/**
 * @author Rafał Jamróz
 */
@RemoteAgent
public class OrderConfirmationRemote implements OrderConfirmationAgent {

    /**
     * Order in the scenario context.
     * 
     * @see ProductsListRemote#currentOrder
     */
    @Inject
    private CurrentOrder currentOder;

    @Override
    public int getProductsCount() {
        // return finder.getOrder(currentOrder.getId()).getProductsCount();
        return 0;
    }

    @Override
    public void submit() {
        // send(new SubmitOrderCommand(currentOrder.getId())
    }

    @Override
    public boolean isSubmitted() {
        // return finder.getOrder(currentOrder.getId()).isConfirmed();
        return false;
    }
}
