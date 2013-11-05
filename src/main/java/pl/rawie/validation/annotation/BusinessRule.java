package pl.rawie.validation.annotation;


import pl.rawie.validation.BusinessRuleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target({ TYPE, METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = BusinessRuleValidator.class)
@Documented
public @interface BusinessRule {
    String message() default "pl.rawie.demo.customValidator.BusinessRule.message";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Class<? extends pl.rawie.validation.BusinessRule> value();

    @Target({ TYPE, METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        BusinessRule[] value();
    }
}
