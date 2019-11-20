/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.mindworks.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;
/**
 *
 * @author abraham
 */
@Entity
@Table(name = "t_order_offering")
public class TOrderOffering implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
 
    @Column(name = "Description")
    private String description;
 
    @Column(name = "Quantity")
    private int quantity;
    
    @Column(name = "InstanceAmount")
    private double instanceAmount;
 
    @Column(name = "InstanceDiscount")
    private double instanceDiscount;
    
    @Column(name = "InstanceIncrease")
    private double instanceIncrease;
    
    @Column(name = "TotalAmount")
    private double totalAmount;
    
    
    @Column(name = "EnteredWidth")
    private Integer enteredWidth;
    @Column(name = "EnteredHeight")
    private Integer enteredHeight;
    @Column(name = "EnteredDepth")
    private Integer enteredDepth;
    
    @Column(name = "ProductionWidth")
    private Integer productionWidth;
    @Column(name = "ProductionHeight")
    private Integer productionHeight;

    
    @Column(name = "DimensionPrice")
    private double dimensionPrice; 

    
    @JoinColumn(name = "OrderId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TOrder order;
    
    @JoinColumn(name = "OfferingId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TOffering offering;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderOffering", fetch = FetchType.EAGER)
    private List<TOrderOfferingPropval> orderOfferingPropValList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderOffering", fetch = FetchType.LAZY)
    private List<TProductInstance> productInstanceList;
    
    @JoinColumn(name = "ProductCaseTypeId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TProductCaseType productCaseType;
     
    
    public TOrderOffering() {
    }

    public TOrderOffering(Integer id) {
        this.id = id;
    }

    public TOrderOffering(Integer id, Date commitmentDate, int quantity, long instanceAmount, long totalAmount) {
        this.id = id;
        this.quantity = quantity;
        this.instanceAmount = instanceAmount;
        this.totalAmount = totalAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getInstanceAmount() {
        return instanceAmount;
    }

    public void setInstanceAmount(double instanceAmount) {
        this.instanceAmount = instanceAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public TProductCaseType getProductCaseType() {
		return productCaseType;
	}

	public void setProductCaseType(TProductCaseType productCaseType) {
		this.productCaseType = productCaseType;
	}

   



	public double getInstanceDiscount() {
		return instanceDiscount;
	}

	public void setInstanceDiscount(double instanceDiscount) {
		this.instanceDiscount = instanceDiscount;
	}

	public List<TOrderOfferingPropval> getOrderOfferingPropValList() {
		return orderOfferingPropValList;
	}

	public void setOrderOfferingPropValList(List<TOrderOfferingPropval> orderOfferingPropValList) {
		this.orderOfferingPropValList = orderOfferingPropValList;
	}

	
	
	
	@Transient
	public void calculateInstancePrice() {
		double calculatedInstanceAmount = 0;
		for (TOrderOfferingPropval oopv : orderOfferingPropValList) {
			calculatedInstanceAmount = calculatedInstanceAmount + oopv.getPropertyValue().getCalculatedPrice();
		}
		double instanceCasePrice = getDimensionPrice();
				
		double instancePrice = ((calculatedInstanceAmount + instanceCasePrice + instanceIncrease) * (100 - instanceDiscount))/100;
		
		this.setInstanceAmount(round(instancePrice, 2));
		this.setTotalAmount(round(instancePrice * quantity, 2));
	}
	
	public double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	public TOrder getOrder() {
		return order;
	}

	public void setOrder(TOrder order) {
		this.order = order;
	}

	public TOffering getOffering() {
		return offering;
	}

	public void setOffering(TOffering offering) {
		this.offering = offering;
	}

	public double getInstanceIncrease() {
		return instanceIncrease;
	}

	public void setInstanceIncrease(double instanceIncrease) {
		this.instanceIncrease = instanceIncrease;
	}

	public Integer getEnteredWidth() {
		return enteredWidth;
	}

	public void setEnteredWidth(Integer enteredWidth) {
		this.enteredWidth = enteredWidth;
	}

	public Integer getEnteredHeight() {
		return enteredHeight;
	}

	public void setEnteredHeight(Integer enteredHeight) {
		this.enteredHeight = enteredHeight;
	}

	public Integer getEnteredDepth() {
		return enteredDepth;
	}

	public void setEnteredDepth(Integer enteredDepth) {
		this.enteredDepth = enteredDepth;
	}

	@Transient
	public void setPropertyLovValue(String propertyCode, Integer refId) {
		for (TOrderOfferingPropval oopv : orderOfferingPropValList) {
			if(oopv.getPropertyValue().getProperty().getCode().equals(propertyCode)) 
			{
				TPropertyLov l = new TPropertyLov();
				l.setId(refId);
				oopv.getPropertyValue().setPropertyLov(l);
			}
		}
	}

	public List<TProductInstance> getProductInstanceList() {
		return productInstanceList;
	}

	public void setProductInstanceList(List<TProductInstance> productInstanceList) {
		this.productInstanceList = productInstanceList;
	}

	public Integer getProductionWidth() {
		return productionWidth;
	}

	public void setProductionWidth(Integer productionWidth) {
		this.productionWidth = productionWidth;
	}

	public Integer getProductionHeight() {
		return productionHeight;
	}

	public void setProductionHeight(Integer productionHeight) {
		this.productionHeight = productionHeight;
	}

	

	public double getDimensionPrice() {
		return dimensionPrice;
	}

	public void setDimensionPrice(double dimensionPrice) {
		this.dimensionPrice = dimensionPrice;
	}

	  
	@Transient
	public String getDimensionInfo() {
		return productionWidth + "/" + productionHeight + "/" + enteredDepth ;
	}

}
