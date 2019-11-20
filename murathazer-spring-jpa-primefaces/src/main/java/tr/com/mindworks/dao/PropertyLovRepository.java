package tr.com.mindworks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tr.com.mindworks.model.TPropertyLov;

@Repository
public interface PropertyLovRepository extends JpaRepository<TPropertyLov, Integer>
{
	@Query("SELECT p FROM TPropertyLov p WHERE p.propertyLovDef.id =:propertyLovDefId order by p.orderBy")
	List<TPropertyLov> findAllPropertyLovByDefId(@Param("propertyLovDefId") Integer propertyLovDefId);
	
	
	@Modifying
    @Transactional
	@Query("Delete from TPropertyLov p WHERE p.propertyLovDef.id =:propertyLovDefId")
    public void deletePropertyLovByDefId(@Param("propertyLovDefId") Integer propertyLovDefId);

	@Modifying
    @Transactional
	@Query("Delete from TPropertyLov p WHERE p.id =:Id")
    public void deletePropertyLovById(@Param("Id") Integer Id);
 
	
}
