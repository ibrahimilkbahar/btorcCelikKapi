package tr.com.mindworks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TCity;
import tr.com.mindworks.model.TCountry;
import tr.com.mindworks.model.TCustomerAddress;
import tr.com.mindworks.model.TDistrict;

@Repository
public interface CustomerAddressRepository extends JpaRepository<TCustomerAddress, Integer>
{ 
	@Query("SELECT p FROM TCustomerAddress p WHERE p.customer.id =:customerId")
    public List<TCustomerAddress> findAllCustomerAddressByCustomerId(@Param("customerId") Integer customerId);
	
	@Query("SELECT p FROM TCountry p")
    public List<TCountry> findAllCountry();
	
	@Query("SELECT p FROM TCity p WHERE p.country.id =:countryId")
    public List<TCity> findCityByCountryId(@Param("countryId") Integer countryId);
	
	@Query("SELECT p FROM TDistrict p WHERE p.city.id =:cityId")
    public List<TDistrict> findDistrictByCityId(@Param("cityId") Integer cityId);
}
