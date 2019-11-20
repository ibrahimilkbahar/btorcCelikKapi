package tr.com.mindworks.controllers.proposal;

import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Provider;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;
import tr.com.mindworks.annotations.SpringFlowScoped;
import tr.com.mindworks.controllers.BaseController;
import tr.com.mindworks.model.TCustomer;
import tr.com.mindworks.model.TCustomerAddress;
import tr.com.mindworks.model.TCustomerContact;
import tr.com.mindworks.model.TCustomerProject;
import tr.com.mindworks.model.TDimension;
import tr.com.mindworks.model.TOffering;
import tr.com.mindworks.model.TOrderOffering;
import tr.com.mindworks.model.TOrderOfferingPropval;
import tr.com.mindworks.model.TOrderStatus;
import tr.com.mindworks.model.TProductCaseType;
import tr.com.mindworks.model.TProductSurfaceType;
import tr.com.mindworks.model.TProductType;
import tr.com.mindworks.model.TProductWingType;
import tr.com.mindworks.model.TPropertyType;
import tr.com.mindworks.model.TProposal;
import tr.com.mindworks.model.TProposalHistory;
import tr.com.mindworks.services.OrderService;
import tr.com.mindworks.services.ProductService;
import tr.com.mindworks.services.ProposalService;
import tr.com.mindworks.util.Constants;
import tr.com.mindworks.util.Currency;
import tr.com.mindworks.util.MailSender;
import tr.com.mindworks.util.PdfExporter;
import tr.com.mindworks.util.TCMBReader;

@Component("proposalUpdateController")
@SpringFlowScoped
@SessionScope
public class ProposalUpdateController extends BaseController {
	@Autowired
	private Provider<ProposalService> proposalService;
	@Autowired
	private Provider<OrderService> orderService;
	@Autowired
	private Provider<ProductService> productService;
	
 
	
	@Getter
	@Setter
	private TProposal proposal;
	@Getter
	@Setter
	private TOffering offering;
	
	@Getter
	@Setter
	private TOrderOffering newOrderOffering;
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
	private List<TProductCaseType> productCaseTypeList;
	@Getter
	@Setter
	private Integer productTypeId,productSurfaceTypeId,productWingTypeId,productCaseTypeId;
	
	@Getter
	@Setter
	private String productDimensionInfo = "En - Boy - Duvar Ölçülerini Giriniz. (cm)";
	@Getter
	@Setter
	private TDimension productDimension;
	@Getter
	@Setter
	private List<TOrderOffering> orderOfferingList;
	@Getter
	@Setter
	private List<TProposalHistory> proposalHistoryList;
	
	
	
	@Getter
	@Setter
	private TCustomer customer;
	@Getter
	@Setter
	private List<TCustomer> customerList;
	@Getter
	@Setter
	private List<TCustomerAddress> customerAddressList;
	@Getter
	@Setter
	private List<TCustomerContact> customerContactList;
	@Getter
	@Setter
	private List<TCustomerProject> customerProjectList;
	@Getter
	@Setter
	private Integer customerId, deliveryAddressId, customerContactId, customerProjectId;
	
	@Getter
	@Setter
	private List<Currency> currencyList;
	@Getter
	@Setter
	private String currencyCode;
	
	
	public void initialize() {
		FacesContext context = FacesContext.getCurrentInstance();
		Object proposalId = context.getExternalContext().getRequestMap().get("proposalId");
		if (proposalId == null)
			proposalId = context.getExternalContext().getRequestParameterMap().get("proposalId");
		if (proposalId == null || "".equals(proposalId)) {
			jsfMessageUtil.addInfoMessage("Teklif bulunamadı.");
		} else {
			if(!context.getExternalContext().getSessionMap().containsKey("orderService"))
				context.getExternalContext().getSessionMap().put("orderService", orderService);
			
			proposal = proposalService.get().findProposalById(Integer.parseInt(proposalId.toString()));
			loadDictionaryTables();
			initNewOrderOffering();
			initCustomerInformations();
			initCurrencyInformations();
			
			
			orderOfferingList = orderService.get().findOrderOfferingsByOrderId(proposal.getOrder().getId());
			loadHistoryRecords();
		}
	}

/*#####################################################################
 *#############TEKLİF BILGILERI######################################## 
 *#####################################################################*/
	private void initCustomerInformations() {
		customerList = orderService.get().findAllCustomers();
		customerId = proposal.getOrder().getCustomer().getId();
		loadCustomerInformation();
		
		deliveryAddressId = proposal.getOrder().getDeliveryAddress().getId();
		if (customer.getIsCorporate()) {
			customerContactId = proposal.getOrder().getCustomerContact().getId();
			customerProjectId = proposal.getOrder().getCustomerProject().getId();
		}
		
	}
	public void onCustomerChange(final AjaxBehaviorEvent event) {
		loadCustomerInformation();
	}
	
