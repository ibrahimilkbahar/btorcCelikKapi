package tr.com.mindworks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tr.com.mindworks.model.TPriceType;
import tr.com.mindworks.model.TProperty;
import tr.com.mindworks.model.TPropertyGroup;
import tr.com.mindworks.model.TPropertyType;

@Repository
public interface PropertyRepository extends JpaRepository<TProperty, Integer>
{
	@Query("SELECT p FROM TPropertyType p order by p.orderBy")
	public List<TPropertyType> findAllPropertyTypes();
	
	@Query("SELECT p FROM TPropertyGroup p order by p.orderBy")
	public List<TPropertyGroup> findAllPropertyGroups();
	
	@Query("SELECT p FROM TPriceType p order by p.orderBy")
	public List<TPriceType> findAllPriceTypes();
	
	
	@Query("SELECT p FROM TProperty p WHERE p.propertyGroup.id =:propertyGroupId and p.id not in ( select k.propertyValue.property from TOfferingPropval  k where k.offering.id =:offeringId)")
    public List<TProperty> findAvailablePropertiesForThatOffering(@Param("offeringId") Integer offeringId,@Param("propertyGroupId") Integer propertyGroupId);

    @Query("SELECT p.propertyValue.property FROM TOfferingPropval p WHERE p.propertyValue.property.propertyGroup.id =:propertyGroupId and p.offering.id =:offeringId order by p.orderBy")
    public List<TProperty> findPropertiesOfThatOffering(@Param("offeringId") Integer offeringId,@Param("propertyGroupId") Integer propertyGroupId);

    @Query("SELECT p FROM TProperty p WHERE p.code =:code")
	public TProperty findByCode(@Param("code") String code);

	@Modifying
    @Transactional
	@Query("Delete from TProperty p WHERE p.id =:propertyId")
	public void deleteProperty(@Param("propertyId") Integer propertyId);
	
	@Modifying
    @Transactional
	@Query("Delete from TPropertyValue p WHERE p.property.id =:propertyId")
	public void deletePropertyValue(@Param("propertyId") Integer propertyId);
}
