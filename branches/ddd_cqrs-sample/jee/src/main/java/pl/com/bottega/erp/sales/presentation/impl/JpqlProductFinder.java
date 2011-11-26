package pl.com.bottega.erp.sales.presentation.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.com.bottega.cqrs.query.PaginatedResult;
import pl.com.bottega.cqrs.query.annotations.Finder;
import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductListItemDto;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;

/**
 * Product finder implementation is based on JPQL.
 */
@Finder
public class JpqlProductFinder implements ProductFinder {

	@PersistenceContext(unitName="defaultPU")
	EntityManager em;

	private static final String QUERY_STRING = "select new " + ProductListItemDto.class.getName() + "(p.entityId, p.name, p.price) from Product p";
	
	@Override
	public PaginatedResult<ProductListItemDto> findProducts(
			ProductSearchCriteria searchCriteria) {

		
		List<ProductListItemDto> resultList = em.createQuery(QUERY_STRING).getResultList();
		
		// TODO include criteria
		return new PaginatedResult<ProductListItemDto>(resultList, 1, resultList.size(), resultList.size());
	}

}
