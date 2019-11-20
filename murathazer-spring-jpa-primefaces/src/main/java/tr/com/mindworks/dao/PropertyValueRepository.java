package tr.com.mindworks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TProperty;
import tr.com.mindworks.model.TPropertyGroup;
import tr.com.mindworks.model.TPropertyType;
import tr.com.mindworks.model.TPropertyValue;

@Repository
public interface PropertyValueRepository extends JpaRepository<TPropertyValue, Integer>
{
	@Query("SELECT p FROM TPropertyType p order by p.orderBy")
	public List<TPropertyType> findAllPropertyTypes();
	
	@Query("SELECT p FROM TPropertyGroup p order by p.orderBy")
	public List<TPropertyGroup> findAllPropertyGroups();
	
	
	@Query("SELECT p FROM TProperty p WHERE p.propertyGroup.id =:propertyGroupId and p.id not in ( select k.propertyValue.property from TOfferingPropval  k where k.offering.id =:offeringId)")
    public List<TProperty> findAvailablePropertiesForThatOffering(@Param("offeringId") Integer offeringId,@Param("propertyGroupId") Integer propertyGroupId);

    @Query("SELECT p.propertyValue.property FROM TOfferingPropval p WHERE p.propertyValue.property.propertyGroup.id =:propertyGroupId and p.offering.id =:offeringId order by p.orderBy")
    public List<TProperty> findPropertiesOfThatOffering(@Param("offeringId") Integer offeringId,@Param("propertyGroupId") Integer propertyGroupId);

 
 

	
	
	
	
}
