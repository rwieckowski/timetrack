package pl.rawie.timetrack.domain.model;

import com.google.common.collect.Range;

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

    @Override
    public String toString() {
        return "Entry{" +
                "summary='" + summary + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
