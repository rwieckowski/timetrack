package pl.rawie.timetrack.interfaces.jsf.controller;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawie.timetrack.application.TimeTrackService;
import pl.rawie.timetrack.domain.shared.DomainError;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.EntryBuilder;
import pl.rawie.timetrack.domain.model.EntryRepository;
import pl.rawie.timetrack.interfaces.jsf.utils.Message;
import pl.rawie.timetrack.utils.Today;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;

@Component
@ManagedBean
public class AddEntryController {
    private static String SUCCESS = "filter.xhtml?faces-redirect=true";
    private static String ERROR = null;

    @Autowired
    private TimeTrackService service;
    @Autowired
    private EntryRepository repository;
    private String summary;
    private Date date;
    private DateTime start;
    private DateTime end;

    public void populateForm() {
        // TODO: validation resets values
        date = Today.withStartOfTheDay().toDate();
        start = getDefaultStart();
        end = new DateTime();
    }

    private DateTime getDefaultStart() {
        DateTime end = Today.withTime(9);
        List<Entry> entries = repository.getDailyEntries(Today.withStartOfTheDay()).getEntries();
        for (Entry entry : entries)
            if (end.isBefore(entry.getEnd()))
                end = entry.getEnd();
        return end;
    }

    public String addEntry() {
        try {
            Entry entry = EntryBuilder
                    .anEntry()
                    .withSummary(summary)
                    .withStart(makeDate(date, start))
                    .withEnd(makeDate(date, end))
                    .build();
            service.addEntry(entry);
        } catch (ConstraintViolationException e) {
            Message.handleValidationExcepton(e);
            FacesContext.getCurrentInstance().validationFailed();
            return ERROR;
        } catch (DomainError e) {
            Message.domainError(e);
            FacesContext.getCurrentInstance().validationFailed();
            return ERROR;
        } catch (Throwable e) {
            Message.generalError(e);
            FacesContext.getCurrentInstance().validationFailed();
            return ERROR;
        }
        return SUCCESS;
    }

    private DateTime makeDate(Date date, DateTime time) {
        return new DateTime(date)
                .withTimeAtStartOfDay()
                .withHourOfDay(time.getHourOfDay())
                .withMinuteOfHour(time.getMinuteOfHour());
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DateTime getStart() {
        return start;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public DateTime getEnd() {
        return end;
    }

    public void setEnd(DateTime end) {
        this.end = end;
    }
}
