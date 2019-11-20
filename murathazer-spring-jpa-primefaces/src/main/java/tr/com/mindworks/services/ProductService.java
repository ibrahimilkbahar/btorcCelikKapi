package tr.com.mindworks.services;

import java.util.List;

import tr.com.mindworks.model.TProductCaseType;
import tr.com.mindworks.model.TProductSurfaceType;
import tr.com.mindworks.model.TProductType;
import tr.com.mindworks.model.TProductWingType;

public interface ProductService
{
	List<TProductType> findAllProductTypes();
	List<TProductSurfaceType> findAllProductSurfaceTypes();
	List<TProductWingType> findAllProductWingTypes();
	List<TProductCaseType> findAllProductCaseTypes();
	
}
