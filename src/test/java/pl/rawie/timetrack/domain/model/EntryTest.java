package pl.rawie.timetrack.domain.model;

import org.junit.Test;
import pl.rawie.timetrack.utils.Today;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

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

    @Test
    public void startAfterEnd() {
        expectIllegalArgumentException(builder().withStart(Today.withTime(14)).withEnd(Today.withTime(12)),
                "start must be before end");
    }

    @Test
    public void startFromDifferentDayThanEnd() {
        expectIllegalArgumentException(builder().withStart(Today.withTime(12).minusDays(1)),
                "start and end must be at the same day");
    }

    @Test
    public void validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Entry>> violations = validator.validate(SampleEntry.builder().withSummary(" ").build());
        System.out.println(violations);
        assertThat(violations.isEmpty(), is(false));
    }
}
