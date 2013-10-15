package pl.rawie.timetrack.interfaces.jsf.converter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass=DateTime.class, value="Time")
public class DateTimeTimeConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return DateTimeFormat.forPattern("HH:mm").parseDateTime(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return DateTimeFormat.forPattern("HH:mm").print((DateTime)value);
    }
}
