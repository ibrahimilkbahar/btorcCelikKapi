package tr.com.mindworks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TProductInstanceSEQ;

@Repository
public interface ProductInstanceSEQRepository extends JpaRepository<TProductInstanceSEQ, Integer>
{
	
}
