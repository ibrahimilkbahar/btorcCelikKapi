package tr.com.mindworks.services.impl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tr.com.mindworks.dao.CustomerAddressRepository;
import tr.com.mindworks.dao.CustomerContactRepository;
import tr.com.mindworks.dao.CustomerProjectRepository;
import tr.com.mindworks.dao.CustomerRepository;
import tr.com.mindworks.dao.DimensionRepository;
import tr.com.mindworks.dao.FullCaseDepthPriceRangeRepository;
import tr.com.mindworks.dao.OfferingPropvalRepository;
import tr.com.mindworks.dao.OfferingRepository;
import tr.com.mindworks.dao.OrderOfferingPropertyRepository;
import tr.com.mindworks.dao.OrderOfferingRepository;
import tr.com.mindworks.dao.OrderRepository;
import tr.com.mindworks.dao.ProductInstanceRepository;
import tr.com.mindworks.dao.ProductInstanceSEQRepository;
import tr.com.mindworks.dao.PropertyLovRepository;
import tr.com.mindworks.dao.PropertyValueRepository;
import tr.com.mindworks.dao.ProposalHistoryRepository;
import tr.com.mindworks.dao.ProposalRepository;
import tr.com.mindworks.dao.ProposalSEQRepository;
import tr.com.mindworks.dao.UserRepository;
import tr.com.mindworks.model.TCustomer;
import tr.com.mindworks.model.TCustomerAddress;
import tr.com.mindworks.model.TCustomerContact;
import tr.com.mindworks.model.TCustomerProject;
import tr.com.mindworks.model.TDimension;
import tr.com.mindworks.model.TDoorOpenSide;
import tr.com.mindworks.model.TOffering;
import tr.com.mindworks.model.TOfferingPropval;
import tr.com.mindworks.model.TOrder;
import tr.com.mindworks.model.TOrderOffering;
import tr.com.mindworks.model.TOrderOfferingPropval;
import tr.com.mindworks.model.TOrderStatus;
import tr.com.mindworks.model.TPriceType;
import tr.com.mindworks.model.TProductInstance;
import tr.com.mindworks.model.TProductInstanceSEQ;
import tr.com.mindworks.model.TProductInstanceStatus;
import tr.com.mindworks.model.TProductType;
import tr.com.mindworks.model.TPropertyLov;
import tr.com.mindworks.model.TPropertyType;
import tr.com.mindworks.model.TPropertyValue;
import tr.com.mindworks.model.TProposal;
import tr.com.mindworks.model.TProposalHistory;
import tr.com.mindworks.model.TProposalSEQ;
import tr.com.mindworks.services.OrderService;
import tr.com.mindworks.util.Constants;

@Service("orderService")
public class OrderServiceImpl implements OrderService, Serializable {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OfferingRepository offeringRepository;
	@Autowired
	private OfferingPropvalRepository offeringPropvalRepository;
	@Autowired
	private OrderOfferingRepository orderOfferingRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CustomerAddressRepository customerAddressRepository;
	@Autowired
	private CustomerContactRepository customerContactRepository;
	@Autowired
	private CustomerProjectRepository customerProjectRepository;
	@Autowired
	private OrderOfferingPropertyRepository orderOfferingPropertyRepository;
	@Autowired
	private PropertyValueRepository propertyValueRepository;
	@Autowired
	private PropertyLovRepository propertyLovRepository;
	@Autowired
	private ProposalRepository proposalRepository;
	@Autowired
	private ProposalSEQRepository proposalSEQRepository; 
	@Autowired
	private ProductInstanceSEQRepository productInstanceSEQRepository; 
	@Autowired
	private ProposalHistoryRepository proposalHistoryRepository; 
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DimensionRepository dimensionRepository;
	@Autowired
	private ProductInstanceRepository productInstanceRepository;
	
	@Autowired
	private FullCaseDepthPriceRangeRepository fullCaseDepthPriceRangeRepository;  
	

	@Override
	public List<TOrder> findAll() {
		return orderRepository.findAll();
	}
	@Override
	public TOrder findOrderById(int orderId) {
		return orderRepository.findOrderById(orderId);
	}
	@Override
	public void saveOrder(TOrder order) {
		orderRepository.save(order);
	}

