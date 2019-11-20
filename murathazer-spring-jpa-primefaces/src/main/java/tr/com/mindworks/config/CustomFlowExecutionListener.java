package tr.com.mindworks.config;

import org.springframework.webflow.context.servlet.ServletExternalContext;
import org.springframework.webflow.core.collection.AttributeMap;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.core.collection.SharedAttributeMap;
import org.springframework.webflow.execution.FlowExecutionListenerAdapter;
import org.springframework.webflow.execution.FlowSession;
import org.springframework.webflow.execution.RequestContext;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author mhazer on 22/12/2016.
 */
public class CustomFlowExecutionListener extends FlowExecutionListenerAdapter
{

    private static final String SESSION_MAP_KEY = CustomFlowExecutionListener.class.getName() + ".SESSION_KEY";

    private static final String EXPOSED_OUTPUT_ID = "_outputId";

    @Override
    public void sessionStarting(RequestContext context, FlowSession session, MutableAttributeMap input)
    {
        if (hasExposedOutput(context) && session.isRoot())
        {
            exposePreviousFlowOutput(context, input);
        }
    }

    @Override
    public void sessionEnded(RequestContext context, FlowSession session, String outcome, AttributeMap output)
    {
        if (shouldExposeOutput((ServletExternalContext) context.getExternalContext()) && session.isRoot())
        {
            String exposedOutputId = exposeOutput(output, context.getExternalContext().getSessionMap());
            addIdentificationParameter((ServletExternalContext) context.getExternalContext(), exposedOutputId);
        }
    }

    private boolean hasExposedOutput(RequestContext context)
    {
        return context.getExternalContext().getRequestParameterMap().contains(EXPOSED_OUTPUT_ID);
    }

    private void exposePreviousFlowOutput(RequestContext context, MutableAttributeMap input)
    {
        String exposedOutputId = context.getExternalContext().getRequestParameterMap().get(EXPOSED_OUTPUT_ID);
        SharedAttributeMap session = context.getExternalContext().getSessionMap();

        input.putAll(retrieveOutputFromSession(session, exposedOutputId));
    }

    @SuppressWarnings("unchecked")
    private AttributeMap retrieveOutputFromSession(SharedAttributeMap session, String exposedOutputId)
    {
        synchronized (session.getMutex())
        {
//            checkState(session.contains(SESSION_MAP_KEY), "no exposed output");

            HashMap<String, AttributeMap> outputMap = (HashMap) session.get(SESSION_MAP_KEY);

//            checkState(outputMap.containsKey(exposedOutputId), "no exposed output under [" + exposedOutputId + "]");

            return outputMap.remove(exposedOutputId);
        }
    }

    private boolean shouldExposeOutput(ServletExternalContext context)
    {
        return context.getFlowDefinitionRedirectRequested();
    }

    @SuppressWarnings("unchecked")
    private String exposeOutput(AttributeMap output, SharedAttributeMap session)
    {
        synchronized (session.getMutex())
        {
            if (!session.contains(SESSION_MAP_KEY))
            {
                session.put(SESSION_MAP_KEY, new HashMap<String, AttributeMap>());
            }

            String outputId = UUID.randomUUID().toString();

            ((HashMap) session.get(SESSION_MAP_KEY)).put(outputId, output);

            return outputId;
        }
    }

    private void addIdentificationParameter(ServletExternalContext servletContext, String id)
    {
        servletContext.getFlowRedirectFlowInput().put(EXPOSED_OUTPUT_ID, id);
    }
}
