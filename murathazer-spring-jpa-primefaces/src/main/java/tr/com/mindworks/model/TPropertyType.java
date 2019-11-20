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
@Table(name = "t_property_type")

public class TPropertyType implements Serializable {

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
   
    @Transient
	public static final TPropertyType PROPERTY_TYPE_TEXT = new TPropertyType(1, "Metin", "TEXT");
    @Transient
	public static final TPropertyType PROPERTY_TYPE_NUMBER = new TPropertyType(2, "Sayı", "NUMBER");
    @Transient
	public static final TPropertyType PROPERTY_TYPE_LIST = new TPropertyType(3, "Liste", "LIST");
    
      
    
    public TPropertyType() {
    }

    public TPropertyType(Integer id) {
        this.id = id;
    }
    
    public TPropertyType(Integer id,String name,String code) {
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
        if (!(object instanceof TPropertyType)) {
            return false;
        }
        TPropertyType other = (TPropertyType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tr.com.mindworks.model.TPropertyType[ id=" + id + " ]";
    }
    
}
