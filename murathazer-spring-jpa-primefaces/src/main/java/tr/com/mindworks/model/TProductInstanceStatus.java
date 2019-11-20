/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.mindworks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

/**
 *
 * @author abraham
 */
@Entity
@Table(name = "t_product_instance_status")
public class TProductInstanceStatus implements Serializable {

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
    
  
    public TProductInstanceStatus() {
    }

    public TProductInstanceStatus(Integer id) {
        this.id = id;
    }

    
    @Transient
   	public static final TProductInstanceStatus PRODUCT_INSTANCE_STATUS_WAITING = new TProductInstanceStatus(1, "Beklemede", "WAITING");
    @Transient
   	public static final TProductInstanceStatus PRODUCT_INSTANCE_STATUS_CUTTING = new TProductInstanceStatus(2, "Kesim", "CUTTING");
    @Transient
   	public static final TProductInstanceStatus PRODUCT_INSTANCE_STATUS_CNC = new TProductInstanceStatus(3, "CNC", "CNC");
    @Transient
   	public static final TProductInstanceStatus PRODUCT_INSTANCE_STATUS_BENDING = new TProductInstanceStatus(4, "Büküm", "BENDING");
    @Transient
   	public static final TProductInstanceStatus PRODUCT_INSTANCE_STATUS_MOLDING = new TProductInstanceStatus(5, "Kaynak", "MOLDING");
    @Transient
   	public static final TProductInstanceStatus PRODUCT_INSTANCE_STATUS_PAINTING = new TProductInstanceStatus(6, "Boyama", "PAINTING");
    @Transient
   	public static final TProductInstanceStatus PRODUCT_INSTANCE_STATUS_MONTAGE = new TProductInstanceStatus(7, "Montaj", "MONTAGE");
    @Transient
   	public static final TProductInstanceStatus PRODUCT_INSTANCE_STATUS_SHIPMENT = new TProductInstanceStatus(8, "Nakliye", "SHIPMENT");
    @Transient
   	public static final TProductInstanceStatus PRODUCT_INSTANCE_STATUS_INSTALLATION = new TProductInstanceStatus(9, "Saha Montaj", "INSTALLATION");
    @Transient
   	public static final TProductInstanceStatus PRODUCT_INSTANCE_STATUS_COMPLETED = new TProductInstanceStatus(10, "Tamamlandı", "COMPLETED");
    @Transient
   	public static final TProductInstanceStatus PRODUCT_INSTANCE_STATUS_CANCELLED = new TProductInstanceStatus(11, "İptal Edildi", "CANCELLED");
    
    
    public TProductInstanceStatus(Integer id, String name, String code) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
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
        if (!(object instanceof TProductInstanceStatus)) {
            return false;
        }
        TProductInstanceStatus other = (TProductInstanceStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tr.com.mindworks.model.TProductInstanceStatus[ id=" + id + " ]";
    }

    
    
}
