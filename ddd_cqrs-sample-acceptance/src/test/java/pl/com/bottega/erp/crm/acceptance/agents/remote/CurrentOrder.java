package pl.com.bottega.erp.crm.acceptance.agents.remote;

import org.jbehave.core.annotations.AfterScenario;

import pl.com.bottega.erp.crm.acceptance.spring.RemoteAgent;

/**
 * Scenario context component. Used to keep values between steps.
 * 
 * @author Rafał Jamróz
 */
@RemoteAgent
public class CurrentOrder {

    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @AfterScenario
    public void clearContext() {
        orderId = null;
    }
}
