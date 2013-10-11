package pl.rawie.timetrack.interfaces.jsf.form;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawie.timetrack.application.TimeTrackService;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.EntryBuilder;
import pl.rawie.timetrack.domain.model.EntryRepository;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@ManagedBean
@SessionScoped
public class EntryListController {
    private Date date;
    private List<Entry> entries = new ArrayList<Entry>();
    @Autowired
    private TimeTrackService service;
    @Autowired
    private EntryRepository repository;

    public void findEntries(ActionEvent e) {
        service.addEntry(EntryBuilder
                .anEntry()
                .withSummary("entry #" + entries.size())
                .withStart(DateTime.now().withTime(12, 0, 0, 0).toDate())
                .withEnd(DateTime.now().withTime(14, 0, 0, 0).toDate())
                .build());
        entries = repository.findAllByDateRange(null);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}
