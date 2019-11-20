package tr.com.mindworks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tr.com.mindworks.model.TOrderOfferingPropval;

@Repository
public interface OrderOfferingPropertyRepository extends JpaRepository<TOrderOfferingPropval, Integer>
{
	
	@Query("SELECT p FROM TOrderOfferingPropval p WHERE p.orderOffering.id =:orderOfferingId"
			+ " order by p.propertyValue.property.propertyGroup.orderBy,p.orderBy")
	public List<TOrderOfferingPropval> findByOrderOfferingId(@Param("orderOfferingId") Integer orderOfferingId);
	
	
	
	@Modifying
    @Transactional
	@Query("Delete from TOrderOfferingPropval p WHERE p.id =:id")
    public void deleteOrderOfferingPropValById(@Param("id") Integer id);
	
	@Query("SELECT p FROM TOrderOfferingPropval  p  WHERE p.orderOffering.offering.id = :offeringId and "
			 + " p.propertyValue.property.id =:propertyId and "
			 + " p.orderOffering.order.orderStatus.code='DRAFT' and "
			 + " p.isMondatory != :isMondatory ")
	public List<TOrderOfferingPropval> findDraftOrderOfferingPropertyBehaviour(@Param("offeringId") Integer offeringId, 
						  @Param("propertyId") Integer propertyId, 
						  @Param("isMondatory") boolean isMondatory);
	
}
