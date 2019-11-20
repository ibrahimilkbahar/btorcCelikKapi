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

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author abraham
 */
@Entity
@Table(name = "t_door_open_side")
public class TDoorOpenSide implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "Id")
    private Integer id;
 
    @Getter
    @Setter
    @Column(name = "Name")
    private String name;
 
    @Getter
    @Setter
    @Column(name = "Code")
    private String code;
    
    @Getter
    @Setter
    @Column(name = "OrderBy")
    private Integer orderBy;
    
  
    public TDoorOpenSide() {
    }

    public TDoorOpenSide(Integer id) {
        this.id = id;
    }
 
    public TDoorOpenSide(Integer id, String name, String code) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
	}

    @Transient
	public static final TDoorOpenSide UNDEFINED = new TDoorOpenSide(0, "Belirtilmemiş", "UNDEFINED");
    
    @Transient
	public static final TDoorOpenSide LEFT_SIDE = new TDoorOpenSide(1, "Sol Yöne Açılım", "LEFT_SIDE");

    @Transient
	public static final TDoorOpenSide RIGHT_SIDE = new TDoorOpenSide(2, "Sağ Yöne Açılım", "RIGHT_SIDE");
    
}
