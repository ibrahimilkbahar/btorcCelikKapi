package tr.com.mindworks.util;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author mhazer
 */
public class JSFUtil implements Serializable
{
    public static <T> T resolveExpression(String expression, Class<T> clazz)
    {
        FacesContext ctx = getFacesContext();
        return ctx.getApplication().evaluateExpressionGet(ctx, expression, clazz);
    }

    public static void redirect(String page) throws IOException
    {
        FacesContext ctx = getFacesContext();
        ExternalContext externalContext = ctx.getExternalContext();
        externalContext.redirect(externalContext.getRequestContextPath().concat(page));
    }

    private static FacesContext getFacesContext()
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Preconditions.checkNotNull(ctx, "can not access JSF instance, please check JSF configurations");
        return ctx;
    }

    public static void putToSessionContext(String key, Object object) throws IOException
    {
        FacesContext ctx = getFacesContext();
        ctx.getExternalContext().getSessionMap().put(key, object);
    }

    public static void putToRequestContext(String key, Object object)
    {
        FacesContext ctx = getFacesContext();
        ctx.getExternalContext().getRequestMap().put(key, object);
    }

    public static String getCurrentViewId()
    {

        FacesContext ctx = FacesContext.getCurrentInstance();
        if (ctx != null && ctx.getViewRoot() != null)
        {
            return ctx.getViewRoot().getViewId();
        }
        else
        {
            return StringUtils.EMPTY;
        }
    }
}
