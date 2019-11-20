package tr.com.mindworks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TDimension;

@Repository
public interface DimensionRepository extends JpaRepository<TDimension, Integer>
{

	@Query("SELECT p FROM TDimension p WHERE p.productType.id =:productTypeId and "
										 + " p.productWingType.id =:productWingTypeId "
										 + " order by p.id")
    public List<TDimension> findAllDimensionsBy_Type_Wing(@Param("productTypeId") Integer productTypeId,
												          @Param("productWingTypeId") Integer productWingTypeId);
	
 


	
}
