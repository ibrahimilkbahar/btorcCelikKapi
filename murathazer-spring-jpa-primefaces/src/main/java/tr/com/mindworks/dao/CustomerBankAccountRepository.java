package tr.com.mindworks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TCustomerBankAccount;

@Repository
public interface CustomerBankAccountRepository extends JpaRepository<TCustomerBankAccount, Integer>
{
	@Query("SELECT p FROM TCustomerBankAccount p WHERE p.customer.id =:customerId")
    public List<TCustomerBankAccount> findAllCustomerBankAccountsByCustomerId(@Param("customerId") Integer customerId);
}
