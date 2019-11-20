package tr.com.mindworks.controllers.proposal;

import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Provider;

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
import tr.com.mindworks.model.TOrder;
import tr.com.mindworks.model.TOrderStatus;
import tr.com.mindworks.model.TProposal;
import tr.com.mindworks.services.OrderService;
import tr.com.mindworks.services.ProposalService;

@Component("proposalCreateController")
@SpringFlowScoped
@SessionScope
public class ProposalCreateController extends BaseController {
    @Autowired
    private Provider<ProposalService> proposalService;
    @Autowired
    private Provider<OrderService> orderService;
    
	@Getter
	@Setter
	private TProposal proposal;

	
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
	
	
	public void initialize() {
		proposal = new TProposal();
		TOrder order = new TOrder();
		order.setCommitmentDate(new Date());
		proposal.setOrder(order);
		
		customerList = orderService.get().findAllCustomers();
	}
	public void onCustomerChange(final AjaxBehaviorEvent event) {
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
	
	public String saveProposal()
    {
        try
        {
        	proposal.getOrder().setSalesResponsible(visit.getUser());
        	proposal.getOrder().setOrderStatus(TOrderStatus.ORDER_STATUS_DRAFT);
        	proposal.getOrder().setCustomer(customer);
        	proposal.getOrder().setDeliveryAddress(new TCustomerAddress(deliveryAddressId));
			if (customer.getIsCorporate()) {
				proposal.getOrder().setCustomerContact(new TCustomerContact(customerContactId));
				proposal.getOrder().setCustomerProject(new TCustomerProject(customerProjectId));
			} else {
				proposal.getOrder().setCustomerContact(null);
				proposal.getOrder().setCustomerProject(null);
			}
			proposal.setRevisionNo(0);
			proposal.setRevisionDate(new Date());
			proposal.setCreateDate(new Date());
			proposal.setLastSendDate(new Date());
			proposal.setLastUpdateDate(new Date());

			proposal.getOrder().setCurrencyCode("TRY");
			proposal.getOrder().setCurrencyValue(Float.toString(1F));
			
			
        	orderService.get().saveOrder(proposal.getOrder());
        	proposalService.get().saveProposal(proposal);
        	
        	
        	
			proposal.setProposalNo(orderService.get().getNextProposalNo());
			proposalService.get().saveProposal(proposal);
			
			orderService.get().insertProposalHistoryRecord(proposal.getId(),"YENi TEKLiF OLUŞTURMA" , "Yeni Teklif Oluşturuldu.", "-", "-");
        	
            jsfMessageUtil.addInfoMessage("Teklif Kaydedildi.");
            FacesContext.getCurrentInstance()
			            .getExternalContext()
			            .getRequestMap()
			            .put("proposalId", proposal.getId().toString());
    		  return "proposalSaved";
        }
        catch (Exception exception)
        {
            jsfMessageUtil.handleException("", exception);
            return null;
        }
    }
	
}
