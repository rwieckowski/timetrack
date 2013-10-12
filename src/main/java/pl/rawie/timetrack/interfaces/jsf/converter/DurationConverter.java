package pl.rawie.timetrack.interfaces.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("Duration")
public class DurationConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        long duration = (Long)value;
        long hours = duration / 60;
        long minutes = duration % 60;
        String str = "";
        if (hours > 0)
            str += hours + "<small>h</small>";
        if (hours > 0 && minutes > 0)
            str += ' ';
        if (minutes > 0)
            str += minutes + "m";
        return str;
    }
}
