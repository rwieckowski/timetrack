package pl.rawie.validation;

import pl.rawie.validation.annotation.BusinessRule;

import javax.validation.ConstraintDefinitionException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BusinessRuleValidator implements ConstraintValidator<BusinessRule, Object>
{
    private pl.rawie.validation.BusinessRule rule;

    @Override
    public void initialize(BusinessRule constraintAnnotation) {
        try {
            this.rule = constraintAnnotation.value().newInstance();
        } catch (InstantiationException e) {
            throw new ConstraintDefinitionException(e);
        } catch (IllegalAccessException e) {
            throw new ConstraintDefinitionException(e);
        }
    }

    @Override
    @SuppressWarnings("unckecked")
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        ContextErrors errors = new ContextErrors(context);
        rule.check(value, errors);
        return errors.isEmpty();
    }

    private static class ContextErrors implements Errors {
        private ConstraintValidatorContext context;
        private boolean empty = true;

        private ContextErrors(ConstraintValidatorContext context) {
            this.context = context;
            this.context.disableDefaultConstraintViolation();
        }

        @Override
        public void reject(String message) {
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
            empty = false;
        }

        @Override
        public void reject(String property, String message) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(property)
                    .addConstraintViolation();
            empty = false;
        }

        @Override
        public boolean isEmpty() {
            return empty;
        }
    }
}