	@Override
	public List<TOrderStatus> findAllOrderStatus() {
		return orderRepository.findAllOrderStatus();
	}
	@Override
	public List<TCustomer> findAllCustomers() {
		return customerRepository.findAll();
	}
	
	
	
	@Override
	public List<TCustomerAddress> findAddressesOfCustomer(Integer customerId) {
		return customerAddressRepository.findAllCustomerAddressByCustomerId(customerId);
	}
	@Override
	public List<TCustomerContact> findContactsOfCustomer(Integer customerId) {
		return customerContactRepository.findAllCustomerContactsByCustomerId(customerId);
	}
	@Override
	public List<TCustomerProject> findProjectsOfCustomer(Integer customerId) {
		return customerProjectRepository.findAllCustomerProjectByCustomerId(customerId);
	}
	
	@Override
	public TCustomer findCustomer(Integer customerId) {
		return customerRepository.findOne(customerId);
	}
	
	
	@Override
	public 	List<TOffering> findOfferings(Integer productTypeId, Integer productSurfaceTypeId, Integer productWingTypeId){
		return offeringRepository.findOfferings(productTypeId,productSurfaceTypeId,productWingTypeId);
	}
	@Override
	public List<TOrderOffering> findOrderOfferingsByOrderId(Integer orderId) {
		return orderOfferingRepository.findOrderOfferingsByOrderId(orderId);
	}
	@Override
	public List<TOrderOffering> findOrderOfferingsWithPrdInsByOrderId(Integer orderId) {
		List<TOrderOffering> result =orderOfferingRepository.findOrderOfferingsByOrderId(orderId);
		for (TOrderOffering oo : result) {
			oo.setProductInstanceList(productInstanceRepository.findAllByOrderOfferingId(oo.getId()));
		}
		return result;
	}
	@Override
	public void deleteOrderOffering(Integer orderOfferingId) {
		List<TOrderOfferingPropval> propertyValueList = orderOfferingPropertyRepository.findByOrderOfferingId(orderOfferingId);
		for (TOrderOfferingPropval oopv : propertyValueList) {
			orderOfferingPropertyRepository.deleteOrderOfferingPropValById(oopv.getId());
			propertyValueRepository.delete(oopv.getPropertyValue().getId());
		}
		orderOfferingRepository.delete(orderOfferingId);		
	}
	@Override
	public void addOrderOffering(TOrderOffering newOrderOffering) {
		orderOfferingRepository.save(newOrderOffering);
		for (int i = 1; i < 5; i++) {//1-4 e kadar özellik gurubu
			List<TOfferingPropval> propertyList = offeringPropvalRepository.findAllOfferingPropvalOfOffering(newOrderOffering.getOffering().getId(), i);
			for (TOfferingPropval op : propertyList) {
				
				TOrderOfferingPropval oopv = new TOrderOfferingPropval();
				oopv.setOrderOffering(newOrderOffering);
				oopv.setIsMondatory(op.getIsMondatory());
				oopv.setIsReadonly(op.getIsReadonly());
				oopv.setIsVisibleOnReport(op.getIsVisibleOnReport());
				oopv.setOrderBy(op.getOrderBy());
				
				TPropertyValue pv = op.getPropertyValue().clonePropertyValue();
				oopv.setPropertyValue(pv);
				
				pv.calculatePrice(newOrderOffering.getProductionWidth(),newOrderOffering.getProductionHeight(),newOrderOffering.getEnteredDepth());
				propertyValueRepository.save(pv);
				orderOfferingPropertyRepository.save(oopv);
			}
		}
	}
	
	
	@Override
	public void updateOrderOffering(TOrderOffering newOrderOffering) {
		TOrderOffering oldOrderOffering = orderOfferingRepository.findOne(newOrderOffering.getId());
		TProposal proposal=proposalRepository.findByOrderId(newOrderOffering.getOrder().getId());
		
		
		if(oldOrderOffering.getQuantity() != newOrderOffering.getQuantity())
		{
			insertProposalHistoryRecord(proposal.getId(), "TEKLIF KALEMİ GUNCELLEME",
					"Kapı Adet Değişti."
					+ " Model: " +newOrderOffering.getOffering().getName()
					+ " Boyut: " +newOrderOffering.getDimensionInfo() ,
					Integer.toString(oldOrderOffering.getQuantity()), 
					Integer.toString(newOrderOffering.getQuantity()));
		}
		if(oldOrderOffering.getInstanceDiscount() != newOrderOffering.getInstanceDiscount())
		{
			insertProposalHistoryRecord(proposal.getId(), "TEKLIF KALEMİ GUNCELLEME",
					"Kapı indirim Değişti."
					+ " Model: " +newOrderOffering.getOffering().getName()
					+ " Boyut: " +newOrderOffering.getDimensionInfo() ,
					Double.toString(oldOrderOffering.getInstanceDiscount()), 
					Double.toString(newOrderOffering.getInstanceDiscount()));
		}
		if(oldOrderOffering.getInstanceIncrease() != newOrderOffering.getInstanceIncrease())
		{
			insertProposalHistoryRecord(proposal.getId(), "TEKLIF KALEMİ GUNCELLEME",
					"Kapı ek ücret Değişti."
					+ " Model: " +newOrderOffering.getOffering().getName()
					+ " Boyut: " +newOrderOffering.getDimensionInfo() ,
					Double.toString(oldOrderOffering.getInstanceIncrease()), 
					Double.toString(newOrderOffering.getInstanceIncrease()));
		}
		orderOfferingRepository.save(newOrderOffering);
	}
	
