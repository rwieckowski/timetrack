package pl.rawie.timetrack.domain.service.impl;

import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.service.OverlapService;

import java.util.Collection;

public class OverlapServiceImpl implements OverlapService {
    @Override
    public boolean overlaps(Entry entry, Collection<Entry> entries) {
        for (Entry existing : entries) {
            try {
                if (!entry.getDateRange().intersection(existing.getDateRange()).isEmpty())
                    return true;
            } catch (IllegalArgumentException e) {
                // no intersection
            }
        }

        return false;
    }
}
