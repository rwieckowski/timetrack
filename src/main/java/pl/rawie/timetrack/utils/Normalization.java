package pl.rawie.timetrack.utils;

import org.joda.time.Duration;

public class Normalization {
    public static Duration deltaFor(Duration duration) {
        return normalize(duration).minus(duration);
    }

    public static Duration normalize(Duration duration) {
        long minutes = (duration.getStandardMinutes() + 14) / 30 * 30;
        return Duration.standardMinutes((minutes >= 30) ? minutes : 30);
    }
}