	@Override
	public TDimension findCloserDimension(Integer width, Integer height, Integer productTypeId,Integer productWingTypeId) {
		List<TDimension> dimensions = dimensionRepository.findAllDimensionsBy_Type_Wing(productTypeId, productWingTypeId);
		for (TDimension tDimension : dimensions) {
			if (tDimension.getWidthMin() <= width && tDimension.getWidthMax() > width
			&& tDimension.getHeightMin() <= height && tDimension.getHeightMax() > height)
			{
				return tDimension;
			}
		}
		return null;
	}
	@Override
	public List<TOrderOfferingPropval> findOrderOfferingPropValList(Integer orderOfferingId) {
		return orderOfferingPropertyRepository.findByOrderOfferingId(orderOfferingId);
	}
	
	@Override
	public TOrderOffering findOrderOfferingById(int orderOfferingId) {
		return orderOfferingRepository.findOne(orderOfferingId);
	}
	
	@Override
	public void updateOrderOfferingProperties(List<TOrderOfferingPropval> orderOfferingPropValList) {
		for (TOrderOfferingPropval oopv : orderOfferingPropValList) {

			TPropertyValue pv = oopv.getPropertyValue();
			if (oopv.getPropertyValue().getPropertyLov() != null
				&& oopv.getPropertyValue().getPropertyLov().getId() > 0 ) 
			{
				pv.setPropertyLov(propertyLovRepository.findOne(oopv.getPropertyValue().getPropertyLov().getId()));
			}else if (oopv.getPropertyValue().getPropertyLov() != null
					&& oopv.getPropertyValue().getPropertyLov().getId() == 0 ) 
			{
				pv.setPropertyLov(null);
			}
			insertPropertyChangeHistory(oopv);
			pv.calculatePrice(oopv.getOrderOffering().getProductionWidth(),oopv.getOrderOffering().getProductionHeight(),oopv.getOrderOffering().getEnteredDepth());
			
				

			propertyValueRepository.save(pv);
			orderOfferingPropertyRepository.save(oopv);
		}
	}
	

