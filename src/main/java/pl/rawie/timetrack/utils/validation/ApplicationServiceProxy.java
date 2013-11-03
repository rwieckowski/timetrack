package pl.rawie.timetrack.utils.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApplicationServiceProxy implements InvocationHandler {
    private Object service;

    public ApplicationServiceProxy(Object service) {
        this.service = service;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method invoked = getServiceMethod(method);
        List<Validation> validations = extractValidations(invoked, args);
        System.out.println(validations);
        Violations violations = invokeValidations(validations);
        rejectIfNotEmpty(violations);
        return method.invoke(service, args);
    }

    private Method getServiceMethod(Method method) throws NoSuchMethodException {
        return service.getClass().getMethod(method.getName(), method.getParameterTypes());
    }

    private List<Validation> extractValidations(Method method, Object[] args) {
        List<Validation> validations = new ArrayList<Validation>();
        Annotation[][] annotations = method.getParameterAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            System.out.println("#" + i + ' ' + Arrays.toString(annotations[i]));
            for (Annotation annotation : annotations[i])
                if (annotation instanceof Check)
                    validations.add(new Validation("arg" + i, args[i], ((Check)annotation).value()));
        }
        return validations;
    }

    private Violations invokeValidations(List<Validation> validations) throws InstantiationException, IllegalAccessException {
        Violations violations = new ViolationsImpl(null);
        for (Validation validation : validations) {
            // TODO: create factory
            // TODO: support multiple beans
            violations = new ViolationsImpl(validation.getValue());
            validation.invoke(violations);
        }
        return violations;
    }

    private void rejectIfNotEmpty(Violations violations) {
        if (violations.hasViolations())
            throw new ValidationException(violations);
    }
}

class Validation {
    private String name;
    private Object value;
    private Class<? extends Validator> validator;

    Validation(String name, Object value, Class<? extends Validator> validator) {
        this.name = name;
        this.value = value;
        this.validator = validator;
    }

    @SuppressWarnings("unckecked")
    public void invoke(Violations violations) throws IllegalAccessException, InstantiationException {
        Validator v = validator.newInstance();
        v.validate(value, violations);
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Validation{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", validator=" + validator +
                '}';
    }
}
