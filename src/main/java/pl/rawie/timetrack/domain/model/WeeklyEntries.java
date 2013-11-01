package pl.rawie.timetrack.domain.model;

import com.google.common.collect.Range;
import org.joda.time.DateTime;
import pl.rawie.timetrack.domain.service.AggregateService;

import java.util.List;

public class WeeklyEntries {
    private Range<DateTime> week;
    private List<Entry> entries;

    public WeeklyEntries(Range<DateTime> week, List<Entry> entries) {
        this.week = week;
        this.entries = entries;
    }

    public WeekSummary generateWeekSummary(AggregateService service) {
        List<AggregateEntry> aggregates = service.normalize(service.aggregate(entries));
        return new WeekSummary(week, aggregates);
    }

    public Range<DateTime> getWeek() {
        return week;
    }

    public List<Entry> getEntries() {
        return entries;
    }
}
