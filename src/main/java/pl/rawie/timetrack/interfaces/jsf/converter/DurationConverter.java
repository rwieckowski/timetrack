package pl.rawie.timetrack.interfaces.jsf.converter;

import org.joda.time.Duration;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass=Duration.class, value="Duration")
public class DurationConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Duration duration = (Duration)value;
        long hours = duration.getStandardHours();
        long minutes = duration.getStandardMinutes() % 60;
        String str = "";
        if (hours > 0)
            str += hours + "<small>h</small>";
        if (hours > 0 && minutes > 0)
            str += ' ';
        if (minutes > 0)
            str += minutes + "<small>m</small>";
        return str;
    }
}
