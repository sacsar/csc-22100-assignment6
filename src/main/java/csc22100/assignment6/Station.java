package csc22100.assignment6;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Station {
    abstract String stationName();
    abstract String lines();

    public static Station create(String station, String lines) {
        return new AutoValue_Station(station, lines);
    }
}
