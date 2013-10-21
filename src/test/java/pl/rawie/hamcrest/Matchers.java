package pl.rawie.hamcrest;

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

public class Matchers {
    public static <E,V> Matcher<Iterable<? extends E>> containsProperty(String propertyName, V... values) {
        return containsProperty(propertyName, Arrays.asList(values));
    }

    public static <E,V> Matcher<Iterable<? extends E>> containsProperty(String propertyName, List<V> values) {
        List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>();
        for (V value : values)
            matchers.add(hasProperty(propertyName, equalTo(value)));
        return contains(matchers);
    }
}
