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
@Table(name = "t_product_wing_type")

public class TProductWingType implements Serializable {

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
    
    
    public TProductWingType() {
    }

    public TProductWingType(Integer id) {
        this.id = id;
    }

    public TProductWingType(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
	}

	@Transient
	public static final TProductWingType WING_TYPE_ONEWING = new TProductWingType(1, "Tek Kanat", "ONEWING");
    @Transient
	public static final TProductWingType WING_TYPE_DOUBLEWING = new TProductWingType(2, "Tam Kasa", "DOUBLEWING");
    
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

	

   
    
}
