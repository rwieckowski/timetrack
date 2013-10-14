package pl.rawie.timetrack.utils;

import org.joda.time.DateTime;

import java.util.Date;

public class Today {
    public static DateTime withTime(int hours, int minutes) {
        return DateTime.now().withTime(hours, minutes, 0, 0);
    }

    public static DateTime withTime(int hours) {
        return DateTime.now().withTime(hours, 0, 0, 0);
    }

    public static DateTime withStartOfTheDay() {
        return DateTime.now().withTimeAtStartOfDay();
    }
}
