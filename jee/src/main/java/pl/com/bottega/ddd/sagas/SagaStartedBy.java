package pl.com.bottega.ddd.sagas;

public interface SagaStartedBy<T> {

	public void handle(T event);
}
