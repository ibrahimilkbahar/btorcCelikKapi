package tr.com.mindworks.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.mindworks.dao.UserRepository;
import tr.com.mindworks.model.TScreenInfo;
import tr.com.mindworks.model.TUser;
import tr.com.mindworks.services.UserService;

/**
 * @author mhazer on 26/03/2017.
 */
@Service("userService")
public class UserServiceImpl implements UserService, Serializable
{
    @Autowired
    private UserRepository userRepository;


    @Override
    public TUser findUser(String loginName)
    {
        return this.userRepository.findByLoginName(loginName);
    }


	@Override
	public void updateUserLastLoginDate(TUser u) {
		u.setLastLogin(new Date());
		this.userRepository.save(u);
	}


	@Override
	public List<TUser> findAllUsers() {
		return userRepository.findAll();
	}


	@Override
	public void saveUser(TUser newUser) {
		userRepository.save(newUser);
	}


	@Override
	public void lockUser(Integer id) {
		TUser newUser = userRepository.findById(id);
		newUser.setIsActive(false);
		userRepository.save(newUser);
	}


	@Override
	public List<TScreenInfo> findAllScreen() {
		return userRepository.findAllScreenInfo();
	}


	@Override
	public DualListModel<TScreenInfo> getUsersPermittedPages(TUser user) {
		List<TScreenInfo> allPages = findAllScreen();
		List<TScreenInfo> source = new ArrayList<TScreenInfo>();
		List<TScreenInfo> target = new ArrayList<TScreenInfo>();
		
		String permittedPages="";
		if(user != null && user.getId()!=null) {
			TUser u = userRepository.findById(user.getId());
			permittedPages= u.getPermittedPages()!=null ? u.getPermittedPages() : "";
		}
		
		for (TScreenInfo screen : allPages) {
			if(permittedPages.contains(screen.getId().toString())) {
				target.add(screen);
			}else {
				source.add(screen);
			}
		}
		 
		
		return new DualListModel<TScreenInfo>(source, target);
	}


	@Override
	public void updateUserPassword(TUser user) {
		this.userRepository.save(user);
	}
}
