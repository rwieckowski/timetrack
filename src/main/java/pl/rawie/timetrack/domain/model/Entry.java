package pl.rawie.timetrack.domain.model;

import com.google.common.collect.Range;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import pl.rawie.validation.annotation.BusinessRule;

import static com.google.common.base.Preconditions.checkNotNull;

@BusinessRule(EntryRule.class)
public class Entry {
    @NotBlank
    private String summary;
    private DateTime start;
    private DateTime end;

    public Entry(String summary, DateTime start, DateTime end) {
        checkNotNull(summary, "summary");
        checkNotNull(start, "start");
        checkNotNull(end, "end");
        this.summary = summary;
        this.start = start;
        this.end = end;
    }

    public boolean sameAs(Entry other) {
        return summary.equalsIgnoreCase(other.summary);
    }

    public String getSummary() {
        return summary;
    }

    public DateTime getStart() {
        return start;
    }

    public DateTime getEnd() {
        return end;
    }

    public Range<DateTime> getDateTimeRange() {
        return Range.closedOpen(start, end);
    }

    public Duration getDuration() {
        return new Duration(start, end);
    }

    @Override
    public String toString() {
        return "Entry{" +
                "summary='" + summary + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
