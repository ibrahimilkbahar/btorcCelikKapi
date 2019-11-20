package tr.com.mindworks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TCity;
import tr.com.mindworks.model.TContactPosition;
import tr.com.mindworks.model.TCountry;
import tr.com.mindworks.model.TCustomer;
import tr.com.mindworks.model.TCustomerContact;
import tr.com.mindworks.model.TDistrict;

@Repository
public interface CustomerContactRepository extends JpaRepository<TCustomerContact, Integer>
{
	@Query("SELECT p FROM TCustomer p")
    public TCustomer findAllCustomer();
	
	@Query("SELECT p FROM TCustomer p WHERE p.fullname like %:fullname%")
    public List<TCustomer> findByFullname(@Param("fullname") String fullname);
	
	
	@Query("SELECT p FROM TCustomerContact p WHERE p.customer.id =:customerId")
    public List<TCustomerContact> findAllCustomerContactsByCustomerId(@Param("customerId") Integer customerId);
	
	@Query("SELECT p FROM TCountry p")
    public List<TCountry> findAllCountry();
	
	@Query("SELECT p FROM TCity p WHERE p.country.id =:countryId")
    public List<TCity> findCityByCountryId(@Param("countryId") Integer countryId);
	
	@Query("SELECT p FROM TDistrict p WHERE p.city.id =:cityId")
    public List<TDistrict> findDistrictByCityId(@Param("cityId") Integer cityId);

	
	@Query("SELECT p FROM TContactPosition p")
    public List<TContactPosition> findAllContactPositions();
}
