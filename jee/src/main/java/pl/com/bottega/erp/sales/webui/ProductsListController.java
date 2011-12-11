package pl.com.bottega.erp.sales.webui;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import pl.com.bottega.cqrs.query.PaginatedResult;
import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductListItemDto;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria.ProductSearchOrder;

@Named(value="products")
public class ProductsListController {
	
	@Inject
	ProductFinder finder;
	ProductSearchCriteria searchCriteria = new ProductSearchCriteria();
	
	private PaginatedResult<ProductListItemDto> finderResult;
	
	private static final int RESULTS_PER_PAGE = 10;
	
	
	
	public List<ProductListItemDto> getItems()
	{
		if (finderResult == null)
		{
			fetch();
		}
		return finderResult.getItems();
	}
	
	public int getTotalItemsCount()
	{
		if (finderResult == null)
		{
			fetch();
		}

		return finderResult.getTotalItemsCount();
	}
	
	public void sortByName()
	{
		
		searchCriteria.setOrderBy(ProductSearchOrder.NAME);
	}
	
	public void sortByPrice()
	{
		searchCriteria.setOrderBy(ProductSearchOrder.PRICE);
	}
	
	private void fetch()
	{
		finderResult =  finder.findProducts(searchCriteria);
	}
	
	public int getPagesCount()
	{
		if (finderResult == null)
		{
			fetch();
		}
		
		return finderResult.getPagesCount();
	}
	
	public String getContainsTextFilter()
	{
		return searchCriteria.getContainsText();
	}
	
	public void setContainsTextFilter(String containsText)
	{
	    if (containsText != null) {
	    	searchCriteria.setContainsText(containsText.trim());
        }
	}
	
	public void setMaxPriceFilter(Double d)
	{
		searchCriteria.setMaxPrice(d);
	}
	
	public Double getMaxPriceFilter()
	{
		return searchCriteria.getMaxPrice();
	}
	
	public void addToOrder(Long productId)
	{
		
	}
}
