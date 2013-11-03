package pl.rawie.timetrack.domain.model;

import java.util.Locale;
import java.util.ResourceBundle;

public class DomainException extends Exception {
    protected String getResourceMessage() {
        return getResourceMessage(Locale.getDefault());
    }

    private String getResourceMessage(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("exceptions", locale);
        String key = getClass().getSimpleName();
        return (bundle.containsKey(key)) ? bundle.getString(key) : "Missing message: " + key;
    }
}
