package pl.rawie.timetrack.interfaces.jsf.utils;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import pl.rawie.timetrack.domain.model.DomainError;
import pl.rawie.timetrack.domain.model.DomainErrorCode;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Message {
    public static void error(Throwable cause) {
        new MessageBuilder()
                .withSummary(cause.getClass().getSimpleName())
                .withDetail(cause.getLocalizedMessage())
                .addMessage();
    }

    public static void domainError(DomainError cause) {
        MessageBuilder builder = new MessageBuilder();
        if (cause.getCode() == DomainErrorCode.VALIDATION_FAILED) {
            for (ObjectError error : cause.getErrors().getAllErrors()) {
                if (error instanceof FieldError) {
                    FieldError fieldError = (FieldError) error;
                    builder
                            .forForm("form")
                            .forField(fieldError.getField())
                            .withSummary(cause.getLocalizedMessage())
                            .withDetail(fieldError.getCode())
                            .addMessage();
                } else {
                    builder
                            .withSummary(cause.getLocalizedMessage())
                            .addMessage();
                }
            }

        } else {
            builder
                    .withSummary(cause.getLocalizedMessage())
                    .addMessage();
        }
    }

    public static void info(String message) {
        new MessageBuilder()
                .withSeverity(FacesMessage.SEVERITY_INFO)
                .withSummary(message)
                .addMessage();
    }
}

class MessageBuilder {
    private String form;
    private String field;
    private FacesMessage.Severity severity;
    private String summary;
    private String detail;

    MessageBuilder() {
        reset();
    }

    private void reset() {
        form = null;
        field = null;
        severity = FacesMessage.SEVERITY_ERROR;
        summary = "Missing message text!!!";
        detail = null;
    }

    public MessageBuilder forForm(String form) {
        this.form = form;
        return this;
    }

    public MessageBuilder forField(String field) {
        this.field = field;
        return this;
    }

    public MessageBuilder withSeverity(FacesMessage.Severity severity) {
        this.severity = severity;
        return this;
    }

    public MessageBuilder withSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public MessageBuilder withDetail(String detail) {
        this.detail = detail;
        return this;
    }

    public MessageBuilder addMessage() {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext
                .getCurrentInstance()
                .addMessage(getClientId(), message);
        return this;
    }

    private String getClientId() {
        String id = "";
        if (form != null) id += form;
        if (form != null && field != null) id += ':';
        if (field != null) id += field;
        return (id.isEmpty()) ? null : id;
    }
}