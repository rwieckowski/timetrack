package pl.rawie.timetrack.application;

import org.joda.time.DateTime;
import pl.rawie.timetrack.domain.model.AggregateEntry;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.WeekSummary;

import java.util.List;

public interface TimeTrackService {
    void addEntry(Entry entry);

    WeekSummary getWeekSummary(DateTime date);
}
