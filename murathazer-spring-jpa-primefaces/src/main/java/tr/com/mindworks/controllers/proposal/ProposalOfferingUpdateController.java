package tr.com.mindworks.controllers.proposal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;
import tr.com.mindworks.annotations.SpringFlowScoped;
import tr.com.mindworks.controllers.BaseController;
import tr.com.mindworks.model.TOrderOffering;
import tr.com.mindworks.model.TOrderOfferingPropval;
import tr.com.mindworks.model.TProductWingType;
import tr.com.mindworks.model.TPropertyGroup;
import tr.com.mindworks.model.TPropertyLov;
import tr.com.mindworks.model.TProposal;
import tr.com.mindworks.services.OrderService;
import tr.com.mindworks.services.ProposalService;
import tr.com.mindworks.util.Constants;

@Component("proposalOfferingUpdateController")
@SpringFlowScoped
@SessionScope
public class ProposalOfferingUpdateController extends BaseController {
    @Autowired
    private Provider<OrderService> orderService;
	@Autowired
	private Provider<ProposalService> proposalService;
	
	@Getter
	@Setter
	private TOrderOffering newOrderOffering;

	@Getter
	@Setter
	private List<TOrderOfferingPropval> orderOfferingPropValList, 
										orderOfferingPropValListMetal,
										orderOfferingPropValListWooden, 
										orderOfferingPropValListAccessory, 
										orderOfferingPropValListOther;

	@Getter
	@Setter
	private Integer proposalId;
	
	public void initialize() {
		FacesContext context = FacesContext.getCurrentInstance();
		String orderOfferingId = context.getExternalContext().getRequestParameterMap().get("orderOfferingId");
		
		if(!context.getExternalContext().getSessionMap().containsKey("orderService"))
			context.getExternalContext().getSessionMap().put("orderService", orderService);
		
		if (orderOfferingId == null || "".equals(orderOfferingId)) {
			jsfMessageUtil.addInfoMessage("Teklif Kalemi bulunamadı.");
		} else {
			initPropertyList(Integer.parseInt(orderOfferingId));
		}
	}

	public void initPropertyList(Integer orderOfferingId) {
		newOrderOffering = orderService.get().findOrderOfferingById(orderOfferingId);
		proposalId = orderService.get().findProposalByOrderId(newOrderOffering.getOrder().getId()).getId();
		orderOfferingPropValList = orderService.get().findOrderOfferingPropValList(newOrderOffering.getId());
		
		orderOfferingPropValListMetal = new ArrayList<TOrderOfferingPropval>();
		orderOfferingPropValListWooden = new ArrayList<TOrderOfferingPropval>();
		orderOfferingPropValListAccessory = new ArrayList<TOrderOfferingPropval>();
		orderOfferingPropValListOther = new ArrayList<TOrderOfferingPropval>();
		 
		
		for (TOrderOfferingPropval orderOffProp : orderOfferingPropValList) {	
			if (orderOffProp.getPropertyValue().getProperty().getPropertyGroup().getId()
					.equals(TPropertyGroup.PROPERTY_GROUP_METAL.getId())) {
				orderOfferingPropValListMetal.add(orderOffProp);
			} else if (orderOffProp.getPropertyValue().getProperty().getPropertyGroup().getId()
					.equals(TPropertyGroup.PROPERTY_GROUP_WOODEN.getId())) {
				orderOfferingPropValListWooden.add(orderOffProp);
			} else if (orderOffProp.getPropertyValue().getProperty().getPropertyGroup().getId()
					.equals(TPropertyGroup.PROPERTY_GROUP_ACCESSORY.getId())) {
				orderOfferingPropValListAccessory.add(orderOffProp);
			} else if (orderOffProp.getPropertyValue().getProperty().getPropertyGroup().getId()
					.equals(TPropertyGroup.PROPERTY_GROUP_OTHERS.getId())) {
				orderOfferingPropValListOther.add(orderOffProp);
			}
		}
	}
	
	
	
	
	public void saveProposalOffering()
    {
		try {
			 
			
			
			validateDiscount();
			validateQuantity();
			validateRelatedValueForWindowDoors();
			

		
			TProposal prop = orderService.get().findProposalByOrderId(newOrderOffering.getOrder().getId());
			prop.setLastUpdateDate(new Date());
			proposalService.get().saveProposal(prop);
		 	
			orderService.get().updateOrderOfferingProperties(orderOfferingPropValList);
			orderOfferingPropValList = orderService.get().findOrderOfferingPropValList(newOrderOffering.getId());
			newOrderOffering.setOrderOfferingPropValList(orderOfferingPropValList);
	    	newOrderOffering.calculateInstancePrice();
	    	orderService.get().updateOrderOffering(newOrderOffering);
	    	
			
	        jsfMessageUtil.addInfoMessage("Kapı Özellikleri Güncellendi.");
	        initPropertyList(newOrderOffering.getId());
		        

		}catch (Exception e) {
			jsfMessageUtil.handleException(" Hatalı bilgi girişi..!!!", e);
		}
    }

