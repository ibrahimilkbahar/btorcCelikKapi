/*
 * To change this license header, choose License Headers in BankAccount Properties.
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
@Table(name = "t_customer_bank_account")
public class TCustomerBankAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
 
    @Column(name = "BankAccountName")
    private String bankAccountName;
 
    @Column(name = "Description")
    private String description;
 
    @Column(name = "IBAN")
    private String IBAN;
    
    @Column(name = "AccountOwnerFullname")
    private String accountOwnerFullname;
    
    @Column(name = "BankName")
    private String bankName;
    
    @Column(name = "BranchCode")
    private String branchCode;
    
    @Column(name = "AccountNumber")
    private String accountNumber;

    @JoinColumn(name = "CustomerId", referencedColumnName = "Id")
    @ManyToOne(fetch = FetchType.EAGER)
    private TCustomer customer;
    

    public TCustomerBankAccount() {
    	
    }

    public TCustomerBankAccount(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(TCustomer customer) {
        this.customer = customer;
    }

	public String getIBAN() {
		return IBAN;
	}

	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}

	public String getAccountOwnerFullname() {
		return accountOwnerFullname;
	}

	public void setAccountOwnerFullname(String accountOwnerFullname) {
		this.accountOwnerFullname = accountOwnerFullname;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
}
