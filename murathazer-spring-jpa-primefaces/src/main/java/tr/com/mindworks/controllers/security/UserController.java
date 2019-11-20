package tr.com.mindworks.controllers.security;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Provider;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;
import tr.com.mindworks.annotations.SpringFlowScoped;
import tr.com.mindworks.controllers.BaseController;
import tr.com.mindworks.model.TScreenInfo;
import tr.com.mindworks.model.TUser;
import tr.com.mindworks.services.UserService;
 

@Component("userController")
@SpringFlowScoped
@SessionScope
public class UserController extends BaseController {

	@Autowired
	private Provider<UserService> userService;
	
	@Getter
	@Setter
	private TUser newUser;
	
	@Getter
	@Setter
	private List<TUser> userList;
 
	@Getter
	@Setter
	private DualListModel<TScreenInfo> permittedScreenList;
	
	 

	
	@PostConstruct
	public void initUserList() {
		newUser = new TUser();
		userList = userService.get().findAllUsers();
		permittedScreenList = new DualListModel<>();
	}
 

	 

	public void lockUser(TUser user) {
		userService.get().lockUser(user.getId());
		userList = userService.get().findAllUsers();
	}
	
	public void saveUser(ActionEvent actionEvent) {
		List permittedPages = permittedScreenList.getTarget();
		StringBuilder sb = new StringBuilder();
		for (Object obj : permittedPages) {
			sb.append("#");
			sb.append(Integer.parseInt(obj.toString()));
			sb.append("#,");
		}
		newUser.setPermittedPages(sb.toString());
		
		if(!(newUser.getId()!=null && newUser.getId()>0))
			newUser.setPassword("1234");
		
		userService.get().saveUser(newUser);
		userList = userService.get().findAllUsers();
	}

	public void resetUser() {
		newUser = new TUser();
		permittedScreenList = userService.get().getUsersPermittedPages(newUser);
	}

 
	
	public void loadUser(TUser user) {
		permittedScreenList = userService.get().getUsersPermittedPages(user);
		newUser = user;
	}
	
 
}
