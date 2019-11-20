package tr.com.mindworks.controllers.offering;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;
import tr.com.mindworks.annotations.SpringFlowScoped;
import tr.com.mindworks.controllers.BaseController;
import tr.com.mindworks.model.TProperty;
import tr.com.mindworks.model.TPropertyLov;
import tr.com.mindworks.model.TPropertyLovDef;
import tr.com.mindworks.services.PropertyService;

@Component("propertyLovDefController")
@SpringFlowScoped
@SessionScope
public class PropertyLovDefController extends BaseController {

	@Autowired
	private Provider<PropertyService> propertyService;
	@Getter
	@Setter
	private TPropertyLovDef propertyLovDef;


	
	@Getter
	@Setter
	private TPropertyLov newPropertyLovItem;
	@Getter
	@Setter
	private List<TPropertyLov> propertyLovItemList;
	
 
	


	
	public void initializePropertyLovDef() {
		FacesContext context = FacesContext.getCurrentInstance();
		String propertyId = context.getExternalContext().getRequestParameterMap().get("propertyId");
		if (propertyId == null || "".equals(propertyId)) {
			jsfMessageUtil.addInfoMessage("property bulunamadı.");
		} else {
			TProperty property = propertyService.get().findPropertyById(Integer.parseInt(propertyId));
			

			propertyLovDef = propertyService.get().findPropertyLovDefById(property.getPropertyLovDef().getId());
			if (propertyLovDef.getPropertyLovList() != null) {
				propertyLovItemList = propertyService.get().findAllPropertyLovByDefId(propertyLovDef.getId());
			}
				
			newPropertyLovItem = new TPropertyLov();
		}
	}
	
	

	public String savePropertyLovDef() {
		try {
			propertyService.get().savePropertyLovDef(propertyLovDef);
			newPropertyLovItem = new TPropertyLov();
			jsfMessageUtil.addInfoMessage("Özellik Seçim Listesi Kaydedildi. :)");
			return "propertyLovDefSaved";
		} catch (Exception exception) {
			jsfMessageUtil.handleException("Özellik Seçim Listesi Kaydedilemedi.!!!", exception);
			return null;
		}
	}

		
	
	
	public void savePropertyLovItem(ActionEvent actionEvent) {
		newPropertyLovItem.setPropertyLovDef(propertyLovDef);
		propertyService.get().savePropertyLov(newPropertyLovItem);
		propertyLovItemList = propertyService.get().findAllPropertyLovByDefId(propertyLovDef.getId());
	}
	
	public void resetPropertyLovItem() {
		newPropertyLovItem = new TPropertyLov();
	}
	
	public void deletePropertyLovItem(TPropertyLov propertyLov) {
		try {
			propertyService.get().deletePropertyLov(propertyLov.getId());
			propertyLovItemList = propertyService.get().findAllPropertyLovByDefId(propertyLovDef.getId());
		}catch (Exception e) {
			jsfMessageUtil.handleException("Özellik Silinemedi. Muhtemelen Kullanılıyor. Kullanılan Özellik Silinemez.!!!", e);
		}
	}
	
	public void loadPropertyLovItem(TPropertyLov propertyLov) {
		newPropertyLovItem = propertyLov;
	}
	
	
	

}