	private void loadCustomerInformation() {
		
		
		
		customerAddressList = orderService.get().findAddressesOfCustomer(customerId);
		customer = orderService.get().findCustomer(customerId);
		
		if (customer.getIsCorporate()) {
			customerContactList = orderService.get().findContactsOfCustomer(customerId);
			customerProjectList = orderService.get().findProjectsOfCustomer(customerId);
		} else {
			customerContactList = null;
			customerProjectList = null;
		}
	}
	private void initCurrencyInformations() {
		TCMBReader kur = TCMBReader.getInstance();
		currencyList = kur.getCurrencies();
		Currency tl = new Currency();
		tl.setCurrencyCode("TRY");
		tl.setForexBuying(1F);
		currencyList.add(0,tl);
	}
	
	

	public void saveProposalStatusToSent(ActionEvent actionEvent) {
		try{
			validateMondatoryPropertiesFilled();
			validateOrderOfferingCount();
			validateSendDate();


			
		    	proposal.getOrder().setOrderStatus(TOrderStatus.ORDER_STATUS_SUBMITTED);
		    	proposal.getOrder().setRejectReason("");
				orderService.get().saveOrder(proposal.getOrder());
				orderService.get().insertProposalHistoryRecord(proposal.getId(),"TEKLİF STATU GUNCELLEME." , "Teklif Onaya Gönderildi.", "-", "-");
				
				loadHistoryRecords();
				
//				String fileInfo = PdfExporter.getProposalReportFileInfo(proposal);
//				MailSender.sendMail(Constants.APPROVERMAIL, fileInfo, "Teklif Onaya Gönderildi. : " + proposal.getProposalNo(), 
//						"Bir Adet Teklif Onaya gönderildi. Lütfen teklifi inceleyiniz.");
//				
				jsfMessageUtil.addInfoMessage("Teklif Onaya Gönderildi.");
			 
		}catch (Exception e) {
			jsfMessageUtil.handleException("Bu teklif Onaya gönderilemez..!!!", e);
		}
		
	}
	private void validateOrderOfferingCount() throws Exception {
		List<TOrderOffering> ooList = orderService.get().findOrderOfferingsByOrderId(proposal.getOrder().getId());
		if(ooList.size()==0)
			throw new Exception("Teklifte En Az 1 adet teklif Kalemi olmalıdır.");
	}

	private void validateSendDate() throws Exception {
		if(proposal.getLastUpdateDate().compareTo(proposal.getLastSendDate())>0)
			throw new Exception("Son güncelleme tarihinden sonra müşteriye iletilmemiş bir teklif onaylanamaz. Önce güncel halini müşteriyle paylaşınız.");
	}

	

