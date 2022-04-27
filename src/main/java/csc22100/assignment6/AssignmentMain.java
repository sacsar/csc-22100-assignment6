package csc22100.assignment6;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "mta", mixinStandardHelpOptions = true)
public class AssignmentMain {
  private static final String WEEK_4_23 =
      "http://web.mta.info/developers/data/nyct/turnstile/turnstile_220423.txt";

  @Command(name = "download", mixinStandardHelpOptions = true)
  void download(@Option(names = {"--output-dir"}) Path outputDir)
      throws IOException, InterruptedException {
    Utils.download(URI.create(WEEK_4_23), outputDir.resolve("2022-04-23.csv"));
  }

  @Command(name = "run", mixinStandardHelpOptions = true)
  void run(@Option(names = {"--data-dir"}) Path dataDir) {
    Supplier<Stream<TurnstileRecord>> supplyRecords =
        () -> {
          try {
            return Utils.streamRecords(dataDir.resolve("2022-04-23.csv"));
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        };
    StationCount topEntries = TurnstileExercises.topEntries(supplyRecords.get());
    System.out.printf("Top entries: %s%n", topEntries);

    StationCount topExits = TurnstileExercises.topExits(supplyRecords.get());
    System.out.printf("Top exits: %s%n", topExits);

    StationCount minEntries = TurnstileExercises.minEntries(supplyRecords.get());
    System.out.printf("Min entries: %s%n", minEntries);

    StationCount minExits = TurnstileExercises.minExits(supplyRecords.get());
    System.out.printf("Min exits: %s%n", minExits);

    List<DayCount> dayCounts = TurnstileExercises.countsByDay(supplyRecords.get());
    dayCounts.forEach(
        dayCount -> System.out.printf("%s: %d\n", dayCount.date(), dayCount.getCount()));
  }

  public static void main(String[] args) {
    int exitCode = new CommandLine(new AssignmentMain()).execute(args);
    System.exit(exitCode);
  }
}
