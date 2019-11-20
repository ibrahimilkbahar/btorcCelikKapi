package tr.com.mindworks.controllers.offering;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Provider;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;
import tr.com.mindworks.annotations.SpringFlowScoped;
import tr.com.mindworks.controllers.BaseController;
import tr.com.mindworks.model.TOffering;
import tr.com.mindworks.model.TOfferingPropval;
import tr.com.mindworks.model.TProductSurfaceType;
import tr.com.mindworks.model.TProductType;
import tr.com.mindworks.model.TProductWingType;
import tr.com.mindworks.model.TProperty;
import tr.com.mindworks.model.TPropertyGroup;
import tr.com.mindworks.model.TPropertyLov;
import tr.com.mindworks.services.OfferingService;
import tr.com.mindworks.services.ProductService;

@Component("offeringController")
@SpringFlowScoped
@SessionScope
public class OfferingController extends BaseController {

	@Autowired
	private Provider<OfferingService> offeringService;
	@Autowired
	private Provider<ProductService> productService;
	
	@Getter
	@Setter
	private TOffering offering;
	@Getter
	@Setter
	private List<TOffering> offeringList;

	
	@Getter
	@Setter
	private List<TProductType> productTypeList;
	@Getter
	@Setter
	private List<TProductSurfaceType> productSurfaceTypeList;
	@Getter
	@Setter
	private List<TProductWingType> productWingTypeList;

	
	@Getter
	@Setter
	private Integer productTypeId,productSurfaceTypeId,productWingTypeId;

	@Getter
	@Setter
	private DualListModel<TProperty> propertyDualListMetal;
	@Getter
	@Setter
	private DualListModel<TProperty> propertyDualListWooden;
	@Getter
	@Setter
	private DualListModel<TProperty> propertyDualListAccessory;
	@Getter
	@Setter
	private DualListModel<TProperty> propertyDualListOther;

	@Getter
	@Setter
	private List<TOfferingPropval> offeringPropvalList;
	@Getter
	@Setter
	private TOfferingPropval newOfferingPropval;
	
	

	
	
	
	@PostConstruct
	public void initOfferingList() {
		offeringList = offeringService.get().findAll();
		
		loadDictionaryTables();
		
	}

	public void initializeDetailOffering() {
		FacesContext context = FacesContext.getCurrentInstance();
		String offeringId = context.getExternalContext().getRequestParameterMap().get("offeringId");
		if (offeringId == null || "".equals(offeringId)) {
			jsfMessageUtil.addInfoMessage("offering bulunamadı.");
		} else {
			offering = offeringService.get().findOfferingById(Integer.parseInt(offeringId));
			
			productTypeId = offering.getProductType().getId();
			productSurfaceTypeId = offering.getProductSurfaceType().getId();
			productWingTypeId = offering.getProductWingType().getId();
			
			loadDictionaryTables();
			
			
			
			newOfferingPropval = new TOfferingPropval();
			initDualLists();
		}
	}
	
	private void loadDictionaryTables() {
		productTypeList = productService.get().findAllProductTypes();
		productSurfaceTypeList = productService.get().findAllProductSurfaceTypes();
		productWingTypeList = productService.get().findAllProductWingTypes();
	}

	public void initializeCreateOffering() {
		offering = new TOffering();
		loadDictionaryTables();
		newOfferingPropval = new TOfferingPropval();
	}
	

	
	public String saveOffering() {
		try {
			offering.setProductType(new TProductType(productTypeId));
			offering.setProductSurfaceType(new TProductSurfaceType(productSurfaceTypeId));
			offering.setProductWingType(new TProductWingType(productWingTypeId));
		
			
			offeringService.get().saveOffering(offering);

			initDualLists();
			jsfMessageUtil.addInfoMessage("Sunu Kaydedildi. :)");
			return "offeringSaved";
		} catch (Exception exception) {
			jsfMessageUtil.addInfoMessage("Sunu Kaydedilemedi.!!!"+ exception.getMessage());
			return null;
		}
	}
	
	public void deleteOffering(TOffering offering) {
		offering.setIsSaleable(false);
		offeringService.get().saveOffering(offering);
		offeringList = offeringService.get().findAll();
	}
	
