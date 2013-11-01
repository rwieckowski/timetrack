package pl.rawie.timetrack.domain.model;

import com.google.common.collect.Range;
import org.joda.time.DateTime;

import java.util.List;

public class WeekSummary {
    private Range<DateTime> week;
    private List<AggregateEntry> aggregates;

    public WeekSummary(Range<DateTime> week, List<AggregateEntry> aggregates) {
        this.week = week;
        this.aggregates = aggregates;
    }

    public Range<DateTime> getWeek() {
        return week;
    }

    public List<AggregateEntry> getAggregates() {
        return aggregates;
    }
}
