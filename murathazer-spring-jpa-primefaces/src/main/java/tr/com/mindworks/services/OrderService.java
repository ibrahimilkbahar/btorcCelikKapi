package tr.com.mindworks.services;

import java.util.List;

import tr.com.mindworks.model.TCustomer;
import tr.com.mindworks.model.TCustomerAddress;
import tr.com.mindworks.model.TCustomerContact;
import tr.com.mindworks.model.TCustomerProject;
import tr.com.mindworks.model.TDimension;
import tr.com.mindworks.model.TOffering;
import tr.com.mindworks.model.TOrder;
import tr.com.mindworks.model.TOrderOffering;
import tr.com.mindworks.model.TOrderOfferingPropval;
import tr.com.mindworks.model.TOrderStatus;
import tr.com.mindworks.model.TProductInstance;
import tr.com.mindworks.model.TPropertyLov;
import tr.com.mindworks.model.TPropertyValue;
import tr.com.mindworks.model.TProposal;
import tr.com.mindworks.model.TProposalHistory;

public interface OrderService
{

	List<TOrder> findAll();
	TOrder findOrderById(int parseInt);
	void saveOrder(TOrder order);
	List<TOrderStatus> findAllOrderStatus();
	
	
	List<TCustomer> findAllCustomers();
	List<TCustomerAddress> findAddressesOfCustomer(Integer customerId);
	List<TCustomerContact> findContactsOfCustomer(Integer customerId);
	List<TCustomerProject> findProjectsOfCustomer(Integer customerId);
	TCustomer findCustomer(Integer customerId);
	

	
	List<TOffering> findOfferings(Integer productTypeId, Integer productSurfaceTypeId, Integer productWingTypeId);
	
	List<TOrderOffering> findOrderOfferingsByOrderId(Integer orderId);
	List<TOrderOffering> findOrderOfferingsWithPrdInsByOrderId(Integer orderId);
	void deleteOrderOffering(Integer orderOfferingId);
	void addOrderOffering(TOrderOffering newOrderOffering);
	void updateOrderOffering(TOrderOffering newOrderOffering);
	
	
	TProposal findProposalByOrderId(Integer id);
	List<TProposalHistory> findAllProposalHistory(Integer id);
	String getNextProposalNo();
	
	
	void updatePropertyValue(TPropertyValue propertyValue);
	List<TOrderOfferingPropval> findOrderOfferingPropValList(Integer orderOfferingId);
	TOrderOffering findOrderOfferingById(int orderOfferingId);
	void updateOrderOfferingProperties(List<TOrderOfferingPropval> orderOfferingPropValList);
	
	
	
	
	TDimension findCloserDimension(Integer width, Integer height,Integer productTypeId,Integer productWingTypeId);
	TOffering findOfferingById(Integer offeringId);
	
	void insertProposalHistoryRecord(Integer proposalId, String operation, String operationInfo, String oldValue, String newValue);
	
	TPropertyLov findPropertyLovById(Integer id);
	
	void updatePropertyLovOfOrderOffering(TOrderOffering orderOffering, String propValCode, Integer value);
	
	void createProductInstancesFromOrder(TOrder order);
	String generateProductNo(TOrderOffering oo);
	
	List<TOrder> findAllApprovedAndCompletedOrders();
	void updateProductInstance(TProductInstance pi);
	
	double findCaseDepthPrice(TOrderOffering orderOffering);


	 
	 
	
	

	


}
