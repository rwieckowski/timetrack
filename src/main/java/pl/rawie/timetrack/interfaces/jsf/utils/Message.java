package pl.rawie.timetrack.interfaces.jsf.utils;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import pl.rawie.timetrack.domain.validator.ValidationError;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Message {
    public static void error(Throwable cause) {
        FacesMessage message = new FacesMessage(
                FacesMessage.SEVERITY_ERROR,
                cause.getClass().getSimpleName() + " bum!!!",
                //cause.getLocalizedMessage());
                "<ul><li>one</li><li>two</li></ul>");
        FacesContext
                .getCurrentInstance()
                .addMessage("form:summary", message);
    }

    public static void validationError(ValidationError cause) {
        String clientId;
        FacesMessage message;
        for (ObjectError error : cause.getErrors().getAllErrors()) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                clientId = "form:" + fieldError.getField();
                message = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Field " + fieldError.getField(),
                        fieldError.getCode());
            } else {
                clientId = null;
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Form", error.getCode());
            }

            FacesContext
                    .getCurrentInstance()
                    .addMessage(clientId, message);
        }
    }

    public static void info(String message) {
        FacesContext
                .getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }
}
