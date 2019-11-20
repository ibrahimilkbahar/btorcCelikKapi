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
import javax.persistence.Table;

/**
 *
 * @author abraham
 */
@Entity
@Table(name = "t_order_offering_propval")

public class TOrderOfferingPropval implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;

	@JoinColumn(name = "OrderOfferingId", referencedColumnName = "Id")
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private TOrderOffering orderOffering;

	@JoinColumn(name = "PropertyValueId", referencedColumnName = "Id")
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private TPropertyValue propertyValue;

	@Column(name = "OrderBy")
	private int orderBy;

	@Column(name = "IsMondatory")
	private boolean isMondatory;

	@Column(name = "IsReadonly")
	private boolean isReadonly;

	@Column(name = "IsVisibleOnReport")
	private boolean isVisibleOnReport;


	public TOrderOfferingPropval() {
	}

	public TOrderOfferingPropval(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public boolean getIsMondatory() {
		return isMondatory;
	}

	public void setIsMondatory(boolean isMondatory) {
		this.isMondatory = isMondatory;
	}

	public boolean getIsReadonly() {
		return isReadonly;
	}

	public void setIsReadonly(boolean isReadonly) {
		this.isReadonly = isReadonly;
	}

    public boolean getIsVisibleOnReport() {
        return isVisibleOnReport;
    }

    public void setIsVisibleOnReport(boolean isVisibleOnReport) {
        this.isVisibleOnReport = isVisibleOnReport;
    }
    
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof TOrderOfferingPropval)) {
			return false;
		}
		TOrderOfferingPropval other = (TOrderOfferingPropval) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "tr.com.mindworks.model.TOrderOfferingPropval[ id=" + id + " ]";
	}

	public TOrderOffering getOrderOffering() {
		return orderOffering;
	}

	public void setOrderOffering(TOrderOffering orderOffering) {
		this.orderOffering = orderOffering;
	}

	public TPropertyValue getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(TPropertyValue propertyValue) {
		this.propertyValue = propertyValue;
	}

}
