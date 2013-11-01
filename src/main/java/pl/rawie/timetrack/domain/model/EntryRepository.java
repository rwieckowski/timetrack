package pl.rawie.timetrack.domain.model;

import com.google.common.collect.Range;
import org.joda.time.DateTime;

import java.util.List;

public interface EntryRepository {
    WeeklyEntries getWeeklyEntries(DateTime date);

    DailyEntries getDailyEntries(DateTime date);

    void storeDailyEntries(DailyEntries dailyEntries);
}