	private void validateMondatoryPropertiesFilled() throws Exception {
		List<TOrderOffering> ooList = orderService.get().findOrderOfferingsByOrderId(proposal.getOrder().getId());
		
		boolean result = true;
		StringBuilder propertyName = new StringBuilder();
		
		for (TOrderOffering oo : ooList) {
			for (TOrderOfferingPropval oopv : oo.getOrderOfferingPropValList()) {
				if (oopv.getIsMondatory()) {
					if (oopv.getPropertyValue().getProperty().getPropertyType().equals(TPropertyType.PROPERTY_TYPE_LIST)) {
						
						if (oopv.getPropertyValue().getPropertyLov() == null
						 || oopv.getPropertyValue().getPropertyLov().getId() == 0) {
							result = false;
							propertyName.append(oopv.getPropertyValue().getProperty().getName()+" , ");
						}
					} else {
						if ((oopv.getPropertyValue().getValueText() == null && oopv.getPropertyValue().getValueText().isEmpty())
						 && (oopv.getPropertyValue().getValueNumber() == null)) {
							result = false;
							propertyName.append(oopv.getPropertyValue().getProperty().getName()+" , ");
						}
					}
				}
			}
		}
		if(!result)
			throw new Exception(propertyName + " özellik(leri) zorunlu olduğu için boş geçilemez.Teklif Kalemlerindeki Zorunlu Alanları inceleyiniz.");
	}
	public void saveProposalInformation(ActionEvent actionEvent) {
		
    	proposal.getOrder().setCustomer(customer);
    	proposal.getOrder().setDeliveryAddress(new TCustomerAddress(deliveryAddressId));
		if (customer.getIsCorporate()) {
			proposal.getOrder().setCustomerContact(new TCustomerContact(customerContactId));
			proposal.getOrder().setCustomerProject(new TCustomerProject(customerProjectId));
		} else {
			proposal.getOrder().setCustomerContact(null);
			proposal.getOrder().setCustomerProject(null);
		}
		
		orderService.get().saveOrder(proposal.getOrder());
		orderService.get().insertProposalHistoryRecord(proposal.getId(),"TEKLİF GENEL BİLGİLERİ GÜNCELLEME" , "Teklif Genel Bilgileri Güncellendi.", "-", "-");
		
		setLastUpdateDateOfProposal();
		loadHistoryRecords();
		jsfMessageUtil.addInfoMessage("Teklif Bilgileri Güncellendi..");
	}
	
	
	/*#####################################################################
	 *#############KAPI EKLEME BILGILERI################################### 
	 *#####################################################################*/
	public void addOrderOffering(ActionEvent actionEvent) {
		if (productDimension != null) {
			newOrderOffering.setId(null);

			newOrderOffering.setProductionWidth(productDimension.getProductionWidth());
			newOrderOffering.setProductionHeight(productDimension.getProductionHeight());
			newOrderOffering.setProductCaseType(new TProductCaseType(productCaseTypeId));
			
			
			if (newOrderOffering.getProductCaseType().getId().equals(TProductCaseType.CASE_TYPE_HALFCASE.getId())) {
				newOrderOffering.setDimensionPrice(productDimension.getPrice());
			} else if (newOrderOffering.getProductCaseType().getId().equals(TProductCaseType.CASE_TYPE_FULLCASE.getId())) {
				double caseDepthPrice = orderService.get().findCaseDepthPrice(newOrderOffering);
				newOrderOffering.setDimensionPrice(productDimension.getPrice() + caseDepthPrice);
			}
				
			
			
			
			
			
			orderService.get().addOrderOffering(newOrderOffering);
			newOrderOffering.setOrderOfferingPropValList(orderService.get().findOrderOfferingPropValList(newOrderOffering.getId()));
			 
 
			newOrderOffering.calculateInstancePrice();
			orderService.get().updateOrderOffering(newOrderOffering);
			
			setLastUpdateDateOfProposal();
			
			insertHistoryLog(newOrderOffering, "TEKLIFE KAPI EKLEME");

			orderOfferingList = orderService.get().findOrderOfferingsByOrderId(proposal.getOrder().getId());
			loadHistoryRecords();
			initNewOrderOffering();
			jsfMessageUtil.addInfoMessage("Kapı Eklendi. :)");
		} else {
			jsfMessageUtil.addInfoMessage("Kapı Ölçü Seçimini yapınız.");
		}
	}

	public void deleteOrderOffering(TOrderOffering orderOffering) {
		orderService.get().deleteOrderOffering(orderOffering.getId());

		insertHistoryLog(orderOffering, "TEKLIFTEN KAPI ÇIKARMA");
		setLastUpdateDateOfProposal();
		orderOfferingList = orderService.get().findOrderOfferingsByOrderId(proposal.getOrder().getId());
		loadHistoryRecords();
	}
	

