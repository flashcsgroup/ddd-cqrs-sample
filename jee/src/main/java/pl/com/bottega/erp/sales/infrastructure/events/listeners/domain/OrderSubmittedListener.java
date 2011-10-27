/**
 * 
 */
package pl.com.bottega.erp.sales.infrastructure.events.listeners.domain;

import javax.enterprise.event.Observes;

import pl.com.bottega.ddd.infrastructure.events.AsynchronousEvent;
import pl.com.bottega.ddd.infrastructure.events.EventListener;
import pl.com.bottega.ddd.infrastructure.events.EventListeners;
import pl.com.bottega.erp.sales.domain.events.OrderSubmittedEvent;

/**
 * @author Slawek
 *
 */
@EventListeners
public class OrderSubmittedListener {

	@EventListener(asynchronous=true)
	public void handle(@Observes @AsynchronousEvent OrderSubmittedEvent event) {
		System.out.println("Sending mail aboud order: " + event.getOrderId());	
	}
}
