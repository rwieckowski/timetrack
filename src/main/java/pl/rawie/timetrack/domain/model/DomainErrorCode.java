package pl.rawie.timetrack.domain.model;

public enum DomainErrorCode {
    OVERLAPPED_ENTRY,
    END_BEFORE_START,
    DIFFERENT_START_DAY_AND_END_DAY,
    REQUIRED;

    public String code() {
        return name().toLowerCase().replace('_', '.');
    }
}
