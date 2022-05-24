package csc22100.assignment6;

import com.google.common.collect.Streams;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TurnstileExercises {

  // Some helpers to handle the Staten Island Railway and Clark St.
  // WARNING: This is not all the filtering you need to do.
  private static final Station CLARK_STREET = Station.create("CLARK ST", "23", "IRT");
  private static final Predicate<Station> NOT_SIR = station -> !"SRT".equals(station.division());
  private static final Predicate<Station> TO_INCLUDE =
      station -> !CLARK_STREET.equals(station) && NOT_SIR.test(station);

  /**
   * Find the station with the most entries recorded.
   *
   * @param records a stream of the raw {@link TurnstileRecord}
   * @return a {@link StationCount} object for the station which saw the greatest number of entries
   */
  public static StationCount topEntries(Stream<TurnstileRecord> records) {
    throw new NotImplementedException();
  }

  /**
   * Find the station with the most exits recorded.
   *
   * @param records a stream of the raw {@link TurnstileRecord}
   * @return a {@link StationCount} object for the station which saw the greatest number of exits
   */
  public static StationCount topExits(Stream<TurnstileRecord> records) {
    // now we find the top station
    return aggregateByStation(records).max(Comparator.comparing(StationCount::getExits)).get();
  }

  /**
   * Find the station with the fewest entries recorded.
   *
   * @param records a stream of the raw {@link TurnstileRecord}
   * @return a {@link StationCount} for the station which saw the fewest entries
   */
  public static StationCount minEntries(Stream<TurnstileRecord> records) {
    throw new NotImplementedException();
  }

  /**
   * Find the station with the fewest exits.
   *
   * @param records a stream of the raw {@link TurnstileRecord}
   * @return a {@link StationCount} for the station which saw the fewest entries
   */
  public static StationCount minExits(Stream<TurnstileRecord> records) {
    throw new NotImplementedException();
  }

  /**
   * Count how many entries and exits there were from the system each day.
   *
   * @param records a stream of the raw {@link TurnstileRecord}
   * @return a (sorted!) list of {@link DayCount} objects, summarising the ridership for the day
   */
  public static List<DayCount> countsByDay(Stream<TurnstileRecord> records) {
    throw new NotImplementedException();
  }

  /*************************************************************************************************
   * You may ignore all functions below this point. They were things I found helpful to implement. *
   *************************************************************************************************/

   public static Map<TurnstileRecord.Key, List<TurnstileCount>> aggregateByTurnstile(
      Stream<TurnstileRecord> records) {
    throw new NotImplementedException();
  }

  public static Stream<StationCount> aggregateByStation(Stream<TurnstileRecord> records) {
    throw new NotImplementedException();
  }

  private static final Comparator<TurnstileRecord> TURNSTILE_COMPARATOR = null;

  private TurnstileExercises() {}
}
