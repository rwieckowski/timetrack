package pl.rawie.timetrack.interfaces.jsf.controller;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawie.timetrack.application.TimeTrackService;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.EntryBuilder;
import pl.rawie.timetrack.domain.model.EntryRepository;
import pl.rawie.timetrack.interfaces.jsf.utils.Message;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@ManagedBean
public class EntryListController {
    @Autowired
    private TimeTrackSession session;
    @Autowired
    private TimeTrackService service;
    @Autowired
    private EntryRepository repository;
    private List<Entry> entries = new ArrayList<Entry>();

    @PostConstruct
    public void init() {
        entries = repository.findAllByDate(getFilterDate());
    }

    public void findEntries(ActionEvent e) {
        try {
            Message.info(getFilterDate().toString());
            service.addEntry(EntryBuilder
                    .anEntry()
                    .withSummary("entry #" + entries.size())
                    .withStart(DateTime.now().withTime(12, 0, 0, 0).toDate())
                    .withEnd(DateTime.now().withTime(14, 0, 0, 0).toDate())
                    .build());
            entries = repository.findAllByDate(getFilterDate());
        } catch (Exception ex) {
            Message.error(ex);
        }
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public Date getFilterDate() {
        return session.getFilterDate();
    }
}
