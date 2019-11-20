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
@Table(name = "t_property")

public class TProperty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
 
    @Column(name = "Name")
    private String name;
 
    @Column(name = "Code")
    private String code;

    @Column(name = "Hint")
    private String hint;
    
	@Column(name = "Price")
    private double price;
	
    @Column(name = "ImagePath")
    private String imagePath;
	    
    @Column(name = "PriceCalculateMath")
    private String priceCalculateMath;
    
    @JoinColumn(name = "PriceTypeId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TPriceType priceType;	
    
    @JoinColumn(name = "PropertyTypeId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TPropertyType propertyType;
    
    @JoinColumn(name = "PropertyGroupId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TPropertyGroup propertyGroup;
    
    @JoinColumn(name = "PropertyLovDefId", referencedColumnName = "Id")
    @ManyToOne(fetch = FetchType.EAGER)
    private TPropertyLovDef propertyLovDef;

    public TProperty() {
    }

    public TProperty(Integer id) {
        this.id = id;
    }
    public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
   
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
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
        if (!(object instanceof TProperty)) {
            return false;
        }
        TProperty other = (TProperty) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tr.com.mindworks.model.TProperty[ id=" + id + " ]";
    }

	public TPropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(TPropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public TPropertyGroup getPropertyGroup() {
		return propertyGroup;
	}

	public void setPropertyGroup(TPropertyGroup propertyGroup) {
		this.propertyGroup = propertyGroup;
	}

	public TPropertyLovDef getPropertyLovDef() {
		return propertyLovDef;
	}

	public void setPropertyLovDef(TPropertyLovDef propertyLovDef) {
		this.propertyLovDef = propertyLovDef;
	}

	public TPriceType getPriceType() {
		return priceType;
	}

	public void setPriceType(TPriceType priceType) {
		this.priceType = priceType;
	}

	public String getPriceCalculateMath() {
		return priceCalculateMath;
	}

	public void setPriceCalculateMath(String priceCalculateMath) {
		this.priceCalculateMath = priceCalculateMath;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
    
}
