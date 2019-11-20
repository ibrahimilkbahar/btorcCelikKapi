package tr.com.mindworks.services.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tr.com.mindworks.model.TUser;
import tr.com.mindworks.services.UserService;
import tr.com.mindworks.services.security.AuthenticationService;

/**
 * @author mhazer on 26/03/2017.
 */
@Service("authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Override
	public TUser authenticate(String loginName, String password) {
		Authentication authRequest = new UsernamePasswordAuthenticationToken(loginName, password);
		Authentication result = this.authenticationManager.authenticate(authRequest);
		TUser user = this.userService.findUser(loginName);
		if (user != null) {
			this.userService.updateUserLastLoginDate(user);
			SecurityContextHolder.getContext().setAuthentication(result);
			return user;
		} else {
			return null;
		}
	}
}
