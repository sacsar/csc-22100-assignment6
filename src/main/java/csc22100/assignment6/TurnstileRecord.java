package csc22100.assignment6;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.NotImplementedException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.google.auto.value.AutoValue;

import static csc22100.assignment6.TurnstileRecord.Fields.*;

public class TurnstileRecord {
  private final String controlArea;
  private final String unit;
  private final String scp;
  private final String station;
  private final String lineName;
  private final String division;
  private final LocalDateTime timestamp;
  private final String description;
  private final long entries;
  private final long exits;

  public TurnstileRecord(
      String controlArea,
      String unit,
      String scp,
      String station,
      String lineName,
      String division,
      LocalDateTime timestamp,
      String description,
      long entries,
      long exits) {
    this.controlArea = controlArea;
    this.unit = unit;
    this.scp = scp;
    this.station = station;
    this.lineName = lineName;
    this.division = division;
    this.timestamp = timestamp;
    this.description = description;
    this.entries = entries;
    this.exits = exits;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public Key getKey() {
    return Key.create(controlArea, unit, scp, station, lineName, division);
  }

  public long getEntries() {
    return entries;
  }

  public long getExits() {
    return exits;
  }

  @AutoValue
  abstract static class Key {
    abstract String controlArea();
    abstract String unit();
    abstract String scp();
    abstract Station station();

    static Key create(String controlArea, String unit, String scp, String station, String lines, String division) {
      return new AutoValue_TurnstileRecord_Key(controlArea, unit, scp, Station.create(station, lines, division));
    }
  }

  public static class Fields {
    public static String CONTROL_AREA = "C/A";
    public static String UNIT = "UNIT";
    public static String SCP = "SCP";
    public static String STATION = "STATION";
    public static String LINE_NAME = "LINENAME";
    public static String DIVISION = "DIVISION";
    public static String DATE = "DATE";
    public static String TIME = "TIME";
    public static String DESCRIPTION = "DESC";
    public static String ENTRIES = "ENTRIES";
    public static String EXITS = "EXITS";
  }

  public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

  public static TurnstileRecord parse(CSVRecord record) {
    String timestampString = record.get(DATE) + " " + record.get(TIME);
    LocalDateTime timestamp = LocalDateTime.parse(timestampString, DATE_FORMATTER);
    return new TurnstileRecord(
        record.get(CONTROL_AREA),
        record.get(UNIT),
        record.get(SCP),
        record.get(STATION),
        record.get(LINE_NAME),
        record.get(DIVISION),
        timestamp,
        record.get(DESCRIPTION),
        convertToLong(record.get(ENTRIES)),
        convertToLong(record.get(EXITS)));
  }

  private static long convertToLong(String n) {
    String trimmed = n.replaceFirst("^0+(?!$)", "");
    return Long.parseLong(trimmed);
  }
}
