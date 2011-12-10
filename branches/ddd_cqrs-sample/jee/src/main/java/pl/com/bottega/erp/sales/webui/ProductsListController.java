package pl.com.bottega.erp.sales.webui;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductListItemDto;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;

@Named(value="products")
public class ProductsListController {
	
	@Inject
	ProductFinder finder;
	ProductSearchCriteria searchCriteria = new ProductSearchCriteria();
	
	private List<ProductListItemDto> list = new ArrayList<ProductListItemDto>();
	
	public List<ProductListItemDto> getItems()
	{
		list =  finder.findProducts(searchCriteria).getItems();
		return list;
	}
	
	public int getTotalItemsCount()
	{
		return 1;
	}
	
	public void sortByName()
	{
		
	}
	
	public void sortByPrice()
	{
		
	}
}
