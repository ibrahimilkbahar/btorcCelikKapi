/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.mindworks.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.data.annotation.Transient;

/**
 *
 * @author abraham
 */
@Entity
@Table(name = "t_property_value")
public class TPropertyValue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
 
    @Column(name = "ValueText")
    private String valueText;
    
    @Column(name = "ValueNumber")
    private Integer valueNumber;
    
    @Column(name = "ValueDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date valueDate;
 
    @Column(name = "ValueFile")
    private String valueFile;
 
    @Column(name = "ValueColour")
    private String valueColour;
    

	@Column(name = "CalculatedPrice")
    private double calculatedPrice;
    
    @JoinColumn(name = "PropertyId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TProperty property;
    
    @JoinColumn(name = "PropertyLovId", referencedColumnName = "Id")
    @ManyToOne(fetch = FetchType.EAGER)
    private TPropertyLov propertyLov;
    

    
    public TPropertyValue() {
    }

    public TPropertyValue(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    public Integer getValueNumber() {
        return valueNumber;
    }

    public void setValueNumber(Integer valueNumber) {
        this.valueNumber = valueNumber;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public String getValueFile() {
        return valueFile;
    }

    public void setValueFile(String valueFile) {
        this.valueFile = valueFile;
    }

    public String getValueColour() {
        return valueColour;
    }

    public void setValueColour(String valueColour) {
        this.valueColour = valueColour;
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
        if (!(object instanceof TPropertyValue)) {
            return false;
        }
        TPropertyValue other = (TPropertyValue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tr.com.mindworks.model.TPropertyValue[ id=" + id + " ]";
    }

    @Transient
	public TPropertyValue clonePropertyValue() {
    	TPropertyValue clone = new TPropertyValue();
    	clone.setProperty(property);
    	clone.setPropertyLov(propertyLov);
    	clone.setValueColour(valueColour);
    	clone.setValueDate(valueDate);
    	clone.setValueFile(valueFile);
    	clone.setValueNumber(valueNumber);
    	clone.setValueText(valueText);
    	clone.setCalculatedPrice(calculatedPrice);
    	
    			
    	return clone;
	}
    
    @Transient
	public void calculatePrice(Integer productionWidth,Integer productionHeight,Integer productionDepth) {
    	
		if (getProperty().getPriceType().equals(TPriceType.PRICE_TYPE_SELF)) 
		{
			if (getProperty().getPropertyType().equals(TPropertyType.PROPERTY_TYPE_LIST)) 
			{
				if (getPropertyLov() != null && getPropertyLov().getId()>0) 
				{
					setCalculatedPrice(getPropertyLov().getPrice());
				}
			} 
			else 
			{
				setCalculatedPrice(getProperty().getPrice());
			}
		} else if ( getProperty().getPriceType().equals(TPriceType.PRICE_TYPE_CALCULATE)) {
			try {
				if (getProperty().getPropertyType().equals(TPropertyType.PROPERTY_TYPE_LIST)
						&&!getProperty().getPriceCalculateMath().isEmpty()) 
				{
					String calcualteMath =  getProperty().getPriceCalculateMath();
					 
					calcualteMath = calcualteMath.replace("EN", "(" + productionWidth + ")");
					calcualteMath = calcualteMath.replace("BOY", "(" + productionHeight + ")");
					calcualteMath = calcualteMath.replace("DUVAR", "(" + productionDepth + ")");
	
					ScriptEngineManager mgr = new ScriptEngineManager();
					ScriptEngine engine = mgr.getEngineByName("JavaScript");
					double priceFactor = Double.parseDouble(engine.eval(calcualteMath).toString());

				
					if (getPropertyLov() != null && getPropertyLov().getId()>0) 
					{
						setCalculatedPrice(getPropertyLov().getPrice() * priceFactor);
					}
				} 
				else 
				{
					String formula = getValueText() == null ? "1" : getValueText();
					String calcualteMath =  "(" + formula + ") * ( " + getProperty().getPriceCalculateMath() + " )";
					ScriptEngineManager mgr = new ScriptEngineManager();
					ScriptEngine engine = mgr.getEngineByName("JavaScript");
					double priceFactor = Double.parseDouble(engine.eval(calcualteMath).toString());

					setCalculatedPrice(getProperty().getPrice() * priceFactor);
				}
			} catch (ScriptException e) {
				
			}
		}
	}

	public TProperty getProperty() {
		return property;
	}

	public void setProperty(TProperty property) {
		this.property = property;
	}

	public TPropertyLov getPropertyLov() {
		return propertyLov;
	}

	public void setPropertyLov(TPropertyLov propertyLov) {
		this.propertyLov = propertyLov;
	}

	

	public double getCalculatedPrice() {
		return calculatedPrice;
	}

	public void setCalculatedPrice(double calculatedPrice) {
		this.calculatedPrice = calculatedPrice;
	}
    
}
