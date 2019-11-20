/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.mindworks.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author abraham
 */
@Entity
@Table(name = "t_district")
@NamedQueries({
    @NamedQuery(name = "TDistrict.findAll", query = "SELECT t FROM TDistrict t")})
public class TDistrict implements Serializable {

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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "district", fetch = FetchType.LAZY)
    private List<TCustomerAddress> tCustomerAddressList;
    
    @JoinColumn(name = "CityId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TCity city;

    public TDistrict() {
    }

    public TDistrict(Integer id) {
        this.id = id;
    }

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

    public List<TCustomerAddress> getTCustomerAddressList() {
        return tCustomerAddressList;
    }

    public void setTCustomerAddressList(List<TCustomerAddress> tCustomerAddressList) {
        this.tCustomerAddressList = tCustomerAddressList;
    }

    public TCity getCity() {
        return city;
    }

    public void setCity(TCity city) {
        this.city = city;
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
        if (!(object instanceof TDistrict)) {
            return false;
        }
        TDistrict other = (TDistrict) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tr.com.mindworks.model.TDistrict[ id=" + id + " ]";
    }
    
}
