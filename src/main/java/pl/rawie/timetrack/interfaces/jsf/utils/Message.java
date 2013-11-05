package pl.rawie.timetrack.interfaces.jsf.utils;

import com.google.common.base.Joiner;
import pl.rawie.timetrack.domain.shared.DomainError;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import static com.sun.jmx.mbeanserver.DefaultMXBeanMappingFactory.propertyName;

public class Message {
    public static void error(Throwable cause) {
        new MessageBuilder()
                .withSummary(cause.getClass().getSimpleName())
                .withDetail(cause.getLocalizedMessage())
                .addMessage();
    }

    private static String getResourceMessage(String code) {
        ResourceBundle bundle = ResourceBundle.getBundle("ValidationError");
        return (bundle.containsKey(code)) ? bundle.getString(code) : "Missing message: " + code;
    }

    public static void handleValidationExcepton(ConstraintViolationException e) {
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            String property = propertyName(violation.getPropertyPath());
            System.out.println(violation);
            System.out.println('<' + property + '>');
            if (property.isEmpty())
                new MessageBuilder()
                        .withSummary(violation.getMessage())
                        .addMessage();
            else
                new MessageBuilder()
                    .forForm("form")
                    .forField(property)
                    .withSummary(violation.getMessage())
                    .addMessage();
        }
    }

    private static String propertyName(Path path) {
        List<String> nodes = new ArrayList<String>();

        for (Path.Node node : path)
            if (node.getName() != null)
                nodes.add(node.getName());

        return Joiner.on('.').join(nodes.subList(2, nodes.size()));
    }

    private static String nodeName(Path.Node node) {
        return (node.getName() != null) ? ('.' + node.getName()) : "";
    }


    public static void domainError(DomainError cause) {
        new MessageBuilder()
                .withSummary(cause.getLocalizedMessage())
                .withDetail(cause.getCode().name())
                .addMessage();
    }

    public static void info(String message) {
        new MessageBuilder()
                .withSeverity(FacesMessage.SEVERITY_INFO)
                .withSummary(message)
                .addMessage();
    }

    public static void generalError(Throwable cause) {
        new MessageBuilder()
                .withSummary(cause.getClass().getSimpleName())
                .withDetail(cause.getLocalizedMessage())
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