package pl.com.bottega.ddd.sagas;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;

import pl.com.bottega.ddd.domain.DomainEventPublisher;

public class SagaIntegrationTest {

    @Inject
    private DomainEventPublisher publisher;

    @Inject
    private SagaSpy spy;

    /**
     * testing {@link SimpleSaga}
     */
    @Test
    @Ignore
    public void shouldRunSimpleTwoStepSaga() throws Exception {
        // when
        publisher.publish(new SampleDomainEvent(1L));
        publisher.publish(new AnotherDomainEvent(1L, "data"));
        // then
        assertEquals(1, spy.getSampleEventHandledCount());
        assertEquals(1, spy.getAnotherEventHandledCount());
        assertEquals(1, spy.getSagaCompletedCount());
    }

    @Test
    @Ignore
    public void shouldNotCompleteSameSagaTwice() throws Exception {
        // when
        publisher.publish(new SampleDomainEvent(1L));
        publisher.publish(new AnotherDomainEvent(1L, "data"));
        publisher.publish(new SampleDomainEvent(1L));
        // then
        assertEquals(2, spy.getSampleEventHandledCount());
        assertEquals(1, spy.getAnotherEventHandledCount());
        assertEquals(1, spy.getSagaCompletedCount());
    }
}
