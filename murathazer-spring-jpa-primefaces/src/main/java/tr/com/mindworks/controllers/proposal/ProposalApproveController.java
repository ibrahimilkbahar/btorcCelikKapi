package tr.com.mindworks.controllers.proposal;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
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
import tr.com.mindworks.model.TCustomer;
import tr.com.mindworks.model.TOrderOffering;
import tr.com.mindworks.model.TOrderOfferingPropval;
import tr.com.mindworks.model.TOrderStatus;
import tr.com.mindworks.model.TProposal;
import tr.com.mindworks.model.TProposalHistory;
import tr.com.mindworks.services.OrderService;
import tr.com.mindworks.services.ProposalService;
import tr.com.mindworks.util.MailSender;
import tr.com.mindworks.util.PdfExporter;

@Component("proposalApproveController")
@SpringFlowScoped
@SessionScope
public class ProposalApproveController extends BaseController {
	@Autowired
	private Provider<ProposalService> proposalService;
	@Autowired
	private Provider<OrderService> orderService;
	
	@Getter
	@Setter
	private List<TProposal> proposalApproveList;

	
	@Getter
	@Setter
	private TProposal proposal;
	@Getter
	@Setter
	private List<TOrderOffering> orderOfferingList;
	
	@Getter
	@Setter
	private TOrderOffering orderOffering;
	@Getter
	@Setter
	private List<TOrderOfferingPropval> orderOfferingPropValList;
	
	@Getter
	@Setter
	private List<TProposalHistory> proposalHistoryList;
	@Getter
	@Setter
	private TCustomer customer;
	@Getter
	@Setter
	private Integer proposalId;
	
	@Getter
	@Setter
	private String rejectReason;
	
	
	@PostConstruct
	public void initialize() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		String orderOfferingId = context.getExternalContext().getRequestParameterMap().get("orderOfferingId");
		context.getExternalContext().getSessionMap().put("service", orderService);
		if (orderOfferingId != null && !"".equals(orderOfferingId)) {
			
			orderOffering = orderService.get().findOrderOfferingById(Integer.parseInt(orderOfferingId));
			orderOfferingPropValList = orderService.get().findOrderOfferingPropValList(orderOffering.getId());
			proposalId = orderService.get().findProposalByOrderId(orderOffering.getOrder().getId()).getId();
			
		} else {
			
			Object proposalId = context.getExternalContext().getRequestMap().get("proposalId");
			if (proposalId == null) {
				proposalId = context.getExternalContext().getRequestParameterMap().get("proposalId");
			}
			if (proposalId != null && !"".equals(proposalId)) {
				
				proposal = proposalService.get().findProposalById(Integer.parseInt(proposalId.toString()));
				customer = proposal.getOrder().getCustomer();
				orderOfferingList = orderService.get().findOrderOfferingsByOrderId(proposal.getOrder().getId());
				proposalHistoryList = orderService.get().findAllProposalHistory(proposal.getId());
				
			} else {
				
				proposalApproveList = proposalService.get().findAllApproveList();
				
			}
			
		}
	}
	
	
	public void saveProposalStatusToReject(ActionEvent actionEvent) {
		
    	proposal.getOrder().setOrderStatus(TOrderStatus.ORDER_STATUS_DRAFT);
		
		orderService.get().saveOrder(proposal.getOrder());
		orderService.get().insertProposalHistoryRecord(proposal.getId(),"TEKLİF STATU GUNCELLEME." , "Teklif Red Edildi.", "-", proposal.getOrder().getRejectReason());
		
		proposalHistoryList = orderService.get().findAllProposalHistory(proposal.getId());
		
		String fileInfo = PdfExporter.getProposalReportFileInfo(proposal);
		MailSender.sendMail(proposal.getOrder().getSalesResponsible().getEmail(), fileInfo, "Teklif Red Edildi. : " + proposal.getProposalNo(), 
				"Teklifiniz Red edildi. Red Nedeni :" + proposal.getOrder().getRejectReason());
		
		
		jsfMessageUtil.addInfoMessage("Teklif Red Edildi.");
	}
	
	public void saveProposalStatusToApprove(ActionEvent actionEvent) {
		
    	proposal.getOrder().setOrderStatus(TOrderStatus.ORDER_STATUS_APPROVED);
    	proposal.getOrder().setRejectReason("");
    	proposal.getOrder().setApprover(visit.getUser());
    	proposal.getOrder().setOrderNo(proposal.getProposalNo());
    	proposal.getOrder().setApproveDate(new Date());
		orderService.get().saveOrder(proposal.getOrder());
		orderService.get().insertProposalHistoryRecord(proposal.getId(),"TEKLİF STATU GUNCELLEME." , "Teklif Onaylandı.", "-", "-");
		
		orderService.get().createProductInstancesFromOrder(proposal.getOrder());
		String fileInfo = PdfExporter.getProposalReportFileInfo(proposal);
		MailSender.sendMail(proposal.getOrder().getSalesResponsible().getEmail(), fileInfo, 
				"Teklif Onaylandı : " + proposal.getProposalNo(), "Teklifiniz Onaylanmıştır. Ürün Kartı Basımına başlayabilirsiniz.");
		
		proposalHistoryList = orderService.get().findAllProposalHistory(proposal.getId());
		jsfMessageUtil.addInfoMessage("Teklif Onaylandı.");
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
}