	private void loadDictionaryTables() {
		productTypeList = productService.get().findAllProductTypes();
		productSurfaceTypeList = productService.get().findAllProductSurfaceTypes();
		productWingTypeList = productService.get().findAllProductWingTypes();
		productCaseTypeList = productService.get().findAllProductCaseTypes();
	}
	
	private void initNewOrderOffering() {
		newOrderOffering = new TOrderOffering();
		newOrderOffering.setOrder(proposal.getOrder());
		newOrderOffering.setOffering(new TOffering());
		newOrderOffering.setEnteredWidth(0);
		newOrderOffering.setEnteredHeight(0);
		newOrderOffering.setEnteredDepth(0);

		loadDictionaryTables();

		productTypeId = productSurfaceTypeId = productWingTypeId = productCaseTypeId = null;
		
		offeringList = null;
		productDimensionInfo = "En - Boy - Duvar Ölçülerini Giriniz. (cm)";
	}
	public void updateOfferingList(final AjaxBehaviorEvent event) {
		offeringList = orderService.get().findOfferings(productTypeId,productSurfaceTypeId,productWingTypeId);
	}
	public void onOfferingChange(final AjaxBehaviorEvent event) {
		offering = orderService.get().findOfferingById(newOrderOffering.getOffering().getId());
	} 
	public void validateDimension() {
		if (newOrderOffering.getEnteredWidth() > 0 
				&& newOrderOffering.getEnteredHeight() > 0 
				&& newOrderOffering.getEnteredDepth() > 0) 
		{
			if (productTypeId == null || 
					productWingTypeId == null || 
					productCaseTypeId ==null) 
			{
				productDimensionInfo = "Ürün Tip bilgilerini doldurunuz.";
			} else {
				productDimension = orderService.get().findCloserDimension(newOrderOffering.getEnteredWidth(), newOrderOffering.getEnteredHeight(),  productTypeId,productWingTypeId);
				if (productDimension != null) {
					productDimensionInfo = productDimension.getProductionWidth() + " / " + productDimension.getProductionHeight() + " / " + newOrderOffering.getEnteredDepth();
				} else {
					productDimensionInfo = "Girilen değerlere uygun kapı ölçüsü bulunamadı.";
				}
			}
		} else {
			productDimensionInfo = "Girilen değerlere uygun kapı ölçüsü bulunamadı.";
		}
	}

	/*#####################################################################
	 *#############NAKLİYE MONTAJ########################################## 
	 *#####################################################################*/
	public void saveTransportationAndInstallInfo(ActionEvent actionEvent) {
		if(!proposal.getOrder().getInstallationRequested()) {
			proposal.getOrder().setInstallationAmount(0);
			proposal.getOrder().setInstallationDescription("");
		}
		if(!proposal.getOrder().getTransportRequested()) {
			proposal.getOrder().setTransportAmount(0);
			proposal.getOrder().setTransportDescription("");
		}
		
		orderService.get().saveOrder(proposal.getOrder());
		orderService.get().insertProposalHistoryRecord(proposal.getId(),"NAKLİYE/KURULUM GÜNCELLEME" , "Nakliye/Kurulum Bilgileri Güncellendi.", "-", "-");
		setLastUpdateDateOfProposal();
		loadHistoryRecords();
		jsfMessageUtil.addInfoMessage("Nakliye Ve Kurulum Bilgileri güncellendi.");
	}
	
	
	

	
	 
	/*#####################################################################
	 *#############ÇIKTI İŞLEMLERİ######################################### 
	 *#####################################################################*/
	public StreamedContent getDownloadProposalReport() {
		orderService.get().insertProposalHistoryRecord(proposal.getId(),"ÇIKTI ALMA" , "Teklif Çıktısı Alındı.", "-", "-");
		
		if(proposal.getLastUpdateDate().after(proposal.getLastSendDate())) {
			proposal.setLastSendDate(new Date());
			proposal.setRevisionDate(new Date());
			proposal.setRevisionNo(proposal.getRevisionNo() + 1);
			
			proposalService.get().saveProposal(proposal);
		}
		

		return PdfExporter.downloadProposalReport(proposal);
	}
	
