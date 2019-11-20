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
import javax.validation.constraints.Size;

/**
 *
 * @author abraham
 */
@Entity
@Table(name = "t_proposal_history")
public class TProposalHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
 
    @JoinColumn(name = "ProposalId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TProposal proposal;
    
    @Column(name = "operation")
    private String operation;
    
    @Column(name = "OperationInfo")
    private String operationInfo;
    
    @Size(max = 400)
    @Column(name = "OldValue")
    private String oldValue;
    
    @Size(max = 400)
    @Column(name = "NewValue")
    private String newValue;
    
    @Column(name = "UpdateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
   
    @JoinColumn(name = "UserId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TUser user;
    
    
    
    
   
    
    
    
    
    public TProposalHistory() {
    }


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public TProposal getProposal() {
		return proposal;
	}


	public void setProposal(TProposal proposal) {
		this.proposal = proposal;
	}


	public String getOperationInfo() {
		return operationInfo;
	}


	public void setOperationInfo(String operationInfo) {
		this.operationInfo = operationInfo;
	}


	public String getOldValue() {
		return oldValue;
	}


	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}


	public String getNewValue() {
		return newValue;
	}


	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}


	public Date getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public TUser getUser() {
		return user;
	}


	public void setUser(TUser user) {
		this.user = user;
	}


	public String getOperation() {
		return operation;
	}


	public void setOperation(String operation) {
		this.operation = operation;
	}

    



	 
    
}
