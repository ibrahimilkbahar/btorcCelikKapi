package tr.com.mindworks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TOrderOffering;
import tr.com.mindworks.model.TPropertyValue;

@Repository
public interface OrderOfferingRepository extends JpaRepository<TOrderOffering, Integer>
{
	@Query("SELECT p FROM TOrderOffering p WHERE p.order.id =:orderId")
	public List<TOrderOffering> findOrderOfferingsByOrderId(@Param("orderId") Integer orderId);

	@Query("SELECT p.propertyValue FROM TOrderOfferingPropval p WHERE p.orderOffering.id =:orderOfferingId and p.propertyValue.property.code=:propCode")
	public TPropertyValue findOrderOfferingPropertyByCode(@Param("orderOfferingId") Integer orderOfferingId,@Param("propCode") String propCode);
}
