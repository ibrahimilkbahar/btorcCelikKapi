package tr.com.mindworks.services.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.mindworks.dao.DimensionRepository;
import tr.com.mindworks.dao.FullCaseDepthPriceRangeRepository;
import tr.com.mindworks.model.TDimension;
import tr.com.mindworks.model.TFullCaseDepthPriceRange;
import tr.com.mindworks.model.TProductCaseType;
import tr.com.mindworks.services.DimensionService;

@Service("dimensionService")
public class DimensionServiceImpl implements DimensionService, Serializable {
 
	@Autowired
	private DimensionRepository dimensionRepository;
	@Autowired
	private FullCaseDepthPriceRangeRepository fullCaseDepthPriceRangeRepository;
 
	
	
	@Override
	public void saveDimension(TDimension dimension) {
		dimensionRepository.save(dimension);
	}
	@Override
	public void deleteDimension(Integer dimensionId) {
		dimensionRepository.delete(dimensionId);
	}
	@Override
	public List<TDimension> findAllDimensionsBy_Type_Wing(Integer productTypeId, 
														  Integer productWingTypeId) {
		return dimensionRepository.findAllDimensionsBy_Type_Wing(productTypeId,productWingTypeId);
	}
	
	
	@Override
	public List<TFullCaseDepthPriceRange> findAllFullCaseDepthPriceRanges(Integer productTypeId,
																	      Integer productWingTypeId) {
		return fullCaseDepthPriceRangeRepository.findAllFullCaseDepthPriceRanges(productTypeId, productWingTypeId);
	}
	@Override
	public void saveFullCaseDepthPriceRange(TFullCaseDepthPriceRange newFullCaseDepthPriceRange) {
		fullCaseDepthPriceRangeRepository.save(newFullCaseDepthPriceRange);
	}
	
	
	
	
	
}
