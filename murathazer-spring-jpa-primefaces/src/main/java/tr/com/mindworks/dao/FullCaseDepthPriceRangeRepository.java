package tr.com.mindworks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TFullCaseDepthPriceRange;

@Repository
public interface FullCaseDepthPriceRangeRepository extends JpaRepository<TFullCaseDepthPriceRange, Integer>
{
	
	@Query("SELECT p FROM TFullCaseDepthPriceRange p WHERE p.productType.id =:productTypeId and "
			 + " p.productWingType.id =:productWingTypeId  "
			 + " order by p.orderBy")
	public List<TFullCaseDepthPriceRange> findAllFullCaseDepthPriceRanges(@Param("productTypeId") Integer productTypeId,
					    		   										  @Param("productWingTypeId") Integer productWingTypeId);

	
	@Query("SELECT p.price FROM TFullCaseDepthPriceRange p WHERE (p.depthMin <= :enteredDepth and p.depthMax > :enteredDepth) and "
															   + "p.productType.id =:productTypeId and "
															   + "p.productWingType.id =:productWingTypeId  ")
	public double findCloserDepthForFullCase(@Param("enteredDepth") Integer enteredDepth,
											 @Param("productTypeId") Integer productTypeId,
											 @Param("productWingTypeId") Integer productWingTypeId);
	
}
