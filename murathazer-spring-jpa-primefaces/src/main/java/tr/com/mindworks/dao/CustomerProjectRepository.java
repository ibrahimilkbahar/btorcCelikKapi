package tr.com.mindworks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TCustomerProject;

@Repository
public interface CustomerProjectRepository extends JpaRepository<TCustomerProject, Integer>
{ 
	@Query("SELECT p FROM TCustomerProject p WHERE p.customer.id =:customerId")
    public List<TCustomerProject> findAllCustomerProjectByCustomerId(@Param("customerId") Integer customerId);
}
