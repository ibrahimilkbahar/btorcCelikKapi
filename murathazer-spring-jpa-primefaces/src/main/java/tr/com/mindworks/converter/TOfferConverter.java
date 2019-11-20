package tr.com.mindworks.converter;

import tr.com.mindworks.model.TOffering;
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
@FacesConverter("offerConverter")
public class TOfferConverter implements Converter {
  
	
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != "" && value != null && value.trim().length() > 0) {
            try {
                Provider<OrderService> service = (Provider<OrderService>) context.getExternalContext().getSessionMap().get("orderService");
                return service.get().findOfferingById(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        return new TOffering();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value instanceof Integer || value == "") {
            return null;
        } else {
            TOffering lov = (TOffering) value;
            return String.valueOf(lov.getId());
        }
    }
}