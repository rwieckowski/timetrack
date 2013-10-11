package pl.rawie.timetrack.domain.validator;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.springframework.validation.Validator;
import pl.rawie.timetrack.domain.model.Entry;

import static org.junit.Assert.assertThat;
import static pl.rawie.timetrack.domain.validator.ValidatorMatchers.hasFieldError;

public class AddEntryValidatorTest {
    private Validator validator = new AddEntryValidator();

    private Entry.Builder builder() {
        Entry.Builder builder = Entry.builder();
        builder.setSummary("summary");
        return builder;
    }

    private void validate(Entry entry, Matcher<ValidationError>... matchers) {
        try {
            ValidatorUtils.invoke(validator, entry, "entry");
        } catch (ValidationError e) {
            for (Matcher<ValidationError> matcher : matchers)
                assertThat(e, matcher);
        }
    }

    @Test
    public void emptySummary() {
        Entry entry = builder()
                .setSummary("")
                .build();
        validate(entry, hasFieldError("summary", "required"));
    }
}


