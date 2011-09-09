package pl.com.bottega.erp.crm.acceptance.agents.remote;

import org.jbehave.core.annotations.AfterScenario;
import org.springframework.stereotype.Component;

/**
 * Scenario context component. Used to keep values between steps.
 * 
 * @author Rafał Jamróz
 */
@Component
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
