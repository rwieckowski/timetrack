package pl.rawie.timetrack.domain.model;

import pl.rawie.timetrack.domain.shared.DomainError;

import java.util.Collection;

public class DomainRule {
    public static void checkRule(boolean condition, DomainErrorCode errorCode) {
        if (!condition)
            throw new DomainError(errorCode);
    }

    public static <T> void checkNotNull(T reference) {
        checkRule(reference != null, DomainErrorCode.REQUIRED);
    }

    public static <T> void checkNotEmpty(Collection<T> reference) {
        checkRule(reference != null && !reference.isEmpty(), DomainErrorCode.REQUIRED);
    }

    public static void checkNotEmpty(String reference) {
        checkRule(reference != null && !reference.isEmpty(), DomainErrorCode.REQUIRED);
    }
}
