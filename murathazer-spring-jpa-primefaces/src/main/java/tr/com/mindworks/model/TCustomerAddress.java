/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.mindworks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author abraham
 */
@Entity
@Table(name = "t_customer_address")
@NamedQueries({
    @NamedQuery(name = "TCustomerAddress.findAll", query = "SELECT t FROM TCustomerAddress t")})
public class TCustomerAddress implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 
    @Column(name = "Id")
    private Integer id;
 
    @Column(name = "IsCentralAddress")
    private boolean isCentralAddress;
 
    @Column(name = "AddressName")
    private String addressName;
 
    @Column(name = "AddressText")
    private String addressText;
 
    @Column(name = "Description")
    private String description;
 
    @Column(name = "PostCode")
    private int postCode;
    
    @JoinColumn(name = "CustomerId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TCustomer customer;
    
    @JoinColumn(name = "DistrictId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TDistrict district;

    public TCustomerAddress() {
    }

    public TCustomerAddress(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressText() {
        return addressText;
    }

    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public TCustomer getCustomer() {
        return customer;
    }

    public void setCustomerId(TCustomer customer) {
        this.customer = customer;
    }

    public TDistrict getDistrict() {
        return district;
    }

    public void setDistrict(TDistrict district) {
        this.district = district;
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
        if (!(object instanceof TCustomerAddress)) {
            return false;
        }
        TCustomerAddress other = (TCustomerAddress) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tr.com.mindworks.model.TCustomerAddress[ id=" + id + " ]";
    }
    
	public boolean getIsCentralAddress() {
        return isCentralAddress;
    }

    public void setIsCentralAddress(boolean isCentralAddress) {
        this.isCentralAddress = isCentralAddress;
    }
}
