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

/**
 *
 * @author abraham
 */
@Entity
@Table(name = "t_customer_project")
public class TCustomerProject implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
 
    @Column(name = "ProjectName")
    private String projectName;
 
    @Column(name = "Description")
    private String description;
 
    @Column(name = "Amount")
    private String amount;
    
    @Column(name = "StartDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "EstimatedEndDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estimatedEndDate;
    
    @JoinColumn(name = "CustomerId", referencedColumnName = "Id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TCustomer customer;
    
    @JoinColumn(name = "ProjectAddressId", referencedColumnName = "Id")
    @ManyToOne(fetch = FetchType.EAGER)
    private TCustomerAddress projectAddress;

    public TCustomerProject() {
    	
    }

    public TCustomerProject(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEstimatedEndDate() {
        return estimatedEndDate;
    }

    public void setEstimatedEndDate(Date estimatedEndDate) {
        this.estimatedEndDate = estimatedEndDate;
    }

    public TCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(TCustomer customer) {
        this.customer = customer;
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
        if (!(object instanceof TCustomerProject)) {
            return false;
        }
        TCustomerProject other = (TCustomerProject) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tr.com.mindworks.model.TCustomerProject[ id=" + id + " ]";
    }

	public TCustomerAddress getProjectAddress() {
		return projectAddress;
	}

	public void setProjectAddress(TCustomerAddress projectAddress) {
		this.projectAddress = projectAddress;
	}
    
}
