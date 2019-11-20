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

import org.springframework.data.annotation.Transient;

/**
 *
 * @author abraham
 */
@Entity
@Table(name = "t_property_lov")
public class TPropertyLov implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
 
    @Column(name = "Name")
    private String name;
 
    @Column(name = "Code")
    private String code;
    
    @Column(name = "OrderBy")
    private Integer orderBy;
    
	@Column(name = "Price")
    private double price;
	
    @Column(name = "ImagePath")
    private String imagePath;
    
    @Column(name = "RelatedInfo")
    private String relatedInfo;
    
    @Column(name = "RelatedValue")
    private String relatedValue;
	  
    @JoinColumn(name = "PropertyLovDefId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TPropertyLovDef propertyLovDef;

    public TPropertyLov() {
    }

    public TPropertyLov(Integer id) {
        this.id = id;
    }
    public TPropertyLov(Integer id,String name,String imagePath) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
    }
    
    @Transient
	public static final TPropertyLov DEFAULT_EMPTY_SELECTION = new TPropertyLov(0, "Seçiniz", "");
    
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
    
    public double getPrice() {
		return price;
	}
    
	public void setPrice(double price) {
		this.price = price;
	}
	
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
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
        if (!(object instanceof TPropertyLov)) {
            return false;
        }
        TPropertyLov other = (TPropertyLov) object;
        if ((this.id == null && other.id != null) ||
        	(this.id != null && other.id == null) ||
        	(this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tr.com.mindworks.model.TPropertyLov[ id=" + id + " ]";
    }

	public TPropertyLovDef getPropertyLovDef() {
		return propertyLovDef;
	}

	public void setPropertyLovDef(TPropertyLovDef propertyLovDef) {
		this.propertyLovDef = propertyLovDef;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getRelatedValue() {
		return relatedValue;
	}

	public void setRelatedValue(String relatedValue) {
		this.relatedValue = relatedValue;
	}

	public String getRelatedInfo() {
		return relatedInfo;
	}

	public void setRelatedInfo(String relatedInfo) {
		this.relatedInfo = relatedInfo;
	}

	
    
}
