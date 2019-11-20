package tr.com.mindworks.services;

import java.util.List;

import org.primefaces.model.DualListModel;

import tr.com.mindworks.model.TOffering;
import tr.com.mindworks.model.TOfferingPropval;
import tr.com.mindworks.model.TProperty;

public interface OfferingService
{
	List<TOffering> findAll();
	void deleteOffering(Integer offeringId);
	TOffering findOfferingById(Integer offeringId);
	void saveOffering(TOffering offering);
	

	
	
	
	
	DualListModel<TProperty> getOfferingPropvalList(Integer offeringId, Integer propertyGroupId);
	List<TOfferingPropval> findAllOfferingPropvalList(Integer offeringId);
	void updateOfferingPropvalList(List updatedPropertyListString, TOffering offering, Integer propertyGroupId);
	void updateOfferingPropval(TOfferingPropval offeringPropval);

	
}
