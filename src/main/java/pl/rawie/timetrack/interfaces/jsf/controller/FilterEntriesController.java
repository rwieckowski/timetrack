package pl.rawie.timetrack.interfaces.jsf.controller;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawie.timetrack.application.TimeTrackService;
import pl.rawie.timetrack.domain.model.DomainError;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.EntryRepository;
import pl.rawie.timetrack.interfaces.jsf.utils.Message;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

@Component
@ManagedBean
public class FilterEntriesController {
    @Autowired
    private TimeTrackSession session;
    @Autowired
    private TimeTrackService service;
    @Autowired
    private EntryRepository repository;
    private List<Entry> entries = new ArrayList<Entry>();

    public void findEntries(ActionEvent e) {
        loadEntries();
    }

    public void loadEntries() {
        try {
            entries = repository.findAllByDate(getFilterDate());
            System.out.println("load: " + entries);
        } catch (DomainError ex) {
            Message.error(ex);
        }
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    private DateTime getFilterDate() {
        return new DateTime(session.getFilterDate());
    }
}
