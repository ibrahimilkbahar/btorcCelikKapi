package tr.com.mindworks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TPropertyLovDef;

@Repository
public interface PropertyLovDefRepository extends JpaRepository<TPropertyLovDef, Integer>
{
	
	
}
