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
@Table(name = "t_proposal")
public class TProposal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
 
    @JoinColumn(name = "OrderId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TOrder order;
    
    @Column(name = "ProposalNo")
    private String proposalNo;
    
    @Size(max = 400)
    @Column(name = "Description")
    private String description;
    
    @Column(name = "CreateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    
    @Column(name = "LastUpdateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;
    
    @Column(name = "LastSendDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSendDate;
    
    @Column(name = "RevisionDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date revisionDate;
    
    @Column(name = "RevisionNo")
    private Integer revisionNo;
    
    
    public TProposal() {
    }
    public TProposal(Integer id) {
    	this.id = id;
    }
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TOrder getOrder() {
		return order;
	}

	public void setOrder(TOrder order) {
		this.order = order;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getLastSendDate() {
		return lastSendDate;
	}

	public void setLastSendDate(Date lastSendDate) {
		this.lastSendDate = lastSendDate;
	}
	public Date getRevisionDate() {
		return revisionDate;
	}
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
	public Integer getRevisionNo() {
		return revisionNo;
	}
	public void setRevisionNo(Integer revisionNo) {
		this.revisionNo = revisionNo;
	}

    



	 
    
}