	public void emailProposalReport(ActionEvent actionEvent) {
		String email = proposal.getOrder().getCustomer().getIsCorporate()
						? proposal.getOrder().getCustomerContact().getEmail()
						: proposal.getOrder().getCustomer().getEmail();
		

		String fileInfo = PdfExporter.getProposalReportFileInfo(proposal);

		 

		MailSender.sendMail(email, fileInfo, "Teklif No: " + proposal.getProposalNo(), "Teklif Raporu Ektedir. İyi Çalışmalar.");

		jsfMessageUtil.addInfoMessage("Teklif email gönderildi.");
		orderService.get().insertProposalHistoryRecord(proposal.getId(), "TEKLIF EMAIL GONDERILDI", "Teklif email gönderildi.", email, "-");
	}
	/*#####################################################################
	 *#############ÖDEME BİLGİLERİ######################################### 
	 *#####################################################################*/
	public void savePaymentInfo(ActionEvent actionEvent) {
		for (Currency c : currencyList) {
			if(c.getCurrencyCode().equals(currencyCode)) {
				proposal.getOrder().setCurrencyCode(currencyCode);
				proposal.getOrder().setCurrencyValue(Float.toString(c.getForexBuying()));
				break;
			}
		}
		
		
		orderService.get().saveOrder(proposal.getOrder());
		orderService.get().insertProposalHistoryRecord(proposal.getId(),"ÖDEME BİLGİLERİ GÜNCELLEME" , "Ödeme Bilgileri Güncellendi.", "-", "-");
		setLastUpdateDateOfProposal();
		loadHistoryRecords();
		jsfMessageUtil.addInfoMessage("Ödeme Bilgileri Güncellendi.");
	}
	
	/*#####################################################################
	 *#############OTHER METHODS########################################### 
	 *#####################################################################*/
	public double getTotalCostOfOrderOfferings() {
		double total = 0d;
		for (TOrderOffering oof : orderOfferingList) {
			total = total + oof.getTotalAmount();
		}
		return round(total/Double.parseDouble(proposal.getOrder().getCurrencyValue()),2);
	}
	
	public double getFinalDiscount() {
		double totalCostOrderOffering = getTotalCostOfOrderOfferings();
		double finalResult = round(totalCostOrderOffering,2);
		return round(finalResult*proposal.getOrder().getFinalDiscount()/100,2);
	}
	
	public double getInstallationAmount() {
		return round(proposal.getOrder().getInstallationAmount()/Double.parseDouble(proposal.getOrder().getCurrencyValue()),2);
	}
	
	public double getTransportAmount() {
		return round(proposal.getOrder().getTransportAmount()/Double.parseDouble(proposal.getOrder().getCurrencyValue()),2);
	}
	
	public double getTotalTax() {
		double totalCostOrderOffering = getTotalCostOfOrderOfferings();
		double finalDiscountCost = getFinalDiscount();
		double total = totalCostOrderOffering - finalDiscountCost + getInstallationAmount() + getTransportAmount();
		return round(total*18/100,2);
	}
	
	public double getGeneralSum() {
		double totalCostOrderOffering = getTotalCostOfOrderOfferings();
		double finalDiscountCost = getFinalDiscount();
		double totalTax = getTotalTax();
		double finalResult = round(totalCostOrderOffering - finalDiscountCost + totalTax + getInstallationAmount() + getTransportAmount(),2);
		return round(finalResult,2);
	}

	public double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	
	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	

	private void setLastUpdateDateOfProposal() {
		proposal.setLastUpdateDate(new Date());
		proposalService.get().saveProposal(proposal);
	}
	
	private void insertHistoryLog(TOrderOffering orderOffering, String operation) {
		TOffering offering = orderService.get().findOfferingById(orderOffering.getOffering().getId());
		orderOffering.setOffering(offering);

		orderService.get().insertProposalHistoryRecord(proposal.getId(), operation,
				"Kapı Bilgisi :" + " Model: " + orderOffering.getOffering().getName() + " Boyut: "
						+ orderOffering.getDimensionInfo() + " Adet : " + orderOffering.getQuantity(),
				"-", "-");
	}
	private void loadHistoryRecords() {
		proposalHistoryList = orderService.get().findAllProposalHistory(proposal.getId());
	}
	public void handleCaseTypeChange() {
		newOrderOffering.setEnteredDepth(10);
	}
}
