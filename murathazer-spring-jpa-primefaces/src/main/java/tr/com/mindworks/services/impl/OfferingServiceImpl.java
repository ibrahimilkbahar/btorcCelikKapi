package tr.com.mindworks.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.mindworks.dao.OfferingPropvalRepository;
import tr.com.mindworks.dao.OfferingRepository;
import tr.com.mindworks.dao.OrderOfferingPropertyRepository;
import tr.com.mindworks.dao.PropertyRepository;
import tr.com.mindworks.dao.PropertyValueRepository;
import tr.com.mindworks.model.TOffering;
import tr.com.mindworks.model.TOfferingPropval;
import tr.com.mindworks.model.TOrderOfferingPropval;
import tr.com.mindworks.model.TProperty;
import tr.com.mindworks.model.TPropertyValue;
import tr.com.mindworks.services.OfferingService;

@Service("offeringService")
public class OfferingServiceImpl implements OfferingService, Serializable {
	@Autowired
	private OfferingRepository offeringRepository;
	@Autowired
	private OfferingPropvalRepository offeringPropvalRepository;
	@Autowired
	private PropertyRepository propertyRepository;
	@Autowired
	private PropertyValueRepository propertyValueRepository;
	@Autowired
	private OrderOfferingPropertyRepository orderOfferingPropertyRepository;

 
	
	@Override
	public List<TOffering> findAll() {
		return offeringRepository.findAll();
	}

	@Override
	public void deleteOffering(Integer offeringId) {
		offeringRepository.delete(offeringId);
	}

	@Override
	public TOffering findOfferingById(Integer offeringId) {
		return offeringRepository.findOne(offeringId);
	}

	@Override
	public void saveOffering(TOffering offering) {
		offeringRepository.save(offering);
	}

	

	@Override
	public DualListModel<TProperty> getOfferingPropvalList(Integer offeringId, Integer propertyGroupId) {
		List<TProperty> propertySource = new ArrayList<TProperty>();
		List<TProperty> propertyTarget = new ArrayList<TProperty>();
		propertySource = propertyRepository.findAvailablePropertiesForThatOffering(offeringId, propertyGroupId);
		propertyTarget = offeringPropvalRepository.findAllPropertiesOfOffering(offeringId, propertyGroupId);
		return new DualListModel<TProperty>(propertySource, propertyTarget);
	}

	@Override
	public List<TOfferingPropval> findAllOfferingPropvalList(Integer offeringId) {
		return offeringPropvalRepository.findAllPropertiesOfOffering(offeringId);
	}

	@Override
	public void updateOfferingPropvalList(List updatedPropertyListString, TOffering offering, Integer propertyGroupId) {
		if (updatedPropertyListString.size() == 0) {
			List<TOfferingPropval> currentPropertyList = offeringPropvalRepository.findAllOfferingPropvalOfOffering(offering.getId(), propertyGroupId);
			for (TOfferingPropval tOfferingPropval : currentPropertyList) {
				offeringPropvalRepository.delete(tOfferingPropval.getId());
				propertyValueRepository.delete(tOfferingPropval.getPropertyValue().getId());
				
			}
		}else if (updatedPropertyListString.size() > 0) {
			List<TProperty> currentPropertyList = offeringPropvalRepository.findAllPropertiesOfOffering(offering.getId(), propertyGroupId);
			List<TProperty> updatedPropertyList = new ArrayList<TProperty>();

			for (Object obj : updatedPropertyListString) {
				updatedPropertyList.add(propertyRepository.findOne(Integer.parseInt(obj.toString())));
			}

			HashMap<String, TProperty> updatedProperties = new HashMap<String, TProperty>();
			HashMap<String, TProperty> currentProperties = new HashMap<String, TProperty>();

			for (TProperty tProperty : updatedPropertyList)
				updatedProperties.put(tProperty.getCode(), tProperty);

			for (TProperty tProperty : currentPropertyList)
				currentProperties.put(tProperty.getCode(), tProperty);

			for (int i = 0; i < updatedPropertyList.size(); i++) {
				TProperty tProperty = updatedPropertyList.get(i);

				if (currentProperties.containsKey(tProperty.getCode())) {
					TOfferingPropval offProp = offeringPropvalRepository.findByOfferingIdAndPropertyCode(offering.getId(), tProperty.getCode());
					offProp.setOrderBy(i);
					offeringPropvalRepository.save(offProp);
				} else {
					TOfferingPropval offProp = new TOfferingPropval();
					offProp.setOrderBy(i);
					offProp.setOffering(offering);
					offProp.setIsMondatory(false);
					offProp.setIsVisibleOnReport(true);
					
					TPropertyValue pv = new TPropertyValue();
					pv.setProperty(tProperty);
					propertyValueRepository.save(pv);
					
					offProp.setPropertyValue(pv);
					
					offeringPropvalRepository.save(offProp);
				}
			}

			for (TProperty tProperty : currentPropertyList) {
				if (!updatedProperties.containsKey(tProperty.getCode())) {
					offeringPropvalRepository.deleteByOfferingIdAndPropertyId(offering.getId(), tProperty.getId());
				}
			}
		}
	}

	@Override
	public void updateOfferingPropval(TOfferingPropval offeringPropval) {
		
		propertyValueRepository.save(offeringPropval.getPropertyValue());
		offeringPropvalRepository.save(offeringPropval);
		
		List<TOrderOfferingPropval> list = orderOfferingPropertyRepository.findDraftOrderOfferingPropertyBehaviour(offeringPropval.getOffering().getId(),
																	 offeringPropval.getPropertyValue().getProperty().getId(),
																	 offeringPropval.getIsMondatory());
		for (TOrderOfferingPropval oopv : list) {
			oopv.setIsMondatory(offeringPropval.getIsMondatory());
			orderOfferingPropertyRepository.save(oopv);
		}
	}

	 
	 

	

	
}
