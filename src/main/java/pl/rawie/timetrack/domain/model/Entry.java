package pl.rawie.timetrack.domain.model;

import javax.validation.constraints.NotNull;

public class Entry {
    @NotNull
    private String summary;

    public Entry(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public static final class Builder {
        private String summary;

        public Builder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public Entry build() {
            return new Entry(summary);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
