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
@Table(name = "t_product_type")

public class TProductType implements Serializable {

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

    public TProductType() {
    }

    public TProductType(Integer id) {
        this.id = id;
    }
    
    public TProductType(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
	}

    
    
	@Transient
	public static final TProductType PRODUCT_TYPE_FIREDOOR = new TProductType(1, "Yangın Kapısı", "FIREDOOR");
    @Transient
	public static final TProductType PRODUCT_TYPE_TECHNICALDOOR = new TProductType(2, "Teknik Kapı", "TECHNICALDOOR");
	@Transient
	public static final TProductType PRODUCT_TYPE_FIRESHAFTDOOR = new TProductType(3, "Yangına Dayanımlı Saç Kapı", "FIRESHAFTDOOR");
    @Transient
	public static final TProductType PRODUCT_TYPE_SHAFTDOOR = new TProductType(4, "Saç Kapı", "SHAFTDOOR");
    

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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orderBy == null) ? 0 : orderBy.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TProductType other = (TProductType) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} 
		return true;
	}

   
    
}
