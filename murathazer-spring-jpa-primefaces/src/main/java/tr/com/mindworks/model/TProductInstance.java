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

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author abraham
 */
@Entity
@Table(name = "t_product_instance")
public class TProductInstance implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
 
    @Getter
    @Setter
    @Column(name = "ProductNo")
    private String productNo;
   
    @Getter
    @Setter
    @JoinColumn(name = "OrderOfferingId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TOrderOffering orderOffering;
   
    @Getter
    @Setter
    @JoinColumn(name = "ProductInstanceStatusId", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TProductInstanceStatus productInstanceStatus;
    
    @Getter
    @Setter
    @JoinColumn(name = "DoorOpenSideId", referencedColumnName = "Id")
    @ManyToOne(fetch = FetchType.EAGER)
    private TDoorOpenSide doorOpenSide;
    
    @Getter
    @Setter
    @Column(name = "OrderBy")
    private Integer orderBy;

 
    
    
    public TProductInstance() {
    }

    public TProductInstance(Integer id) {
        this.id = id;
    }

    

    
}
