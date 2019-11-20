package tr.com.mindworks.services.security;

import tr.com.mindworks.model.TUser;

/**
 * @author mhazer on 15/08/16.
 */
public interface AuthenticationService
{
    TUser authenticate(String username, String password);
}
