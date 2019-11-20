package tr.com.mindworks.config;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.context.ServletContextAware;

import com.sun.faces.config.ConfigureListener;

@Configuration
public class JsfConfig extends SpringBootServletInitializer implements ServletContextAware
{
    @Configuration
    @Profile("dev")
    static class ConfigureJSFContextParameters implements ServletContextInitializer
    {
        @Override
        public void onStartup(ServletContext servletContext) throws ServletException
        {
            servletContext.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
            servletContext.setInitParameter("javax.faces.STATE_SAVING_METHOD", "server");
            servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
            servletContext.setInitParameter("facelets.DEVELOPMENT", "true");
            servletContext.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", "1");
            servletContext.setInitParameter("primefaces.THEME", "bluesky");
            servletContext.setInitParameter("primefaces.UPLOADER","commons");
        }
    }

    @Bean
    public ServletRegistrationBean facesServlet()
    {
        FacesServlet servlet = new FacesServlet();
        ServletRegistrationBean registration = new ServletRegistrationBean(servlet, "*.xhtml");
        registration.setName("FacesServlet");
        registration.setLoadOnStartup(1);
        return registration;
    }
    
    @Bean
    public ServletRegistrationBean facesServletRegistraiton() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new FacesServlet(), new String[]{"*.xhtml"});
        registration.setName("Faces Servlet");
        registration.setLoadOnStartup(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean facesUploadFilterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new FileUploadFilter(), facesServletRegistraiton());
        registrationBean.setName("PrimeFaces FileUpload Filter");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setDispatcherTypes(DispatcherType.FORWARD, DispatcherType.REQUEST);
        return registrationBean;
    }
    
    @Bean
    public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener()
    {
        return new ServletListenerRegistrationBean(new ConfigureListener());
    }

    @Override
    public void setServletContext(ServletContext servletContext)
    {
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
        servletContext.setInitParameter("primefaces.FONT_AWESOME", "true");
        servletContext.setInitParameter("javax.faces.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE", "true");
        servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
    }

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor()
    {
        return new CustomScopeRegisteringBeanFactoryPostProcessor();
    }
}
