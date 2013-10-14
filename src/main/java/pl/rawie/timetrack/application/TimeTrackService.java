package pl.rawie.timetrack.application;

import org.joda.time.DateTime;
import pl.rawie.timetrack.domain.model.AggregateEntry;
import pl.rawie.timetrack.domain.model.Entry;

import java.util.List;

public interface TimeTrackService {
    void addEntry(Entry entry);

    List<AggregateEntry> getWeekSummary(DateTime date);
}
