package tr.com.mindworks.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.faces.webflow.JsfFlowHandlerAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;

@Configuration
public class WebMvcConfig
{
    @Autowired
    private WebFlowConfig webFlowConfig;

    @Bean
    public FlowHandlerMapping flowHandlerMapping()
    {
        FlowHandlerMapping mapping = new FlowHandlerMapping();
        mapping.setOrder(-1);
        mapping.setFlowRegistry(this.webFlowConfig.flowRegistry());
        return mapping;
    }

    @Bean
    public FlowHandlerAdapter flowHandlerAdapter()
    {
        JsfFlowHandlerAdapter adapter = new JsfFlowHandlerAdapter();
        adapter.setFlowExecutor(this.webFlowConfig.flowExecutor());
        adapter.setSaveOutputToFlashScopeOnRedirect(true);
        return adapter;
    }

    @Bean
    public DispatcherServlet dispatcherServlet()
    {
        return new DispatcherServlet();
    }

    @Bean
    public ServletRegistrationBean dispatcherServletRegistration()
    {
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet(), "/swf/*");
        registration.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
        return registration;
    }

    @Bean
    public SimpleControllerHandlerAdapter simpleControllerHandlerAdapter()
    {
        return new SimpleControllerHandlerAdapter();
    }

    @Bean
    public ErrorPageRegistrar errorPageRegistrar()
    {
        return new MyErrorPageRegistrar();
    }

    private static class MyErrorPageRegistrar implements ErrorPageRegistrar
    {
        @Override
        public void registerErrorPages(ErrorPageRegistry registry)
        {
            registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.xhtml"));
            registry.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/access.xhtml"));
            registry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error.xhtml"));
            registry.addErrorPages(new ErrorPage(Exception.class, "/error.xhtml"));
        }

    }
}
