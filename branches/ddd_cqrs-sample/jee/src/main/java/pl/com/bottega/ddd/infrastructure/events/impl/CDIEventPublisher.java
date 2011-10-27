package pl.com.bottega.ddd.infrastructure.events.impl;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import pl.com.bottega.ddd.application.ApplicationEventPublisher;
import pl.com.bottega.ddd.domain.DomainEvent;
import pl.com.bottega.ddd.domain.DomainEventPublisher;

public class CDIEventPublisher implements ApplicationEventPublisher,
		DomainEventPublisher {


	@Inject Event<Serializable> eventBus;
	
	@EJB EJBAsynchronousEventDispatcher asynchronousDispatcher;
	
	@Override
	public void publish(DomainEvent event) 
	{	
		eventBus.fire(event);
		asynchronousDispatcher.fire(event);
	}

	@Override
	public void publish(Serializable applicationEvent) {
		eventBus.fire(applicationEvent);
		asynchronousDispatcher.fire(applicationEvent);
	}

}
