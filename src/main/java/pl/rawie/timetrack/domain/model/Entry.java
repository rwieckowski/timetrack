package pl.rawie.timetrack.domain.model;

import com.google.common.collect.Range;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

import java.util.Date;

public class Entry {
    private String summary;
    private DateTime start;
    private DateTime end;

    public Entry(String summary, DateTime start, DateTime end) {
        this.summary = summary;
        this.start = start;
        this.end = end;
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

    public long getDuration() {
        return new Duration(start, end).getStandardMinutes();
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
