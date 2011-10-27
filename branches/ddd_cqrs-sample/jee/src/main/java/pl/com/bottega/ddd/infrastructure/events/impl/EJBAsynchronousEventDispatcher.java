package pl.com.bottega.ddd.infrastructure.events.impl;

import java.io.Serializable;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import pl.com.bottega.ddd.infrastructure.events.AsynchronousEvent;

@Stateless
public class EJBAsynchronousEventDispatcher {



	@Inject @AsynchronousEvent Event<Serializable> events;

	@Asynchronous
	public void fire(Serializable event) {
		events.fire(event);
	}




}