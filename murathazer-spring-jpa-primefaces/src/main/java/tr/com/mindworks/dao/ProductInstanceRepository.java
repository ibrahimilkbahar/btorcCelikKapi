package tr.com.mindworks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TProductInstance;

@Repository
public interface ProductInstanceRepository extends JpaRepository<TProductInstance, Integer>
{
 
	@Query("SELECT p FROM TProductInstance p WHERE p.orderOffering.id =:orderOfferingId order by p.orderBy")
	List<TProductInstance> findAllByOrderOfferingId(@Param("orderOfferingId") Integer orderOfferingId);

}