	private void initDualLists() {
		propertyDualListMetal = offeringService.get().getOfferingPropvalList(offering.getId(), TPropertyGroup.PROPERTY_GROUP_METAL.getId());
		propertyDualListWooden = offeringService.get().getOfferingPropvalList(offering.getId(), TPropertyGroup.PROPERTY_GROUP_WOODEN.getId());
		propertyDualListAccessory = offeringService.get().getOfferingPropvalList(offering.getId(), TPropertyGroup.PROPERTY_GROUP_ACCESSORY.getId());
		propertyDualListOther = offeringService.get().getOfferingPropvalList(offering.getId(), TPropertyGroup.PROPERTY_GROUP_OTHERS.getId());
		offeringPropvalList= offeringService.get().findAllOfferingPropvalList(offering.getId());
	}

	

	

	
	
	public void cloneOffering(TOffering offering) {
		
	}


	public void saveOfferingPropvalList() {
		offeringService.get().updateOfferingPropvalList(propertyDualListMetal.getTarget(), offering, TPropertyGroup.PROPERTY_GROUP_METAL.getId());
		offeringService.get().updateOfferingPropvalList(propertyDualListWooden.getTarget(), offering, TPropertyGroup.PROPERTY_GROUP_WOODEN.getId());
		offeringService.get().updateOfferingPropvalList(propertyDualListAccessory.getTarget(), offering, TPropertyGroup.PROPERTY_GROUP_ACCESSORY.getId());
		offeringService.get().updateOfferingPropvalList(propertyDualListOther.getTarget(), offering, TPropertyGroup.PROPERTY_GROUP_OTHERS.getId());
		initDualLists();
		jsfMessageUtil.addInfoMessage("Sunu Özellik Dağılımları Kaydedildi. :)");
	}
	
	
	

	
	public void saveOfferingProperty(ActionEvent actionEvent) {
		if(newOfferingPropval.getPropertyValue().getProperty().getPropertyType().getCode().equals("LIST")
		    && newOfferingPropval.getIsMondatory()
		    && newOfferingPropval.getPropertyValue().getPropertyLov() != null
		    && newOfferingPropval.getPropertyValue().getPropertyLov().getPropertyLovDef() !=null
		    && 		(newOfferingPropval.getPropertyValue().getPropertyLov().getId()==null 
		     	  || newOfferingPropval.getPropertyValue().getPropertyLov().getId()==0)) 
		{
			newOfferingPropval.getPropertyValue().setPropertyLov(newOfferingPropval.getPropertyValue().getPropertyLov().getPropertyLovDef().getPropertyLovList().get(0));
		}
		
		if(newOfferingPropval.getPropertyValue().getProperty().getPropertyType().getCode().equals("LIST")
		    && newOfferingPropval.getPropertyValue().getPropertyLov() != null
		    && newOfferingPropval.getPropertyValue().getPropertyLov().getId()==0) 
		{
			newOfferingPropval.getPropertyValue().setPropertyLov(null);
		}
		
		offeringService.get().updateOfferingPropval(newOfferingPropval);
		jsfMessageUtil.addInfoMessage("Sunu Özellik davranışı Kaydedildi. :)");
		newOfferingPropval= new TOfferingPropval();
	}
	
	
	
	
	public void loadOfferingProperty(TOfferingPropval offProp) {
		newOfferingPropval = offProp;

		if (newOfferingPropval.getPropertyValue().getProperty().getPropertyType().getCode().equals("LIST")
				&& newOfferingPropval.getIsMondatory() 
				&& newOfferingPropval.getPropertyValue().getPropertyLov() != null
				&& newOfferingPropval.getPropertyValue().getPropertyLov().getPropertyLovDef() != null
				&& 		  (newOfferingPropval.getPropertyValue().getPropertyLov().getId() == null
						|| newOfferingPropval.getPropertyValue().getPropertyLov().getId() == 0)) {
			newOfferingPropval.getPropertyValue().setPropertyLov(newOfferingPropval.getPropertyValue().getPropertyLov().getPropertyLovDef().getPropertyLovList().get(0));
		}

		else if (newOfferingPropval.getPropertyValue().getProperty().getPropertyType().getCode().equals("LIST")
				&& (newOfferingPropval.getPropertyValue().getPropertyLov() == null
						|| newOfferingPropval.getPropertyValue().getPropertyLov().getId() == null
						|| newOfferingPropval.getPropertyValue().getPropertyLov().getId() == 0)) {
			newOfferingPropval.getPropertyValue().setPropertyLov(new TPropertyLov());
		}
	}
}
