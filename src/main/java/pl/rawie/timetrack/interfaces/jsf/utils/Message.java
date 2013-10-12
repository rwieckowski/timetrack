package pl.rawie.timetrack.interfaces.jsf.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Message {
    public static void error(Throwable cause) {
        FacesMessage message = new FacesMessage(
                FacesMessage.SEVERITY_ERROR,
                cause.getClass().getSimpleName(),
                cause.getLocalizedMessage());
        FacesContext
                .getCurrentInstance()
                .addMessage(null, message);
    }

    public static void info(String message) {
        FacesContext
                .getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
    }
}
