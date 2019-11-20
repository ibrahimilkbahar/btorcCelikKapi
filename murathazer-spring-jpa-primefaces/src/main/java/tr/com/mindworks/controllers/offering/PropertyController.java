package tr.com.mindworks.controllers.offering;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;
import tr.com.mindworks.annotations.SpringFlowScoped;
import tr.com.mindworks.controllers.BaseController;
import tr.com.mindworks.model.TPriceType;
import tr.com.mindworks.model.TProperty;
import tr.com.mindworks.model.TPropertyGroup;
import tr.com.mindworks.model.TPropertyLovDef;
import tr.com.mindworks.model.TPropertyType;
import tr.com.mindworks.services.PropertyService;

@Component("propertyController")
@SpringFlowScoped
@SessionScope
public class PropertyController extends BaseController {

	@Autowired
	private Provider<PropertyService> propertyService;
	@Getter
	@Setter
	private TProperty property;
	@Getter
	@Setter
	private List<TProperty> propertyList;

	@Getter
	@Setter
	private List<TPropertyType> propertyTypeList;
	@Getter
	@Setter
	private List<TPropertyGroup> propertyGroupList;
	@Getter
	@Setter
	private List<TPriceType> priceTypeList;

	@Getter
	@Setter
	private Integer propertyTypeId, propertyGroupId, propertyLovDefId, priceTypeId;

	@PostConstruct
	public void initPropertyList() {
		propertyList = propertyService.get().findAll();
		propertyTypeList = propertyService.get().findAllPropertyType();
		propertyGroupList = propertyService.get().findAllPropertyGroup();
		priceTypeList = propertyService.get().findAllPriceType();

	}

	public void initializeDetailProperty() {
		FacesContext context = FacesContext.getCurrentInstance();
		String propertyId = context.getExternalContext().getRequestParameterMap().get("propertyId");
		if (propertyId == null || "".equals(propertyId)) {
			jsfMessageUtil.addInfoMessage("property bulunamadı.");
		} else {
			property = propertyService.get().findPropertyById(Integer.parseInt(propertyId));
			propertyGroupId = property.getPropertyGroup().getId();

			propertyTypeId = property.getPropertyType().getId();
			priceTypeId = property.getPriceType().getId();
		}
	}

	public void initializeCreateProperty() {
		property = new TProperty();
		property.setPropertyType(new TPropertyType());
		property.setPropertyGroup(new TPropertyGroup());
		property.setPriceType(new TPriceType());
		propertyTypeId = null;
		propertyGroupId = null;
		priceTypeId = null;
		propertyTypeList = propertyService.get().findAllPropertyType();
		propertyGroupList = propertyService.get().findAllPropertyGroup();
		priceTypeList = propertyService.get().findAllPriceType();
	}

	public String saveProperty() {
		try {

			if (!priceTypeId.equals(TPriceType.PRICE_TYPE_CALCULATE.getId()))
				property.setPriceCalculateMath(null);

			if (propertyTypeId.equals(TPropertyType.PROPERTY_TYPE_LIST.getId()))
				property.setPrice(0);

			property.setPropertyGroup(new TPropertyGroup(propertyGroupId));
			property.setPropertyType(new TPropertyType(propertyTypeId));
			property.setPriceType(new TPriceType(priceTypeId));

			if (propertyTypeId.equals(TPropertyType.PROPERTY_TYPE_LIST.getId())
				&& property.getPropertyLovDef() == null) {
				
				TPropertyLovDef propertyLovDef = new TPropertyLovDef();
				propertyLovDef.setName(property.getName());
				propertyLovDef.setCode(property.getCode());

				propertyService.get().savePropertyLovDef(propertyLovDef);
				property.setPropertyLovDef(propertyLovDef);
			}
			
			propertyService.get().saveProperty(property);
			jsfMessageUtil.addInfoMessage("Özellik Kaydedildi. :)");
			return "propertySaved";
		} catch (Exception exception) {
			jsfMessageUtil.handleException("Özellik Kaydedilemedi.!!!", exception);
			return null;
		}
	}

	public void deleteProperty(TProperty property) {
		propertyService.get().deleteProperty(property);
		if (property.getPropertyLovDef() != null) {
			propertyService.get().deletePropertyLovDef(property.getPropertyLovDef().getId());
		}
		propertyList = propertyService.get().findAll();

	}

}
