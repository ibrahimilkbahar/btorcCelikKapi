package tr.com.mindworks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

/**
 *
 * @author abraham
 */
@Entity
@Table(name = "t_order_status")
public class TOrderStatus implements Serializable {

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
    
    @Transient
	public static final TOrderStatus ORDER_STATUS_DRAFT = new TOrderStatus(1, "Taslak", "DRAFT");
    @Transient
	public static final TOrderStatus ORDER_STATUS_SUBMITTED = new TOrderStatus(2, "Gönderilmiş", "SUBMITTED");
    @Transient
	public static final TOrderStatus ORDER_STATUS_APPROVED = new TOrderStatus(3, "Onaylanmış", "APPROVED");
    @Transient
	public static final TOrderStatus ORDER_STATUS_COMPLETED = new TOrderStatus(4, "Tamamlanmış", "COMPLETED");
    @Transient
   	public static final TOrderStatus ORDER_STATUS_CANCELLED = new TOrderStatus(4, "İptal Edilmiş", "CANCELLED");
    
    
    public TOrderStatus() {
    }

    public TOrderStatus(Integer id) {
        this.id = id;
    }
    public TOrderStatus(Integer id,String name,String code) {
        this.id = id;
        this.name = name;
        this.code = code;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TOrderStatus)) {
            return false;
        }
        TOrderStatus other = (TOrderStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tr.com.mindworks.model.TOrderStatus[ id=" + id + " ]";
    }
    
}
