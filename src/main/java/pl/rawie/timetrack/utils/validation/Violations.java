package pl.rawie.timetrack.utils.validation;

import java.util.List;

public interface Violations {
    void reject(String code);

    void rejectIfEmpty(String property);

    void rejectIfEmpty(String property, String code);

    boolean hasViolations();

    List<Violation> getViolations();
}
