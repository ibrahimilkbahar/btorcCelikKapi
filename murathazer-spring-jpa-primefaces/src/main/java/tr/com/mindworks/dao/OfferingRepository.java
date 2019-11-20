package tr.com.mindworks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TOffering;

@Repository
public interface OfferingRepository extends JpaRepository<TOffering, Integer>
{
	@Query("SELECT p FROM TOffering p WHERE p.productType.id =:productTypeId "
			+ " and p.productSurfaceType.id =:productSurfaceTypeId "
			+ " and p.productWingType.id =:productWingTypeId "
			+ " and p.isSaleable = 1")
    public List<TOffering> findOfferings(@Param("productTypeId") Integer productTypeId,
    									 @Param("productSurfaceTypeId") Integer productSurfaceTypeId,
    									 @Param("productWingTypeId") Integer productWingTypeId);

	

	
}
