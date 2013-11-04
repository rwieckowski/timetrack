package pl.rawie.timetrack.domain.model;

import org.junit.Ignore;
import org.junit.Test;
import pl.rawie.timetrack.utils.Today;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static pl.rawie.timetrack.domain.model.SampleEntry.builder;

public class EntryTest {
    private void expectNullPointerException(EntryBuilder builder, String message) {
        try {
            builder.build();
            fail("Should failed");
        } catch (NullPointerException e) {
            assertThat(e.getMessage(), is(equalTo(message)));
        }
    }

    private void expectIllegalArgumentException(EntryBuilder builder, String message) {
        try {
            builder.build();
            fail("Should failed");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(equalTo(message)));
        }
    }

    @Test
    public void nullSummary() {
        expectNullPointerException(builder().withSummary(null), "summary");
    }

    @Ignore
    @Test
    public void emptySummary() {
        expectIllegalArgumentException(builder().withSummary(""), "summary");
    }

    @Test
    public void nullStart() {
        expectNullPointerException(builder().withStart(null), "start");
    }

    @Test
    public void nullEnd() {
        expectNullPointerException(builder().withEnd(null), "end");
    }

    @Ignore
    @Test
    public void startAfterEnd() {
        expectIllegalArgumentException(builder().withStart(Today.withTime(14)).withEnd(Today.withTime(12)),
                "start must be before end");
    }

    @Ignore
    @Test
    public void startFromDifferentDayThanEnd() {
        expectIllegalArgumentException(builder().withStart(Today.withTime(12).minusDays(1)),
                "start and end must be at the same day");
    }
}
