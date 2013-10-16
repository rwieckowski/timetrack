package pl.rawie.timetrack.domain.model;

public enum DomainErrorCode {
    OVERLAPPED_ENTRY;

    public String code() {
        return name().toLowerCase().replace('_', '.');
    }
}
