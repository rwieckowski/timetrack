import org.junit.Test;

public class ExcTest {
    @Test
    public void illegal() {
        throw new IllegalArgumentException("foo", new Throwable("is required"));
    }
}
