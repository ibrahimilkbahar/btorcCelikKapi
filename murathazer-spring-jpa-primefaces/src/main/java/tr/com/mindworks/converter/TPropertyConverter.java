package tr.com.mindworks.converter;

import tr.com.mindworks.model.TPropertyLov;
import tr.com.mindworks.services.OrderService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Provider;

/**
 * Created by edsoft on 02.11.2017.
 */
@FacesConverter("themeConverter")
public class TPropertyConverter implements Converter {
  
	
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (!value.equals("Seçiniz...") && value != null && value.trim().length() > 0) {
            try {
                Provider<OrderService> service = (Provider<OrderService>) context.getExternalContext().getSessionMap().get("orderService");
                return service.get().findPropertyLovById(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == "" || value == null || value instanceof Integer) {
            return null;
        } else {
            TPropertyLov lov = (TPropertyLov) value;
            return String.valueOf(lov.getId());
        }
    }
}