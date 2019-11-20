package tr.com.mindworks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.faces.config.AbstractFacesFlowConfiguration;
import org.springframework.faces.webflow.FlowFacesContextLifecycleListener;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.execution.FlowExecutionListener;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.security.SecurityFlowExecutionListener;

@Configuration
public class WebFlowConfig extends AbstractFacesFlowConfiguration
{
    @Bean
    public FlowExecutor flowExecutor()
    {
        return getFlowExecutorBuilder(flowRegistry())
                .addFlowExecutionListener(new FlowFacesContextLifecycleListener())
                .addFlowExecutionListener(new SecurityFlowExecutionListener())
                .addFlowExecutionListener(customFlowExecutionListener())
                .build();
    }

    @Bean
    public FlowDefinitionRegistry flowRegistry()
    {
        FlowDefinitionRegistry build = getFlowDefinitionRegistryBuilder(flowBuilderServices())
                .setBasePath("/WEB-INF/flows")
                .addFlowLocationPattern("/**/*-flow.xml")
                .build();
        return build;
    }

    @Bean
    public FlowBuilderServices flowBuilderServices()
    {
        return getFlowBuilderServicesBuilder().setDevelopmentMode(true).build();
    }

    @Bean
    public FlowExecutionListener customFlowExecutionListener()
    {
        return new CustomFlowExecutionListener();
    }
}