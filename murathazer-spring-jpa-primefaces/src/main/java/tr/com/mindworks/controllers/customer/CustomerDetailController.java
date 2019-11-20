package tr.com.mindworks.controllers.customer;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;
import tr.com.mindworks.annotations.SpringFlowScoped;
import tr.com.mindworks.controllers.BaseController;
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

@Component("customerDetailController")
@SpringFlowScoped
@SessionScope
public class CustomerDetailController extends BaseController {
	@Autowired
	private Provider<CustomerService> customerService;

	@Getter
	@Setter
	private TCustomer customer;

	@Getter
	@Setter
	private Integer isCorporate;

	@Getter
	@Setter
	private List<TContactPosition> contactPositionList;
	@Getter
	@Setter
	private List<TDistrict> districtList;
	@Getter
	@Setter
	private List<TCity> cityList;
	@Getter
	@Setter
	private List<TCountry> countryList;
	@Getter
	@Setter
	private Integer country, city, district, projectAddress, contactPosition;

	@Getter
	@Setter
	private TCustomerAddress newCustomerAddress;
	@Getter
	@Setter
	private List<TCustomerAddress> customerAddressList;

	@Getter
	@Setter
	private TCustomerContact newCustomerContact;
	@Getter
	@Setter
	private List<TCustomerContact> customerContactList;

	@Getter
	@Setter
	private TCustomerProject newCustomerProject;
	@Getter
	@Setter
	private List<TCustomerProject> customerProjectList;

	@Getter
	@Setter
	private TCustomerBankAccount newCustomerBankAccount;
	@Getter
	@Setter
	private List<TCustomerBankAccount> customerBankAccountList;

	public void initializeCreateCustomer() {
		FacesContext context = FacesContext.getCurrentInstance();
		isCorporate = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("isCorporate"));

		contactPositionList = customerService.get().findAllContactPositions();
		countryList = customerService.get().findAllCountry();
		newCustomerAddress = new TCustomerAddress();
		newCustomerContact = new TCustomerContact();
		newCustomerProject = new TCustomerProject();
		newCustomerBankAccount = new TCustomerBankAccount();

