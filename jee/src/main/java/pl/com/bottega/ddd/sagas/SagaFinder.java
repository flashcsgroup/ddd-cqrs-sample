package pl.com.bottega.ddd.sagas;

import java.io.Serializable;

/**
 * Generic interface for saga finders.
 * @param <D> saga data type
 * @param <E> event type
 */
public interface SagaFinder<D, E extends Serializable> {

	D find(E event);
}
