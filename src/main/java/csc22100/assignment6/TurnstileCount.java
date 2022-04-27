package csc22100.assignment6;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;

import java.time.LocalDate;

/** A class summarizing the entries/exits to a turnstile on a given day. */
@AutoValue
public abstract class TurnstileCount {
  abstract TurnstileRecord.Key key();

  abstract LocalDate date();

  abstract long entries();

  abstract long exits();

  public static TurnstileCount create(
      TurnstileRecord.Key key, LocalDate date, long entries, long exits) {
    Preconditions.checkArgument(
        entries >= 0 && exits >= 0,
        "TurnstileCounts must have non-negative entries and exits. Found entries=%s and exits=%s",
        entries,
        exits);
    return new AutoValue_TurnstileCount(key, date, entries, exits);
  }
}
