package tr.com.mindworks.controllers.customer;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;
import tr.com.mindworks.annotations.SpringFlowScoped;
import tr.com.mindworks.controllers.BaseController;
import tr.com.mindworks.model.TCustomer;
import tr.com.mindworks.services.CustomerService;


@Component("customerListController")
@SpringFlowScoped
@SessionScope
public class CustomerListController extends BaseController
{
    @Autowired
    private Provider<CustomerService> customerService;

    @Getter
    @Setter
    private List<TCustomer> customerList;
    @Getter
    @Setter
    private List<TCustomer> filteredCustomerList;

    @PostConstruct
    public void initCustomerList()
    {
    	customerList = customerService.get().findAll();
    }
    
    
    public void deleteCustomer(TCustomer cust) {
    	 customerService.get().deleteCustomer(cust.getId());
    	 customerList = customerService.get().findAll();
    }
}
