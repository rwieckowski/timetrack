package pl.rawie.validation;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Set;

public class ApplicationServiceProxy implements InvocationHandler {
    private Object service;

    public ApplicationServiceProxy(Object service) {
        this.service = service;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method invoked = getServiceMethod(method);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        ExecutableValidator validator = factory.getValidator().forExecutables();
        Set<ConstraintViolation<Object>> violations = validator.validateParameters(service, invoked, args);
        System.out.printf("Validating method %s ... %d violation(s)%n", method.getName(), violations.size());

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

        return method.invoke(service, args);
    }

    private Method getServiceMethod(Method method) throws NoSuchMethodException {
        return service.getClass().getMethod(method.getName(), method.getParameterTypes());
    }
}