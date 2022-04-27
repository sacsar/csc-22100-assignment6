package csc22100.assignment6;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/** A class to summarize the entries and exits to a particular station. */
public class StationCount {

  private final Station station;
  private final long entries;
  private final long exits;

  public StationCount(Station station, long entries, long exits) {
    this.station = station;
    this.entries = entries;
    this.exits = exits;
  }

  public Station getStation() {
    return station;
  }

  public long getEntries() {
    return entries;
  }

  public long getExits() {
    return exits;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("station", station)
        .add("entries", entries)
        .add("exits", exits)
        .toString();
  }

  public StationCount merge(StationCount other) {
    Preconditions.checkArgument(
        station.equals(other.getStation()),
        "Can only merge counts from the same station. Found %s and %s",
        station,
        other.getStation());
    return new StationCount(getStation(), entries + other.getEntries(), exits + other.getExits());
  }
}
