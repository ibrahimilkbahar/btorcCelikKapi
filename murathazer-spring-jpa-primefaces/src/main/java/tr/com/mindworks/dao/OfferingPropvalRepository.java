package tr.com.mindworks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tr.com.mindworks.model.TOfferingPropval;
import tr.com.mindworks.model.TProperty;

@Repository
public interface OfferingPropvalRepository extends JpaRepository<TOfferingPropval, Integer> {

	@Query("SELECT p.propertyValue.property FROM TOfferingPropval p WHERE p.propertyValue.property.propertyGroup.id =:propertyGroupId and p.offering.id =:offeringId order by p.orderBy")
	public List<TProperty> findAllPropertiesOfOffering(@Param("offeringId") Integer offeringId, @Param("propertyGroupId") Integer propertyGroupId);

	@Query("SELECT p FROM TOfferingPropval p WHERE p.propertyValue.property.propertyGroup.id =:propertyGroupId and p.offering.id =:offeringId order by p.orderBy")
	public List<TOfferingPropval> findAllOfferingPropvalOfOffering(@Param("offeringId") Integer offeringId, @Param("propertyGroupId") Integer propertyGroupId);

	
	@Query("SELECT p FROM TOfferingPropval p WHERE p.offering.id =:offeringId order by p.propertyValue.property.propertyGroup.orderBy,p.orderBy")
	public List<TOfferingPropval> findAllPropertiesOfOffering(@Param("offeringId") Integer offeringId);

	
	@Modifying
    @Transactional
	@Query("Delete from TOfferingPropval p WHERE p.id in "
			+ "(select p2.id from TOfferingPropval p2 where p2.offering.id =:offeringId and p2.propertyValue.property.id =:propertyId)")
    public void deleteByOfferingIdAndPropertyId2(@Param("offeringId") Integer offeringId, @Param("propertyId") Integer propertyId);
	
	@Modifying
    @Transactional
    @Query(value = "delete op  from t_offering_propval op\n" + 
    				"JOIN t_offering o on op.offeringId = o.ID\n" + 
    				"JOIN t_property_value pv on op.propertyValueId = pv.ID\n" + 
    				"JOIN t_property p on pv.propertyId = p.ID\n" + 
    				"where o.ID = :offeringId and p.ID = :propertyId", nativeQuery = true)
    public void deleteByOfferingIdAndPropertyId(@Param("offeringId") Integer offeringId, @Param("propertyId") Integer propertyId);
	
	
	@Query("SELECT p FROM TOfferingPropval p WHERE p.propertyValue.property.code =:propertyCode and p.offering.id =:offeringId")
	public TOfferingPropval findByOfferingIdAndPropertyCode(@Param("offeringId") Integer offeringId, @Param("propertyCode")String propertyCode);

	
}
