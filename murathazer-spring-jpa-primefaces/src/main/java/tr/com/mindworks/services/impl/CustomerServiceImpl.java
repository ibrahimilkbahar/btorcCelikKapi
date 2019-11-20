package tr.com.mindworks.services.impl;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.mindworks.dao.CustomerAddressRepository;
import tr.com.mindworks.dao.CustomerBankAccountRepository;
import tr.com.mindworks.dao.CustomerContactRepository;
import tr.com.mindworks.dao.CustomerProjectRepository;
import tr.com.mindworks.dao.CustomerRepository;
import tr.com.mindworks.model.TCity;
import tr.com.mindworks.model.TContactPosition;
import tr.com.mindworks.model.TCountry;
import tr.com.mindworks.model.TCustomer;
import tr.com.mindworks.model.TCustomerAddress;
import tr.com.mindworks.model.TCustomerBankAccount;
import tr.com.mindworks.model.TCustomerContact;
import tr.com.mindworks.model.TCustomerProject;
import tr.com.mindworks.model.TDistrict;
import tr.com.mindworks.services.CustomerService;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService, Serializable {
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerContactRepository customerContactRepository;
	
	@Autowired
	private CustomerAddressRepository customerAddressRepository;
	
	@Autowired
	private CustomerProjectRepository customerProjectRepository;
	
	@Autowired
	private CustomerBankAccountRepository customerBankAccountRepository;
	
	//#########################Customer####################
	@Override
	public List<TCustomer> findCustomerByName(String name) {
		return customerRepository.findByFullname(name);
	}
	
	@Override
	@Transactional
	public void saveCustomer(TCustomer customer) {
		
		customerRepository.saveAndFlush(customer);
	}

	@Override
	public List<TCustomer> findAll() {
		return customerRepository.findAll();
	}

	@Override
	public TCustomer findCustomerById(Integer customerId) {
		return customerRepository.findOne(customerId);
	}
	

	@Override
	public void deleteCustomer(Integer customerId) {
		customerRepository.delete(customerId);		
	}
	
	
	
	//#########################Customer Address####################
	@Override
	public List<TCountry> findAllCountry() {
		return customerAddressRepository.findAllCountry();
	}

	@Override
	public List<TCity> findCitiesByCountryId(Integer countryId) {
		return customerAddressRepository.findCityByCountryId(countryId);
	}

	@Override
	public List<TDistrict> findDistrictsByCityId(Integer cityId) {
		return customerAddressRepository.findDistrictByCityId(cityId);
	}

	@Override
	public List<TCustomerAddress> findAllCustomerAddresses(TCustomer customer) {
		return customerAddressRepository.findAllCustomerAddressByCustomerId(customer.getId());
	}

	@Override
	public void saveCustomerAddress(TCustomerAddress newCustomerAddress) {
		customerAddressRepository.save(newCustomerAddress);
		
	}

	@Override
	public void deleteCustomerAddress(Integer customerAddressId) {
		customerAddressRepository.delete(customerAddressId);
	}

	@Override
	public TCustomerAddress findCustomerAddressById(Integer addressId) {
		return customerAddressRepository.findOne(addressId);
	}



	//#########################Customer Contacts####################
	@Override
	public void saveCustomerContact(TCustomerContact newCustomerContact) {
		customerContactRepository.save(newCustomerContact);
	}

	@Override
	public List<TCustomerContact> findAllCustomerContacts(TCustomer customer) {
		return customerContactRepository.findAllCustomerContactsByCustomerId(customer.getId());
	}
	
	@Override
	public void deleteCustomerContact(Integer customerContactId) {
		customerContactRepository.delete(customerContactId);
	}

	@Override
	public TCustomerContact findCustomerContactById(Integer contactId) {
		return customerContactRepository.findOne(contactId);
	}
	
	@Override
	public List<TContactPosition> findAllContactPositions() {
		return customerContactRepository.findAllContactPositions();
	}
	
	//#########################Customer Projects####################
	@Override
	public void saveCustomerProject(TCustomerProject newCustomerProject) {
		customerProjectRepository.save(newCustomerProject);
	}

	@Override
	public void deleteCustomerProject(Integer customerProjectId) {
		customerProjectRepository.delete(customerProjectId);
	}

	@Override
	public List<TCustomerProject> findAllCustomerProjects(TCustomer customer) {
		return customerProjectRepository.findAllCustomerProjectByCustomerId(customer.getId());
	}

	@Override
	public TCustomerProject findCustomerProjectById(Integer projectId) {
		return customerProjectRepository.findOne(projectId);
	}

	
	//#####################Customer Bank Account #####################
	@Override
	public void saveCustomerBankAccount(TCustomerBankAccount newCustomerBankAccount) {
		customerBankAccountRepository.save(newCustomerBankAccount);
	}

	@Override
	public List<TCustomerBankAccount> findallCustomerBankAccounts(TCustomer customer) {
		return customerBankAccountRepository.findAllCustomerBankAccountsByCustomerId(customer.getId());
	}

	@Override
	public void deleteCustomerBankAccount(Integer customerBankAccountId) {
		customerBankAccountRepository.delete(customerBankAccountId);
	}

	@Override
	public boolean validateNewCustomerSingle(TCustomer customer) {
		Integer c1 = 0, c2 = 0, c3 = 0;
		if (customer.getFullname() != null && !customer.getFullname().isEmpty())
			c1 = customerRepository.findCustomerCountByFullname(customer.getFullname());
		
		if (customer.getCitizenId() != null && !customer.getCitizenId().isEmpty())
			c2 = customerRepository.findCustomerCountByCitizenId(customer.getCitizenId());
		
		if (customer.getTaxId() != null && !customer.getTaxId().isEmpty())
			c3 = customerRepository.findCustomerCountByTaxId(customer.getTaxId());

		if (c1 > 0 || c2 > 0 || c3 > 0)
			return false;
		else
			return true;
	}
	
	@Override
	public boolean validateCustomerSingle(TCustomer customer) {
		Integer c1 = 0, c2 = 0, c3 = 0;
		if (customer.getFullname() != null && !customer.getFullname().isEmpty())
			c1 = customerRepository.findCustomerCountByFullname(customer.getFullname(), customer.getId());

		if (customer.getCitizenId() != null && !customer.getCitizenId().isEmpty())
			c2 = customerRepository.findCustomerCountByCitizenId(customer.getCitizenId(), customer.getId());

		if (customer.getTaxId() != null && !customer.getTaxId().isEmpty())
			c3 = customerRepository.findCustomerCountByTaxId(customer.getTaxId(), customer.getId());

		if (c1 > 0 || c2 > 0 || c3 > 0)
			return false;
		else
			return true;
	}
	

}
