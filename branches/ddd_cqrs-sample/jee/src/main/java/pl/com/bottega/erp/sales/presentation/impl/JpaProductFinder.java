package pl.com.bottega.erp.sales.presentation.impl;

import java.util.ArrayList;

import pl.com.bottega.cqrs.query.PaginatedResult;
import pl.com.bottega.cqrs.query.annotations.Finder;
import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductListItemDto;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;

@Finder
public class JpaProductFinder implements ProductFinder {

	@Override
	public PaginatedResult<ProductListItemDto> findProducts(
			ProductSearchCriteria searchCriteria) {
		
		return new PaginatedResult<ProductListItemDto>(new ArrayList<ProductListItemDto>(), 1, 0, 0);
	}

}
