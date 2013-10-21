package pl.rawie.timetrack.utils;

import org.joda.time.Duration;

public class Normalization {
    public static Duration delta(Duration duration) {
        long minutes = (duration.getStandardMinutes() + 14) / 30 * 30;
        long delta = (minutes >= 30) ? minutes - duration.getStandardMinutes() : 30 - duration.getStandardMinutes();
        return Duration.standardMinutes(delta);
    }
}
