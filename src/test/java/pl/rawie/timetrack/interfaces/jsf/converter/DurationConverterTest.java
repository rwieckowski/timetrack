package pl.rawie.timetrack.interfaces.jsf.converter;

import org.joda.time.Duration;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DurationConverterTest {
    private void assertGetAsString(Duration duration, String expected) {
        assertThat(new DurationConverter().getAsString(null, null, duration), is(equalTo(expected)));
    }

    @Test
    public void getAsString() {
        assertGetAsString(Duration.ZERO, "0<small>m</small>");
        assertGetAsString(Duration.standardMinutes(30), "30<small>m</small>");
        assertGetAsString(Duration.standardMinutes(60), "1<small>h</small>");
        assertGetAsString(Duration.standardMinutes(90), "1<small>h</small> 30<small>m</small>");
        assertGetAsString(Duration.standardMinutes(-30), "-30<small>m</small>");
        assertGetAsString(Duration.standardMinutes(-60), "-1<small>h</small>");
        assertGetAsString(Duration.standardMinutes(-90), "-1<small>h</small> 30<small>m</small>");
    }
}
