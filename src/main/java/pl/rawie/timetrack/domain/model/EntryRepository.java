package pl.rawie.timetrack.domain.model;

import java.util.Date;
import java.util.List;

public interface EntryRepository {
    List<Entry> findAllByDate(Date date);

    void store(Entry entry);
}
