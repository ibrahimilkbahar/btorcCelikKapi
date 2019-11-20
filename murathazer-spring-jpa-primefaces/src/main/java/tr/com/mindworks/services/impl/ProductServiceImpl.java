package tr.com.mindworks.services.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.mindworks.dao.ProductTypeRepository;
import tr.com.mindworks.model.TProductCaseType;
import tr.com.mindworks.model.TProductSurfaceType;
import tr.com.mindworks.model.TProductType;
import tr.com.mindworks.model.TProductWingType;
import tr.com.mindworks.services.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService, Serializable {

	@Autowired
	private ProductTypeRepository productTypeRepository;
	
	@Override
	public List<TProductType> findAllProductTypes() {
		return productTypeRepository.findAllProductTypes();
	}
	@Override
	public List<TProductSurfaceType> findAllProductSurfaceTypes() {
		return productTypeRepository.findAllProductSurfaceTypes();
	}
	@Override
	public List<TProductWingType> findAllProductWingTypes() {
		return productTypeRepository.findAllProductWingTypes();
	}
	@Override
	public List<TProductCaseType> findAllProductCaseTypes() {
		return productTypeRepository.findAllProductCaseTypes();
	}
	
	
	
}
