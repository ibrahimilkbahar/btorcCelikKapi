/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.mindworks.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author abraham
 */
@Entity
@Table(name = "t_order")
public class TOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
 
    @Column(name = "OrderNo")
    private String orderNo;
    
    @Size(max = 400)
    @Column(name = "Description")
    private String description;
    
    @Column(name = "OrderDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    
    @Column(name = "ApproveDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approveDate;
    
    @Column(name = "CommitmentDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date commitmentDate;
    
    @JoinColumn(name = "DeliveryAddressId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TCustomerAddress deliveryAddress;
    
    @JoinColumn(name = "CustomerContactId", referencedColumnName = "Id")
    @ManyToOne(fetch = FetchType.EAGER ,optional =true)
    private TCustomerContact customerContact;
    
    @JoinColumn(name = "CustomerProjectId", referencedColumnName = "Id")
    @ManyToOne(fetch = FetchType.EAGER ,optional =true)
    private TCustomerProject customerProject;
    
    @JoinColumn(name = "CustomerId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TCustomer customer;

    @JoinColumn(name = "OrderStatusId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TOrderStatus orderStatus;
    
    @JoinColumn(name = "SalesResponsibleId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TUser salesResponsible;
    
    @JoinColumn(name = "ApproverId", referencedColumnName = "Id")
    @ManyToOne(fetch = FetchType.EAGER)
    private TUser approver;
    
    @Column(name = "TransportRequested")
    private boolean transportRequested;
    @Column(name = "TransportAmount")
    private double transportAmount;
    @Column(name = "TransportDescription")
    private String transportDescription;
    
    @Column(name = "InstallationRequested")
    private boolean installationRequested;
    @Column(name = "InstallationAmount")
    private double installationAmount;
    @Column(name = "InstallationDescription")
    private String installationDescription;
    
    @Column(name = "finalDiscount")
    private double finalDiscount;
    
    @Size(max = 400)
    @Column(name = "PaymentInformation")
    private String paymentInformation;
    
    @Size(max = 400)
    @Column(name = "RejectReason")
    private String rejectReason;
    
    
    @Column(name = "CurrencyCode")
    private String currencyCode;
    
    @Column(name = "CurrencyValue")
    private String currencyValue;
    
    public TOrder() {
    }

    public TOrder(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public Date getCommitmentDate() {
        return commitmentDate;
    }

    public void setCommitmentDate(Date commitmentDate) {
        this.commitmentDate = commitmentDate;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TOrder)) {
            return false;
        }
        TOrder other = (TOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tr.com.mindworks.model.TOrder[ id=" + id + " ]";
    }

	public TCustomerAddress getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(TCustomerAddress deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public TCustomerContact getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(TCustomerContact customerContact) {
		this.customerContact = customerContact;
	}

	public TCustomerProject getCustomerProject() {
		return customerProject;
	}

	public void setCustomerProject(TCustomerProject customerProject) {
		this.customerProject = customerProject;
	}

	public TCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(TCustomer customer) {
		this.customer = customer;
	}

	public TOrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(TOrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public TUser getSalesResponsible() {
		return salesResponsible;
	}

	public void setSalesResponsible(TUser salesResponsible) {
		this.salesResponsible = salesResponsible;
	}

	public boolean getTransportRequested() {
		return transportRequested;
	}

	public void setTransportRequested(boolean transportRequested) {
		this.transportRequested = transportRequested;
	}

	public double getTransportAmount() {
		return transportAmount;
	}

	public void setTransportAmount(double transportAmount) {
		this.transportAmount = transportAmount;
	}

	public String getTransportDescription() {
		return transportDescription;
	}

	public void setTransportDescription(String transportDescription) {
		this.transportDescription = transportDescription;
	}

	public boolean getInstallationRequested() {
		return installationRequested;
	}

	public void setInstallationRequested(boolean installationRequested) {
		this.installationRequested = installationRequested;
	}

	public double getInstallationAmount() {
		return installationAmount;
	}

	public void setInstallationAmount(double installationAmount) {
		this.installationAmount = installationAmount;
	}

	public String getInstallationDescription() {
		return installationDescription;
	}

	public void setInstallationDescription(String installationDescription) {
		this.installationDescription = installationDescription;
	}

	public double getFinalDiscount() {
		return finalDiscount;
	}

	public void setFinalDiscount(double finalDiscount) {
		this.finalDiscount = finalDiscount;
	}

	public String getPaymentInformation() {
		return paymentInformation;
	}

	public void setPaymentInformation(String paymentInformation) {
		this.paymentInformation = paymentInformation;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyValue() {
		return currencyValue;
	}

	public void setCurrencyValue(String currencyValue) {
		this.currencyValue = currencyValue;
	}

	public TUser getApprover() {
		return approver;
	}

	public void setApprover(TUser approver) {
		this.approver = approver;
	}

	 
    
}
