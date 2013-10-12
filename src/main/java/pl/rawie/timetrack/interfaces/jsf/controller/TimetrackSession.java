package pl.rawie.timetrack.interfaces.jsf.controller;

import org.springframework.stereotype.Component;
import pl.rawie.timetrack.utils.Today;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Date;

@Component
@ManagedBean
@SessionScoped
public class TimeTrackSession implements Serializable {
    private Date filterDate = Today.toDate();

    public Date getFilterDate() {
        return filterDate;
    }

    public void setFilterDate(Date filterDate) {
        this.filterDate = filterDate;
    }
}
