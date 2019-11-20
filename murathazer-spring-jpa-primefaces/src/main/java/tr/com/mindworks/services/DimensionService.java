package tr.com.mindworks.services;

import java.util.List;

import tr.com.mindworks.model.TDimension;
import tr.com.mindworks.model.TFullCaseDepthPriceRange;

public interface DimensionService
{

	void saveDimension(TDimension dimension);

	void deleteDimension(Integer dimensionId);

	List<TDimension> findAllDimensionsBy_Type_Wing(Integer productTypeId, 
												   Integer productWingTypeId);

	List<TFullCaseDepthPriceRange> findAllFullCaseDepthPriceRanges(Integer productTypeId, Integer productWingTypeId);

	void saveFullCaseDepthPriceRange(TFullCaseDepthPriceRange newFullCaseDepthPriceRange);
 
	
	

	


}
