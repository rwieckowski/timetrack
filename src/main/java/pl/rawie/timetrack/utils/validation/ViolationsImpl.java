package pl.rawie.timetrack.utils.validation;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ViolationsImpl implements Violations {
    public static final char SEPARATOR = '.';
    public static final String EMPTY = "empty";
    private Object bean;
    private List<Violation> violations = new ArrayList<Violation>();

    public ViolationsImpl(Object bean) {
        this.bean = bean;
    }

    @Override
    public void reject(String code) {
        violations.add(new Violation("", code));
    }

    @Override
    public void rejectIfEmpty(String property) {
        rejectIfEmpty(property, EMPTY);
    }

    @Override
    public void rejectIfEmpty(String property, String code) {
        try {
            String value = (String)BeanUtils.getProperty(bean, property);
            if (value == null || value.isEmpty())
                violations.add(new Violation(property, code));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasViolations() {
        return !violations.isEmpty();
    }

    @Override
    public List<Violation> getViolations() {
        return violations;
    }
}
