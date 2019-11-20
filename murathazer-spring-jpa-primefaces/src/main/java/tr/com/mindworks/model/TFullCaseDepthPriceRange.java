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
@Table(name = "t_full_case_depth_price_range")

public class TFullCaseDepthPriceRange implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
 
    @Column(name = "DepthMin")
    private int depthMin;
    @Column(name = "DepthMax")
    private int depthMax;
    
    @Column(name = "Price")
    private double price;
    
    @Column(name = "OrderBy")
    private Integer orderBy;
    
    @JoinColumn(name = "ProductTypeId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TProductType productType;

    @JoinColumn(name = "ProductWingTypeId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TProductWingType productWingType;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

	public TProductType getProductType() {
		return productType;
	}

	public void setProductType(TProductType productType) {
		this.productType = productType;
	}

	public TProductWingType getProductWingType() {
		return productWingType;
	}

	public void setProductWingType(TProductWingType productWingType) {
		this.productWingType = productWingType;
	}

	public int getDepthMin() {
		return depthMin;
	}

	public void setDepthMin(int depthMin) {
		this.depthMin = depthMin;
	}

	public int getDepthMax() {
		return depthMax;
	}

	public void setDepthMax(int depthMax) {
		this.depthMax = depthMax;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	

	

   
    
}
