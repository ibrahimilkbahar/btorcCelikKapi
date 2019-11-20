package tr.com.mindworks.services;

import java.util.List;

import org.primefaces.model.DualListModel;

import tr.com.mindworks.model.TScreenInfo;
import tr.com.mindworks.model.TUser;

/**
 * @author mhazer on 26/03/2017.
 */
public interface UserService
{
    TUser findUser(String loginName);

	void updateUserLastLoginDate(TUser u);

	List<TUser> findAllUsers();

	void saveUser(TUser newUser);

	void lockUser(Integer id);
	
	List<TScreenInfo> findAllScreen();

	DualListModel<TScreenInfo> getUsersPermittedPages(TUser user);

	void updateUserPassword(TUser user);

}
