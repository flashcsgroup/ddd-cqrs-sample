/**
 * 
 */
package pl.com.bottega.erp.sales.infrastructure.events.listeners.application;

import javax.enterprise.event.Observes;

import pl.com.bottega.ddd.infrastructure.events.AsynchronousEvent;
import pl.com.bottega.ddd.infrastructure.events.EventListener;
import pl.com.bottega.ddd.infrastructure.events.EventListeners;
import pl.com.bottega.erp.sales.application.events.ProductAddedToOrderEvent;

/**
 * @author Slawek
 *
 */
@EventListeners
public class ProductAddedToOrderListener{

	@EventListener(asynchronous=true)
	public void handle(@Observes @AsynchronousEvent ProductAddedToOrderEvent event) {
		System.out.println("Spy report:: client: " + event.getClientId() + " have added product: " + event.getProductid());
		
	}
}
