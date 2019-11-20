package tr.com.mindworks.controllers.order;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Provider;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;
import tr.com.mindworks.annotations.SpringFlowScoped;
import tr.com.mindworks.controllers.BaseController;
import tr.com.mindworks.model.TDoorOpenSide;
import tr.com.mindworks.model.TOrder;
import tr.com.mindworks.model.TOrderOffering;
import tr.com.mindworks.model.TOrderStatus;
import tr.com.mindworks.model.TProductInstance;
import tr.com.mindworks.model.TProductInstanceStatus;
import tr.com.mindworks.services.OrderService;
import tr.com.mindworks.util.PdfExporter;

@Component("orderController")
@SpringFlowScoped
@SessionScope
public class OrderController extends BaseController {

	@Autowired
	private Provider<OrderService> orderService;

	
	@Getter
	@Setter
	private TOrder order;
	@Getter
	@Setter
	private List<TOrder> orderList;
	
	@Getter
	@Setter
	private List<TOrderOffering> orderOfferingList;
	
	@Getter
	@Setter
	private List<TDoorOpenSide> doorOpenSideList;
	
	
	
	

	
	
	
	@PostConstruct
	public void initOrderList() {
		orderList = orderService.get().findAllApprovedAndCompletedOrders();

		
	}

	public void initializeDetailOrder() {
		FacesContext context = FacesContext.getCurrentInstance();
		String orderId = context.getExternalContext().getRequestParameterMap().get("orderId");
		if (orderId == null || "".equals(orderId)) {
			jsfMessageUtil.addInfoMessage("sipariş bulunamadı.");
		} else {
			order = orderService.get().findOrderById(Integer.parseInt(orderId));
			orderOfferingList = orderService.get().findOrderOfferingsWithPrdInsByOrderId(Integer.parseInt(orderId));
		}
		
		doorOpenSideList= new ArrayList<TDoorOpenSide>();
		doorOpenSideList.add(TDoorOpenSide.UNDEFINED);
		doorOpenSideList.add(TDoorOpenSide.LEFT_SIDE);
		doorOpenSideList.add(TDoorOpenSide.RIGHT_SIDE);
		
		
	}
	
	public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            DataTable dataTable = (DataTable) event.getSource();
            TProductInstance pi = (TProductInstance) dataTable.getRowData();
            
            
            
            if(newValue.equals(TDoorOpenSide.LEFT_SIDE.getId()))
            	pi.setDoorOpenSide(TDoorOpenSide.LEFT_SIDE);
            else if(newValue.equals(TDoorOpenSide.RIGHT_SIDE.getId()))
            	pi.setDoorOpenSide(TDoorOpenSide.RIGHT_SIDE);
            else 
            	pi.setDoorOpenSide(TDoorOpenSide.UNDEFINED);
            