	private void insertPropertyChangeHistory(TOrderOfferingPropval oopv) {
		TPropertyValue newpv = oopv.getPropertyValue();
		TPropertyValue oldpv = propertyValueRepository.findOne(oopv.getPropertyValue().getId());

		String oldVal = ""; 
		String newVal = "";

		if (newpv.getProperty().getPropertyType().equals(TPropertyType.PROPERTY_TYPE_LIST)) {
			oldVal = oldpv.getPropertyLov() != null && oldpv.getPropertyLov().getId() != null ? oldpv.getPropertyLov().getName() : "";
			newVal = newpv.getPropertyLov() != null && newpv.getPropertyLov().getId() != null ? newpv.getPropertyLov().getName() : "";
		
		} else if (newpv.getProperty().getPropertyType().equals(TPropertyType.PROPERTY_TYPE_TEXT)) {
			oldVal = oldpv.getValueText() != null ? oldpv.getValueText() : "";
			newVal = newpv.getValueText() != null ? newpv.getValueText() : "";

		} else if (newpv.getProperty().getPropertyType().equals(TPropertyType.PROPERTY_TYPE_NUMBER)) {
			oldVal = oldpv.getValueNumber() != null ? oldpv.getValueNumber().toString() : "";
			newVal = newpv.getValueNumber() != null ? newpv.getValueNumber().toString() : "";

		} 
		if(!oldVal.equals(newVal)) {
			insertProposalHistoryRecord(proposalRepository.findByOrderId(oopv.getOrderOffering().getOrder().getId()).getId()
					,"KAPI ÖZELLİĞİ GÜNCELLEME"
					,"Kapı Özelliği Değişti."
					+ " Model: " +oopv.getOrderOffering().getOffering().getName()
					+ " Boyut: " +oopv.getOrderOffering().getDimensionInfo() 
					+ " Adet : " +oopv.getOrderOffering().getQuantity()
					+ " Özellik : " +oopv.getPropertyValue().getProperty().getName()
					, oldVal, newVal);
		}
		
		if (newpv.getProperty().getPriceType().equals(TPriceType.PRICE_TYPE_SPECIAL)) {
			double oldPrice = oldpv.getCalculatedPrice();
			double newPrice = newpv.getCalculatedPrice();
			if(oldPrice != newPrice) {
				insertProposalHistoryRecord(proposalRepository.findByOrderId(oopv.getOrderOffering().getOrder().getId()).getId()
						,"KAPI ÖZELLİĞİ FİYAT GÜNCELLEME"
						,"Kapı Özelliği Fiyatı Değişti."
						+ " Model: " +oopv.getOrderOffering().getOffering().getName()
						+ " Boyut: " +oopv.getOrderOffering().getDimensionInfo() 
						+ " Adet : " +oopv.getOrderOffering().getQuantity()
						+ " Özellik : " +oopv.getPropertyValue().getProperty().getName()
						, Double.toString(oldPrice), Double.toString(newPrice));
			}
		}
	}
	@Override
	public void insertProposalHistoryRecord(Integer proposalId,String operation,String operationInfo,String oldValue,String newValue) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		TProposalHistory ph = new TProposalHistory();
		ph.setProposal(new TProposal(proposalId));
		ph.setUser(userRepository.findByLoginName(auth.getName()));
		ph.setOperation(operation);
		ph.setOperationInfo(operationInfo);
		ph.setNewValue(newValue);
		ph.setOldValue(oldValue);
		ph.setUpdateDate(new Date());
		
