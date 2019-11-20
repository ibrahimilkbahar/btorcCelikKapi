package tr.com.mindworks.controllers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import tr.com.mindworks.annotations.SpringRequestScoped;
import tr.com.mindworks.controllers.BaseController;
import tr.com.mindworks.model.TUser;
import tr.com.mindworks.model.Visit;
import tr.com.mindworks.services.security.AuthenticationService;
import tr.com.mindworks.util.JSFUtil;

/**
 * @author mhazer on 15/08/16.
 */
@Component("authenticationController")
@SpringRequestScoped
public class AuthenticationController extends BaseController
{
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Autowired
    private Visit visit;

    @Autowired
    private AuthenticationService authenticationService;

    public void init()
    {
        log.debug("authentication controller init");
    }

    public void login()
    {
        try
        {
            log.info("kullanici adi ({}) ve sifre (****) kontrol ediliyor {}", this.username);
            TUser user = this.authenticationService.authenticate(this.username, this.password);
            if(user !=null) {
            	log.info("kullanici adi ve sifre dogrulandi {}", this.username);
            	this.visit.setUser(user);
            	JSFUtil.redirect("/swf/adminPage");
            }else {
            	String s = "kullanici ("+ this.username +") sisteme girisi basarisiz oldu";
            	throw new Exception(s);
            }
        }
        catch (Exception exception)
        {
            log.info("kullanici ({}) sisteme girisi basarisiz oldu", this.username);
            jsfMessageUtil.handleException("Kullanıcı adı ve şifre doğrulanamadı", exception);
        }
    }
}
