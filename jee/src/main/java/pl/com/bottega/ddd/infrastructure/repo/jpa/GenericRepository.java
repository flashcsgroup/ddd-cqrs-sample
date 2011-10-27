package pl.com.bottega.ddd.infrastructure.repo.jpa;

public interface GenericRepository {

	Object load(Object id);

	void persist(Object entity);

	Object save(Object entity);

}