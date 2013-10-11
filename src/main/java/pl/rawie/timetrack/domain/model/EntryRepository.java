package pl.rawie.timetrack.domain.model;

import com.google.common.collect.Range;

import java.util.Date;
import java.util.List;

public interface EntryRepository {
    List<Entry> findAllByDateRange(Range<Date> range);

    void store(Entry entry);
}
