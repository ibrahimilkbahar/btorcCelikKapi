package tr.com.mindworks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tr.com.mindworks.model.TCity;
import tr.com.mindworks.model.TCountry;
import tr.com.mindworks.model.TCustomer;
import tr.com.mindworks.model.TDistrict;

@Repository
public interface CustomerRepository extends JpaRepository<TCustomer, Integer>
{
	@Query("SELECT p FROM TCustomer p")
    public TCustomer findAllCustomer();
	
	@Query("SELECT p FROM TCustomer p WHERE p.fullname like %:fullname%")
    public List<TCustomer> findByFullname(@Param("fullname") String fullname);
	
 
	
	
	@Query("SELECT count(p) FROM TCustomer p WHERE p.fullname = :fullname")
    public Integer findCustomerCountByFullname(@Param("fullname") String fullname);
	
	@Query("SELECT count(p) FROM TCustomer p WHERE p.citizenId = :citizenId")
    public Integer findCustomerCountByCitizenId(@Param("citizenId") String citizenId);
	
	@Query("SELECT count(p) FROM TCustomer p WHERE p.taxId = :taxId")
    public Integer findCustomerCountByTaxId(@Param("taxId") String taxId);
	
	
	@Query("SELECT count(p) FROM TCustomer p WHERE p.fullname = :fullname and p.id <> :Id")
    public Integer findCustomerCountByFullname(@Param("fullname") String fullname,@Param("Id") Integer Id);
	
	@Query("SELECT count(p) FROM TCustomer p WHERE p.citizenId = :citizenId and p.id <> :Id")
    public Integer findCustomerCountByCitizenId(@Param("citizenId") String citizenId,@Param("Id") Integer Id);
	
	@Query("SELECT count(p) FROM TCustomer p WHERE p.taxId = :taxId and p.id <> :Id")
    public Integer findCustomerCountByTaxId(@Param("taxId") String taxId,@Param("Id") Integer Id);
	
	
	@Query("SELECT p FROM TCountry p")
    public List<TCountry> findAllCountry();
	
	@Query("SELECT p FROM TCity p WHERE p.country.id =:countryId")
    public List<TCity> findCityByCountryId(@Param("countryId") Integer countryId);
	
	@Query("SELECT p FROM TDistrict p WHERE p.city.id =:cityId")
    public List<TDistrict> findDistrictByCityId(@Param("cityId") Integer cityId);
}
