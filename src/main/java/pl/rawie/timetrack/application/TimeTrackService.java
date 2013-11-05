package pl.rawie.timetrack.application;

import org.joda.time.DateTime;
import pl.rawie.timetrack.domain.model.AggregateEntry;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.WeekSummary;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface TimeTrackService {
    void addEntry(@Valid Entry entry);

    WeekSummary getWeekSummary(DateTime date);
}
