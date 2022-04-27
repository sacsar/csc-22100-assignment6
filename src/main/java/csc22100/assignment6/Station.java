package csc22100.assignment6;

import com.google.auto.value.AutoValue;

/**
 * A class representing a Station.
 */
@AutoValue
public abstract class Station {
    abstract String stationName();
    abstract String lines();
    abstract String division();

    public static Station create(String station, String lines, String division) {
        return new AutoValue_Station(station, lines, division);
    }
}
