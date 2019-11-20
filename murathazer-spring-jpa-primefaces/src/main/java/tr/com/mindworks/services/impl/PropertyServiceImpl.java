package tr.com.mindworks.services.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.mindworks.dao.PropertyLovDefRepository;
import tr.com.mindworks.dao.PropertyLovRepository;
import tr.com.mindworks.dao.PropertyRepository;
import tr.com.mindworks.model.TPriceType;
import tr.com.mindworks.model.TProperty;
import tr.com.mindworks.model.TPropertyGroup;
import tr.com.mindworks.model.TPropertyLov;
import tr.com.mindworks.model.TPropertyLovDef;
import tr.com.mindworks.model.TPropertyType;
import tr.com.mindworks.services.PropertyService;

@Service("propertyService")
public class PropertyServiceImpl implements PropertyService, Serializable {
	@Autowired
	private PropertyRepository propertyRepository;
	@Autowired
	private PropertyLovDefRepository propertyLovDefRepository;
	@Autowired
	private PropertyLovRepository propertyLovRepository;
	
	
	
	@Override
	public List<TProperty> findAll() {
		return propertyRepository.findAll();
	}

	@Override
	public void deleteProperty(TProperty property) {
		propertyRepository.deletePropertyValue(property.getId());
		propertyRepository.deleteProperty(property.getId());

		


	}

	@Override
	public TProperty findPropertyById(Integer propertyId) {
		return propertyRepository.findOne(propertyId);
	}

	@Override
	public void saveProperty(TProperty property) {
		propertyRepository.saveAndFlush(property);
	}
	
	@Override
	public List<TPropertyType> findAllPropertyType() {
		return propertyRepository.findAllPropertyTypes();
	}

	@Override
	public List<TPropertyGroup> findAllPropertyGroup() {
		return propertyRepository.findAllPropertyGroups();
	}
	@Override
	public List<TPriceType> findAllPriceType() {
		return propertyRepository.findAllPriceTypes();
	}
	


	@Override
	public void savePropertyLov(TPropertyLov newPropertyLov) {
		propertyLovRepository.save(newPropertyLov);
	}
	@Override
	public void deletePropertyLov(Integer id) {
		propertyLovRepository.deletePropertyLovById(id);
	}
	@Override
	public List<TPropertyLov> findAllPropertyLovByDefId(Integer propertyLovDefId) {
		return propertyLovRepository.findAllPropertyLovByDefId(propertyLovDefId);
	}
	
	@Override
	public void savePropertyLovDef(TPropertyLovDef propertyLovDef) {
		propertyLovDefRepository.save(propertyLovDef);
	}
	@Override
	public List<TPropertyLovDef> findAllPropertyLovDef() {
		return propertyLovDefRepository.findAll();
	}
	@Override
	public TPropertyLovDef findPropertyLovDefById(Integer propertyLovDefId) {
		return propertyLovDefRepository.findOne(propertyLovDefId);
	}
	@Override
	public void deletePropertyLovDef(Integer propertyLovDefId) {
		propertyLovDefRepository.delete(propertyLovDefId);
	}

	@Override
	public TProperty findPropertyByCode(String code) {
		return propertyRepository.findByCode(code);
	}
}
