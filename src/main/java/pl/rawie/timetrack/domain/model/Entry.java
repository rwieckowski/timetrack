package pl.rawie.timetrack.domain.model;

import com.google.common.collect.Range;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

import java.util.Date;

public class Entry {
    private String summary;
    private Date start;
    private Date end;

    public Entry(String summary, Date start, Date end) {
        this.summary = summary;
        this.start = start;
        this.end = end;
    }

    public String getSummary() {
        return summary;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public Range getDateRange() {
        return Range.closedOpen(start, end);
    }

    public long getDuration() {
        Duration duration = new Duration(new DateTime(start), new DateTime(end));
        return duration.getStandardMinutes();
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
