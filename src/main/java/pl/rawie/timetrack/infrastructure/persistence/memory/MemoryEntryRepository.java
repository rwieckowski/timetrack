package pl.rawie.timetrack.infrastructure.persistence.memory;

import com.google.common.collect.Range;
import org.springframework.stereotype.Repository;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.EntryRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class MemoryEntryRepository implements EntryRepository {
    private List<Entry> entries = new ArrayList<Entry>();

    @Override
    public List<Entry> findAllByDateRange(Range<Date> range) {
        return entries;
    }

    @Override
    public void store(Entry entry) {
        entries.add(entry);
    }
}
