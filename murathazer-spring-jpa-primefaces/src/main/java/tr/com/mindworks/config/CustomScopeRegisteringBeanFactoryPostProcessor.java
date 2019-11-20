package tr.com.mindworks.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.webflow.scope.FlowScope;

/**
 * @author mhazer on 10/08/16.
 */
public class CustomScopeRegisteringBeanFactoryPostProcessor implements BeanFactoryPostProcessor
{
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException
    {
        beanFactory.registerScope(ScopeName.VIEW, new ViewScope());
        beanFactory.registerScope(ScopeName.FLOW, new FlowScope());
    }
}
