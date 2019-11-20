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
@Table(name = "t_dimension")
public class TDimension implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
 
    @Column(name = "WidthMin")
    private int widthMin;
    @Column(name = "WidthMax")
    private int widthMax;

    @Column(name = "HeightMin")
    private int heightMin;
    @Column(name = "HeightMax")
    private int heightMax;

    @Column(name = "ProductionWidth")
    private int productionWidth;
    @Column(name = "ProductionHeight")
    private int productionHeight;
    
    @Column(name = "Price")
    private double price;
    
    @JoinColumn(name = "ProductTypeId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TProductType productType;

    @JoinColumn(name = "ProductWingTypeId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TProductWingType productWingType;
    

    
    public TDimension() {
    }

    public TDimension(Integer id) {
        this.id = id;
    }
    
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

  

	public TProductType getProductType() {
		return productType;
	}

	public void setProductType(TProductType productType) {
		this.productType = productType;
	}

	public int getWidthMin() {
		return widthMin;
	}

	public void setWidthMin(int widthMin) {
		this.widthMin = widthMin;
	}

	public int getWidthMax() {
		return widthMax;
	}

	public void setWidthMax(int widthMax) {
		this.widthMax = widthMax;
	}

	public int getHeightMin() {
		return heightMin;
	}

	public void setHeightMin(int heightMin) {
		this.heightMin = heightMin;
	}

	public int getHeightMax() {
		return heightMax;
	}

	public void setHeightMax(int heightMax) {
		this.heightMax = heightMax;
	}

	

 

	

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getProductionWidth() {
		return productionWidth;
	}

	public void setProductionWidth(Integer productionWidth) {
		this.productionWidth = productionWidth;
	}

	public Integer getProductionHeight() {
		return productionHeight;
	}

	public void setProductionHeight(Integer productionHeight) {
		this.productionHeight = productionHeight;
	}

	
	public TProductWingType getProductWingType() {
		return productWingType;
	}

	public void setProductWingType(TProductWingType productWingType) {
		this.productWingType = productWingType;
	}

	
	

    
}