            orderService.get().updateProductInstance(pi);
        }
    }
	public void onCellEdit(TProductInstance pi, Integer statusId) {
         
		if(pi.getProductInstanceStatus().getId()>TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_WAITING.getId()) 
        {
        	jsfMessageUtil.handleException("Üretime gönderilmiş bir kapının kapı açım yönü değiştirilemez.", new Exception("Hatalı İşlem"));
			return;
        }
        if(statusId.equals(TDoorOpenSide.LEFT_SIDE.getId()))
        	pi.setDoorOpenSide(TDoorOpenSide.LEFT_SIDE);
        else if(statusId.equals(TDoorOpenSide.RIGHT_SIDE.getId()))
        	pi.setDoorOpenSide(TDoorOpenSide.RIGHT_SIDE);
        else 
        	pi.setDoorOpenSide(TDoorOpenSide.UNDEFINED);
        
        orderService.get().updateProductInstance(pi);
    
    }

	
	public String saveOrder() {
		try {
			orderService.get().saveOrder(order);
			jsfMessageUtil.addInfoMessage("Sipariş Güncellendi. :)");
			return "orderSaved";
		} catch (Exception exception) {
			jsfMessageUtil.addInfoMessage("Sipariş Güncellenenmedi.!!!"+ exception.getMessage());
			return null;
		}
	}
	
	
	public StreamedContent productionReportOrderOffering(TOrderOffering orderOffering) {
		return PdfExporter.downloadOrderOfferingProductionReport(orderOffering);
	}
	public StreamedContent productionReportProductInstance(TProductInstance productInstance) {
		return PdfExporter.downloadOrderOfferingProductionReport(productInstance);
	}
	
	public void updateProductInstanceStatus(TProductInstance pi, int statusId) {
		if(pi.getDoorOpenSide().getCode().equals(TDoorOpenSide.UNDEFINED.getCode())
				&&statusId>TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_WAITING.getId()) 
		{
			jsfMessageUtil.handleException("Üretime geçmeden önce kapının kanat açım yönünü seçmeniz gerekmektedir.", new Exception("Hatalı İşlem"));
			return;
		}
		
		TProductInstanceStatus pis = null;
		if (TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_WAITING.getId().equals(statusId))
			pis = TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_WAITING;
		else if (TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_CUTTING.getId().equals(statusId))
			pis = TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_CUTTING;
		else if (TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_CNC.getId().equals(statusId))
			pis = TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_CNC;
		else if (TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_BENDING.getId().equals(statusId))
			pis = TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_BENDING;
		else if (TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_MOLDING.getId().equals(statusId))
			pis = TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_MOLDING;
		else if (TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_PAINTING.getId().equals(statusId))
			pis = TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_PAINTING;
		else if (TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_MONTAGE.getId().equals(statusId))
			pis = TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_MONTAGE;
		else if (TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_SHIPMENT.getId().equals(statusId))
			pis = TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_SHIPMENT;
		else if (TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_INSTALLATION.getId().equals(statusId))
			pis = TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_INSTALLATION;
		else if (TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_COMPLETED.getId().equals(statusId))
			pis = TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_COMPLETED;
		else if (TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_CANCELLED.getId().equals(statusId))
			pis = TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_CANCELLED;
	 
		
		
		
		pi.setProductInstanceStatus(pis);
		orderService.get().updateProductInstance(pi);
		
		boolean allCompleted = true;
		for (TOrderOffering oo : orderOfferingList) {
			for (TProductInstance pi2 : oo.getProductInstanceList()) {
				if(!(pi2.getProductInstanceStatus().equals(TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_CANCELLED)
				  || pi2.getProductInstanceStatus().equals(TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_COMPLETED))) 
				{
					allCompleted = false;
				}
			}
		}
		if(allCompleted) 
		{
			order.setOrderStatus(TOrderStatus.ORDER_STATUS_COMPLETED);
			orderService.get().saveOrder(order);
			
		}
	}
	public double getCompletePercentOfOrder() {
		double result = 0d;
		int i=0;
		for (TOrderOffering oo : orderOfferingList) {
			result = result + getCompletePercentOfOrderOffering(oo);
			i++;
		} 
		return round(result/i,2);
	}
	public double getCompletePercentOfOrderOffering(TOrderOffering oo) {
		double result = 0d;
		int i=0;
		for (TProductInstance pi : oo.getProductInstanceList()) {
			if(pi.getProductInstanceStatus().equals(TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_CANCELLED)) {
				continue;
			} 
			result = result + getCompletePercentOfProduct(pi);
			i++;
		} 
		return round(result/i,2);
	}
	public double getCompletePercentOfProduct(TProductInstance pi) {
		if(pi.getProductInstanceStatus().equals(TProductInstanceStatus.PRODUCT_INSTANCE_STATUS_CANCELLED)) {
			return 0;
		} 
		return (pi.getProductInstanceStatus().getId()-1)*100/7;
	}
	public double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	
	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}
