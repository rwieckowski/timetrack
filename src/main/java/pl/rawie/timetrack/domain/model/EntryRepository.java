package pl.rawie.timetrack.domain.model;

import com.google.common.collect.Range;
import org.joda.time.DateTime;

import java.util.List;

public interface EntryRepository {
    List<Entry> findAllByDateRange(Range<DateTime> date);

    DailyEntries getDailyEntries(DateTime start);

    void storeDailyEntries(DailyEntries dailyEntries);
}
