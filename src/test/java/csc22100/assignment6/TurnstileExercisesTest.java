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
}
