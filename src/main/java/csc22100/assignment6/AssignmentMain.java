package csc22100.assignment6;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name="mta", mixinStandardHelpOptions = true)
public class AssignmentMain {
    private static final String WEEK_4_23 = "http://web.mta.info/developers/data/nyct/turnstile/turnstile_220423.txt";

    @Command(name="download", mixinStandardHelpOptions = true)
    void download(@Option(names={"--output-dir"}) Path outputDir) throws IOException, InterruptedException {
        Utils.download(URI.create(WEEK_4_23), outputDir.resolve("2022-04-23.csv"));
    }

    @Command(name="run", mixinStandardHelpOptions = true)
    void run(@Option(names={"--data-dir"}) Path dataDir) {

    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new AssignmentMain()).execute(args);
        System.exit(exitCode);
    }
}
