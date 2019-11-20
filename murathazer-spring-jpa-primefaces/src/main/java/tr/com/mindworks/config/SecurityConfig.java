package tr.com.mindworks.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

import tr.com.mindworks.model.TUser;
import tr.com.mindworks.util.ConnectionProvider;

import java.sql.*;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/css/**", "/images/**", "/login.xhtml", "/resources", "/fonts/**",
						"/javax.faces.resource/**", "/console/**")
				.permitAll().anyRequest().authenticated().and().sessionManagement().invalidSessionUrl("/login.xhtml")
				.and().csrf().disable().logout().logoutUrl("/logout").logoutSuccessUrl("/login.xhtml")
				.invalidateHttpSession(true).permitAll();

		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.expressionHandler(new DefaultWebSecurityExpressionHandler() {
			@Override
			protected SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, FilterInvocation fi) {
				WebSecurityExpressionRoot root = (WebSecurityExpressionRoot) super.createSecurityExpressionRoot( authentication, fi);
				return root;
			}
		});
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		List<TUser> userList = getUserList();
		for (TUser tUser : userList) {
			auth.inMemoryAuthentication().withUser(tUser.getLoginName()).password(tUser.getPassword()).roles("USER");
		}
	}

	private List<TUser> getUserList() {
		List<TUser> result = new ArrayList<TUser>();
		Statement st = null;
		ResultSet rs = null;

		try {
			System.out.println("Kullanıcılar Sisteme yükleniyor.");
			ConnectionProvider.init();

			String query = "SELECT * FROM T_User where IsActive = 1";
			st = ConnectionProvider.getConnection().createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				TUser u = new TUser();
				u.setLoginName(rs.getString("LoginName"));
				u.setPassword(rs.getString("Password"));
				result.add(u);
			}
			System.out.println("Kullanıcılar Sisteme Yükleme işlemi tamamlandı.");

		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		} finally {
			try {
				rs.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