		proposalHistoryRepository.save(ph);
		
	}
	
	@Override
	public TProposal findProposalByOrderId(Integer orderId) {
		return proposalRepository.findByOrderId(orderId);
	}
	@Override
	public void updatePropertyValue(TPropertyValue propertyValue) {
		propertyValueRepository.save(propertyValue);
	}
	@Override
	public List<TProposalHistory> findAllProposalHistory(Integer proposalId) {
		return proposalRepository.findAllProposalHistory(proposalId);
	}
	@Override
	public TOffering findOfferingById(Integer offeringId) {
		return offeringRepository.findOne(offeringId);
	}
	@Override
	public TPropertyLov findPropertyLovById(Integer id) {
		return propertyLovRepository.findOne(id);
	}
	@Override
	public void updatePropertyLovOfOrderOffering(TOrderOffering orderOffering, String propValCode, Integer value) {
		if (value != null) {
			List<TOrderOfferingPropval> propValList = orderOfferingPropertyRepository
					.findByOrderOfferingId(orderOffering.getId());
			for (TOrderOfferingPropval oopv : propValList) {
				if (oopv.getPropertyValue().getProperty().getCode().equals(propValCode)) {
					oopv.getPropertyValue().setPropertyLov(new TPropertyLov(value));
					propertyValueRepository.save(oopv.getPropertyValue());
					break;
				}
			}
		}
	}
	@Override
	public String getNextProposalNo() {
		TProposalSEQ t = new TProposalSEQ();
		proposalSEQRepository.save(t);
		
		String proposalSeqId = t.getId().toString();
		DateFormat df = new SimpleDateFormat("yyMMdd");
		String sdt = df.format(new Date(System.currentTimeMillis()));

		for (; 4 > proposalSeqId.length();) {
			proposalSeqId = "0" + proposalSeqId;
		}
		return sdt + proposalSeqId;
	}
	
	@Override
	public String generateProductNo(TOrderOffering oo) {
		TProductInstanceSEQ t = new TProductInstanceSEQ();
		productInstanceSEQRepository.save(t);
		
		String proposalSeqId = t.getId().toString();
		DateFormat df = new SimpleDateFormat("yyMM");
		String sdt = df.format(new Date(System.currentTimeMillis()));

		for (; 4 > proposalSeqId.length();) {
			proposalSeqId = "0" + proposalSeqId;
		}
		if(oo.getOffering().getProductType().equals(TProductType.PRODUCT_TYPE_FIREDOOR)) 
		{
			sdt = "Y"+sdt;
		}
		else if(oo.getOffering().getProductType().equals(TProductType.PRODUCT_TYPE_TECHNICALDOOR)) 
		{
			sdt = "T"+sdt;	
		}
		else if(oo.getOffering().getProductType().equals(TProductType.PRODUCT_TYPE_FIRESHAFTDOOR)) 
		{
			sdt = "YS"+sdt;
		}
		else if(oo.getOffering().getProductType().equals(TProductType.PRODUCT_TYPE_SHAFTDOOR)) 
		{
			sdt = "S"+sdt;
		}
		return sdt + proposalSeqId;
	}
	
	@Override
	public void createProductInstancesFromOrder(TOrder order) {
		List<TOrderOffering> orderOfferingList = orderOfferingRepository.findOrderOfferingsByOrderId(order.getId());
		
		for (TOrderOffering oo : orderOfferingList) {
			TPropertyValue pv = orderOfferingRepository.findOrderOfferingPropertyByCode(oo.getId(),Constants.PROP_DOOROPENSIDE);
			for (int j = 0; j < oo.getQuantity(); j++) {
				TProductInstance pi = new TProductInstance();
				pi.setOrderOffering(oo);
				pi.setOrderBy(j+1);
				pi.setProductInstanceStatus(TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_WAITING);
				pi.setProductNo(generateProductNo(oo));
				
				
				
				if(pv.getPropertyLov()==null)
				{
					pi.setDoorOpenSide(TDoorOpenSide.UNDEFINED);
				}
				else if(pv.getPropertyLov().getCode().equals(TDoorOpenSide.LEFT_SIDE.getCode()))
				{
					pi.setDoorOpenSide(TDoorOpenSide.LEFT_SIDE);
				}
				else if(pv.getPropertyLov().getCode().equals(TDoorOpenSide.RIGHT_SIDE.getCode()))
				{
					pi.setDoorOpenSide(TDoorOpenSide.RIGHT_SIDE);
				}
				
				
				productInstanceRepository.save(pi);
			}
		}
	}
	@Override
	public List<TOrder> findAllApprovedAndCompletedOrders() {
		List<TOrder> lst1 =orderRepository.findAllOrdersByStatusId(TOrderStatus.ORDER_STATUS_APPROVED.getId());
		List<TOrder> lst2 =orderRepository.findAllOrdersByStatusId(TOrderStatus.ORDER_STATUS_COMPLETED.getId());
		lst1.addAll(lst2);
		return lst1;
	}
	@Override
	public void updateProductInstance(TProductInstance pi) {
		productInstanceRepository.save(pi);
	}
	@Override
	public double findCaseDepthPrice(TOrderOffering orderOffering) {
		return fullCaseDepthPriceRangeRepository.findCloserDepthForFullCase(orderOffering.getEnteredDepth(),
																			orderOffering.getOffering().getProductType().getId(),
																			orderOffering.getOffering().getProductWingType().getId());
	}
}