		customer = new TCustomer();
	}

	public void initializeDetailCustomer() {
		countryList = customerService.get().findAllCountry();
		contactPositionList = customerService.get().findAllContactPositions();

		FacesContext context = FacesContext.getCurrentInstance();
		String customerId = context.getExternalContext().getRequestParameterMap().get("customerId");
		if (customerId == null || "".equals(customerId)) {
			jsfMessageUtil.addInfoMessage("Müşteri bulunamadı.");
		} else {
			customer = customerService.get().findCustomerById(Integer.parseInt(customerId));
			initCustomerInformations();
		}

	}

	private void initCustomerInformations() {
		customerContactList = customerService.get().findAllCustomerContacts(customer);
		customerAddressList = customerService.get().findAllCustomerAddresses(customer);
		customerProjectList = customerService.get().findAllCustomerProjects(customer);
		customerBankAccountList = customerService.get().findallCustomerBankAccounts(customer);
		newCustomerAddress = new TCustomerAddress();
		newCustomerContact = new TCustomerContact();
		newCustomerProject = new TCustomerProject();
		newCustomerBankAccount = new TCustomerBankAccount();
		isCorporate = customer.getIsCorporate() ? 1 : 0;
	}

	 
	public String saveCustomer() {
		try {
			customer.setIsCorporate(isCorporate == 1 ? true : false);
			if (customer.getId() == null && customerService.get().validateNewCustomerSingle(customer)) {
				customerService.get().saveCustomer(customer);
				jsfMessageUtil.addInfoMessage("Müşteri Kaydedildi. :)");
				return "customerSaved";
			} else if (customer.getId() != null && customerService.get().validateCustomerSingle(customer)) {
				customerService.get().saveCustomer(customer);
				jsfMessageUtil.addInfoMessage("Müşteri Kaydedildi. :)");
				return "customerSaved";
			} else {
				throw new Exception("Müşteri Adı,Firma Adı, TCKN, Vergi No Bilgileri Tekil olmalıdır.");
			}
		} catch (Exception exception) {
			jsfMessageUtil.handleException("Müşteri Kaydedilemedi.!!!", exception);
			return null;
		}
	}
	 

	// #####CUSTOMER CONTACTS
	public void saveContact(ActionEvent actionEvent) {
		newCustomerContact.setCustomer(customer);
		newCustomerContact.setContactPosition(new TContactPosition(contactPosition));
		customerService.get().saveCustomerContact(newCustomerContact);
		newCustomerContact = new TCustomerContact();
		customerContactList = customerService.get().findAllCustomerContacts(customer);
	}

	public void resetContact() {
		newCustomerContact = new TCustomerContact();
	}

	public void deleteContact(TCustomerContact customerContact) {
		customerService.get().deleteCustomerContact(customerContact.getId());
		customerContactList = customerService.get().findAllCustomerContacts(customer);
	}
	
	public void loadContact(TCustomerContact contact) {
		newCustomerContact = contact;
		contactPosition = contact.getContactPosition().getId();
		cityList = customerService.get().findCitiesByCountryId(country);
		districtList = customerService.get().findDistrictsByCityId(city);
	}

	// #####CUSTOMER ADDRESS
	public void saveAddress(ActionEvent actionEvent) {
		newCustomerAddress.setCustomerId(customer);
		newCustomerAddress.setDistrict(new TDistrict(district));
		customerService.get().saveCustomerAddress(newCustomerAddress);

		newCustomerAddress = new TCustomerAddress();
		customerAddressList = customerService.get().findAllCustomerAddresses(customer);
	}

	public void resetAddress() {
		newCustomerAddress = new TCustomerAddress();
	}

	public void loadAddress(TCustomerAddress address) {
		newCustomerAddress = address;
		country = address.getDistrict().getCity().getCountry().getId();
		city = address.getDistrict().getCity().getId();
		district = address.getDistrict().getId();
		cityList = customerService.get().findCitiesByCountryId(country);
		districtList = customerService.get().findDistrictsByCityId(city);
	}

	public void deleteAddress(TCustomerAddress customerAddress) {
		customerService.get().deleteCustomerAddress(customerAddress.getId());
		customerAddressList = customerService.get().findAllCustomerAddresses(customer);
	}

	public void onCountryChange(final AjaxBehaviorEvent event) {
		cityList = customerService.get().findCitiesByCountryId(country);
	}

	public void onCityChange(final AjaxBehaviorEvent event) {
		districtList = customerService.get().findDistrictsByCityId(city);
	}

	// #####CUSTOMER PROJECTS
	public void saveProject(ActionEvent actionEvent) {
		newCustomerProject.setCustomer(customer);

		newCustomerProject.setProjectAddress(new TCustomerAddress(projectAddress));
		customerService.get().saveCustomerProject(newCustomerProject);

		newCustomerProject = new TCustomerProject();
		customerProjectList = customerService.get().findAllCustomerProjects(customer);
	}

	public void resetProject() {
		newCustomerProject = new TCustomerProject();
	}

	public void deleteProject(TCustomerProject customerProject) {
		customerService.get().deleteCustomerProject(customerProject.getId());
		customerProjectList = customerService.get().findAllCustomerProjects(customer);
	}

	public void loadProject(TCustomerProject project) {
		newCustomerProject = project;
		projectAddress = project.getProjectAddress().getId();

		customerAddressList = customerService.get().findAllCustomerAddresses(customer);
	}

	// #####CUSTOMER PROJECTS
	public void saveBankAccount(ActionEvent actionEvent) {
		newCustomerBankAccount.setCustomer(customer);
		customerService.get().saveCustomerBankAccount(newCustomerBankAccount);
		newCustomerBankAccount = new TCustomerBankAccount();
		customerBankAccountList = customerService.get().findallCustomerBankAccounts(customer);
	}

	public void resetBankAccount() {
		newCustomerBankAccount = new TCustomerBankAccount();
	}

	public void deleteBankAccount(TCustomerBankAccount customerBankAccount) {
		customerService.get().deleteCustomerBankAccount(customerBankAccount.getId());
		customerBankAccountList = customerService.get().findallCustomerBankAccounts(customer);
	}

	public void loadBankAccount(TCustomerBankAccount bankAccount) {
		newCustomerBankAccount = bankAccount;
	}
}
