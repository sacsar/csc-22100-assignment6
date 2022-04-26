package csc22100.assignment6;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;

class Utils {
  private static final CSVFormat CSV_FORMAT =
      CSVFormat.Builder.create().setHeader().setIgnoreSurroundingSpaces(true).build();

  private static final HttpClient HTTP_CLIENT =
      HttpClient.newBuilder().version(Version.HTTP_2).build();

  public static void download(URI uri, Path outputPath) throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
    HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofFile(outputPath));
  }

  public static Stream<TurnstileRecord> streamRecords(Path path) throws IOException {
    Reader in = new FileReader(path.toFile());
    return CSV_FORMAT.parse(in).stream().map(TurnstileRecord::parse);
  }

  private Utils() {}
}
