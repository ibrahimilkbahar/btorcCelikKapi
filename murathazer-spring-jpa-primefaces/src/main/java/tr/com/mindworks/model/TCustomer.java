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

/**
 *
 * @author abraham
 */
@Entity
@Table(name = "t_customer")
public class TCustomer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
 
    @Column(name = "Fullname")
    private String fullname;
 
    @Column(name = "IsCorporate")
    private boolean isCorporate;
 
    @Column(name = "Description")
    private String description;
 
    @Column(name = "Phone")
    private String phone;
    
    @Column(name = "Fax")
    private String fax;
 
    @Column(name = "Email")
    private String email;
 
    @Column(name = "TaxId")
    private String taxId;
    
    @Column(name = "TaxOffice")
    private String taxOffice;
    
    @Column(name = "CitizenId")
    private String citizenId;
    
    @Column(name = "cariCode")
    private String cariCode;
    
    @Column(name = "WebUrl")
    private String webUrl;
    


    public TCustomer() {
    }

    public TCustomer(Integer id) {
        this.id = id;
    }

    public TCustomer(Integer id, boolean isCorporate, String phone) {
        this.id = id;
        this.isCorporate = isCorporate;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean getIsCorporate() {
        return isCorporate;
    }

    public void setIsCorporate(boolean isCorporate) {
        this.isCorporate = isCorporate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getTaxOffice() {
		return taxOffice;
	}

	public void setTaxOffice(String taxOffice) {
		this.taxOffice = taxOffice;
	}

	public String getCitizenId() {
		return citizenId;
	}

	public void setCitizenId(String citizenId) {
		this.citizenId = citizenId;
	}

	public String getCariCode() {
		return cariCode;
	}

	public void setCariCode(String cariCode) {
		this.cariCode = cariCode;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
    
}
