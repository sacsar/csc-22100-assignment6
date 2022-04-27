package csc22100.assignment6;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.NotImplementedException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** A class to summarize the ridership on a given day. */
@AutoValue
public abstract class DayCount {
  abstract LocalDate date();

  abstract long entries();

  abstract long exits();

  public DayCount merge(DayCount other) {
    Preconditions.checkArgument(
        date().equals(other.date()),
        "Only two DayCounts with the same date can be merged. Found %s and %s",
        DateTimeFormatter.ISO_DATE.format(date()),
        DateTimeFormatter.ISO_DATE.format(other.date()));
    return new AutoValue_DayCount(date(), entries() + other.entries(), exits() + other.exits());
  }

  /** Get the number of riders for the day. */
  public long getCount() {
    throw new NotImplementedException();
  }

  public static DayCount create(TurnstileCount turnstileCount) {
    return new AutoValue_DayCount(
        turnstileCount.date(), turnstileCount.entries(), turnstileCount.exits());
  }
}