	private void validateRelatedValueForWindowDoors() throws Exception {
		for (TOrderOfferingPropval oopv : orderOfferingPropValList) {
			if((oopv.getPropertyValue().getProperty().getCode().equals(Constants.PROP_TYPE1WINDOWSIZEONEWING)
					|| oopv.getPropertyValue().getProperty().getCode().equals(Constants.PROP_TYPE2WINDOWSIZEONEWING) 
					|| oopv.getPropertyValue().getProperty().getCode().equals(Constants.PROP_TYPE3WINDOWSIZEONEWING))
					
					&& Integer.parseInt(oopv.getPropertyValue().getPropertyLov().getRelatedValue())!=oopv.getOrderOffering().getProductionWidth()) 
			{
				throw new Exception(oopv.getPropertyValue().getProperty().getName() +" özelliğinde kasaya uygun cam seçiniz.");
			}
		}
	}

	private void validateQuantity() throws Exception {
		if(newOrderOffering.getQuantity()==0) {
			throw new Exception("Adet 0 dan büyük olmalı.");
		}
	}

	private void validateDiscount() throws Exception {
		String validation = "SUCCESS";
		if (newOrderOffering.getOffering().getProductWingType().getId().equals(TProductWingType.WING_TYPE_ONEWING.getId())) {
			 if ((newOrderOffering.getQuantity() > 0 && newOrderOffering.getQuantity() <= 9 && newOrderOffering.getInstanceDiscount() > 0) ||
			     (newOrderOffering.getQuantity() > 9 && newOrderOffering.getQuantity() <= 50 && newOrderOffering.getInstanceDiscount() > 5) ||
				 (newOrderOffering.getQuantity() > 51 && newOrderOffering.getQuantity() <= 100 && newOrderOffering.getInstanceDiscount() > 7) ||
				 (newOrderOffering.getQuantity() > 101 && newOrderOffering.getQuantity() <= 250 && newOrderOffering.getInstanceDiscount() > 8) ||
				 (newOrderOffering.getQuantity() > 250 && newOrderOffering.getInstanceDiscount() > 10)) {
				validation="Adet ile indirim yüzdesi tutarsız. ";
			 }
		}
		else 
		{
			if ((newOrderOffering.getQuantity() > 0 && newOrderOffering.getQuantity() <= 9 && newOrderOffering.getInstanceDiscount() > 0) ||
				(newOrderOffering.getQuantity() > 9 && newOrderOffering.getQuantity() <= 50 && newOrderOffering.getInstanceDiscount() > 8) ||
				(newOrderOffering.getQuantity() > 51 && newOrderOffering.getQuantity() <= 100 && newOrderOffering.getInstanceDiscount() > 10) ||
				(newOrderOffering.getQuantity() > 101 && newOrderOffering.getQuantity() <= 250 && newOrderOffering.getInstanceDiscount() > 12) ||
				(newOrderOffering.getQuantity() > 250 && newOrderOffering.getInstanceDiscount() <= 15)) {
				validation="Adet ile indirim yüzdesi tutarsız.";
			 }
		}
		if(!"SUCCESS".equals(validation))
			throw new Exception(validation);
	}
	
	
}
