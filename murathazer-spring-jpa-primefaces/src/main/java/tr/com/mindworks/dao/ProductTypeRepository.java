package tr.com.mindworks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TProductCaseType;
import tr.com.mindworks.model.TProductSurfaceType;
import tr.com.mindworks.model.TProductType;
import tr.com.mindworks.model.TProductWingType;

@Repository
public interface ProductTypeRepository extends JpaRepository<TProductType, Integer>
{

	 
	@Query("SELECT p FROM TProductType p order by orderby")
	List<TProductType> findAllProductTypes();


	@Query("SELECT p FROM TProductSurfaceType p order by orderby")
	List<TProductSurfaceType> findAllProductSurfaceTypes();


	@Query("SELECT p FROM TProductWingType p order by orderby")
	List<TProductWingType> findAllProductWingTypes();


	@Query("SELECT p FROM TProductCaseType p order by orderby")
	List<TProductCaseType> findAllProductCaseTypes();

}
