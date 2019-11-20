package tr.com.mindworks.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * @author mhazer on 07/05/14.
 */
@Component("jsfMessageUtil")
public class JSFMessageUtil implements Serializable
{
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public void addMessage(FacesMessage.Severity severity, String summary, String detail)
    {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void addInfoMessage(String summary)
    {
        addMessage(FacesMessage.SEVERITY_INFO, summary, summary);
    }

    public void addWarningMessage(String summary)
    {
        addMessage(FacesMessage.SEVERITY_WARN, summary, summary);
    }

    public void addErrorMessage(String summary)
    {
        addMessage(FacesMessage.SEVERITY_ERROR, summary, summary);
    }

    public void handleException(String premiseErrorMessage, Throwable exception)
    {
        StringBuilder wholeExceptionMessage = new StringBuilder(premiseErrorMessage);
        String exceptionMessage = exception.getMessage();
        boolean isErrorMessageNotBlankAndNotEqualsToNull = StringUtils.isNotBlank(premiseErrorMessage) && null != exceptionMessage;
        if (isErrorMessageNotBlankAndNotEqualsToNull)
        {
            wholeExceptionMessage.append(" (");
        }
        wholeExceptionMessage.append(exceptionMessage == null ? "" : exceptionMessage);
        if (isErrorMessageNotBlankAndNotEqualsToNull)
        {
            wholeExceptionMessage.append(")");
        }
        logger.error("hata oluştu {}-{}", wholeExceptionMessage.toString(), exception.getMessage());
        logger.error("cause:", exception.getCause());
        addMessage(FacesMessage.SEVERITY_ERROR, wholeExceptionMessage.toString(), null);
    }
}
