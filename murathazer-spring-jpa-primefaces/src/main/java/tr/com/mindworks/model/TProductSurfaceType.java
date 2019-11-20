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
@Table(name = "t_product_surface_type")

public class TProductSurfaceType implements Serializable {

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
    
    
    public TProductSurfaceType() {
    }

    public TProductSurfaceType(Integer id) {
        this.id = id;
    }
    
    public TProductSurfaceType(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
	}

    
    
	@Transient
	public static final TProductSurfaceType SURFACE_TYPE_STANDART = new TProductSurfaceType(1, "Standart", "STANDART");
    @Transient
	public static final TProductSurfaceType SURFACE_TYPE_GLASS = new TProductSurfaceType(1, "Camlı", "GLASS");
	@Transient
	public static final TProductSurfaceType SURFACE_TYPE_ZMENFEZ = new TProductSurfaceType(1, "Z Menfezli", "ZMENFEZ");
    @Transient
	public static final TProductSurfaceType SURFACE_TYPE_PMENFEZ = new TProductSurfaceType(1, "Punch Menfezli", "PMENFEZ");
    

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
