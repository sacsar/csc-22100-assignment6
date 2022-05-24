package csc22100.assignment6;

import com.google.common.io.Resources;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class TurnstileExercisesTest implements WithAssertions {
  private static final Path sampleData;
  
  static {
    try {
      sampleData = Paths.get(Resources.getResource("sample.csv").toURI());
    } catch (URISyntaxException e) {
      throw new RuntimeException(e); 
    }
  }
  
  private static final Station FIFTY_THIRD = Station.create("53 ST", "R", "BMT");
  private static final Station SEVENTY_SECOND = Station.create("72 ST", "123", "IRT");

  private static List<TurnstileRecord> records;

  @BeforeAll
  public static void setup() throws IOException {
    records = Utils.streamRecords(sampleData).collect(Collectors.toList());
  }

  @Test
  public void testMaxEntries() {
    StationCount topEntries = TurnstileExercises.topEntries(records.stream());
    assertThat(topEntries.getStation()).isEqualTo(FIFTY_THIRD);
    assertThat(topEntries.getEntries()).isEqualTo(2100);
  }

  @Test
  public void testTopExits() {
    StationCount topExits = TurnstileExercises.topExits(records.stream());
    assertThat(topExits.getStation()).isEqualTo(SEVENTY_SECOND);
    assertThat(topExits.getExits()).isEqualTo(1988);
  }

  @Test
  public void testMinEntries() {
    StationCount minEntries = TurnstileExercises.minEntries(records.stream());
    assertThat(minEntries.getStation()).isEqualTo(SEVENTY_SECOND);
    assertThat(minEntries.getEntries()).isEqualTo(1255);
  }

  @Test
  public void testMinExits() {
    StationCount minExits = TurnstileExercises.minExits(records.stream());
    assertThat(minExits.getStation()).isEqualTo(FIFTY_THIRD);
    assertThat(minExits.getExits()).isEqualTo(990);
  }

  @Test
  public void testDayCount() {
    List<DayCount> dayCounts = TurnstileExercises.countsByDay(records.stream());
    DayCount expected =
        DayCount.create(
            TurnstileCount.create(
                TurnstileRecord.Key.create("foo", "bar", "baz", "qux", "123", "foo"),
                LocalDate.of(2022, 4, 15),
                3355,
                2978));
    assertThat(dayCounts).containsExactly(expected);
  }

  /**
   * C230,R233,00-03-00,53 ST,R,BMT,04/15/2022,00:00:00,REGULAR,0000000100,0000000010
   * C230,R233,00-03-00,53 ST,R,BMT,04/15/2022,04:00:00,REGULAR,0000000120,0000000025
   * C230,R233,00-03-00,53 ST,R,BMT,04/15/2022,08:00:00,REGULAR,0000000124,0000000045
   * C230,R233,00-03-00,53 ST,R,BMT,04/15/2022,12:00:00,REGULAR,0000000245,0000000123
   * C230,R233,00-03-00,53 ST,R,BMT,04/15/2022,16:00:00,REGULAR,0000001467,0000000589
   * C230,R233,00-03-00,53 ST,R,BMT,04/15/2022,20:00:00,REGULAR,0000002000,0000000789
   * C230,R233,00-03-00,53 ST,R,BMT,04/16/2022,00:00:00,REGULAR,0000002200,0000001000
   */
  @Test
  public void testAggregateByTurnstile() {
    Map<TurnstileRecord.Key, List<TurnstileCount>> byTurnstile = TurnstileExercises.aggregateByTurnstile(records.stream());
    TurnstileRecord.Key key = TurnstileRecord.Key.create("C230", "R233", "00-03-00", "53 ST", "R", "BMT");

    List<TurnstileCount> expected = List.of(
            TurnstileCount.create(key, LocalDate.of(2022,4,15),20, 15),
            TurnstileCount.create(key, LocalDate.of(2022,4,15),4, 20),
            TurnstileCount.create(key, LocalDate.of(2022,4,15),121, 78),
            TurnstileCount.create(key, LocalDate.of(2022,4,15),1222, 466),
            TurnstileCount.create(key, LocalDate.of(2022,4,15),533, 200),
            TurnstileCount.create(key, LocalDate.of(2022,4,15),200, 211)
    );

    assertThat(byTurnstile.get(key)).containsExactly(expected.toArray(new TurnstileCount[]{}));
  }
}
