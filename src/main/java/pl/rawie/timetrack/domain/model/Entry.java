package pl.rawie.timetrack.domain.model;

import java.util.Date;

public class Entry {
    private String summary;
    private Date start;
    private Date end;

    public Entry(String summary, Date start, Date end) {
        this.summary = summary;
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

    @Override
    public String toString() {
        return "Entry{" +
                "summary='" + summary + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
