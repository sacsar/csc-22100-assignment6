# CSc 22100 Assignment 6

The focus of this assignment is the Streams API and Functional Interfaces. This means that **loops are banned** in this
assignment, you have to use streams rather than `for` or `while`. We will play
around [MTA Turnstile Data](http://web.mta.info/developers/turnstile.html) from last week.

The turnstile data is published weekly as a CSV. The definitions of the columns can be
found [here](http://web.mta.info/developers/resources/nyct/turnstile/ts_Field_Description.txt). Some things to know:

- Turnstiles are uniquely identified by the tuple `(C/A, UNIT, SCP)`. Stations are uniquely identified by the
  tuple `(STATION, LINENAME)` (in other words, Penn Station on the A/C/E and Penn Station on the 1/2/3 are different).
- The `entries` and `exits` are counts of how many times the turnstile rotated in a given direction, which means to find
  how many people entered/exited in a given period, you need to subtract two consecutive entry/exit counts. For example,
  here are two consecutive records for (one turnstile at) 53rd Street on the R:
    ```
    C020	R233	00-03-01	53 ST	R	BMT	REGULAR	196568	347669	2022-04-16 08:00:00.000
    C020	R233	00-03-01	53 ST	R	BMT	REGULAR	196619	347726	2022-04-16 12:00:00.000
    ```
  At 8am, the turnstile had seen 196568 entries and at 12pm it had seen 196619 entries, i.e. 51 people entered in those
  four hours.
- Some turnstiles seem to count down rather than up. Take the absolute value.
- There is the possibility of the counts overflowing (they handle up to 10 digits), but that didn't happen last week.
- Some of the data is obviously incorrect. I feel comfortable assuming 926,340,484 people did not enter the subway at
  Hunts Point in four hours last Wednesday morning!

## Instructions

1. Like the last assignment, start off by downloading the data published 4/23 by running `./gradlew download` (or just
   grab it from the MTA site).
2. Filter out any cases where more than 2000 entries or exits are recorded in a "period".
3. Also filter out Clark St on the 2/3 (entry counting is broken) and SIR (no exit data).
4. Implement the various (public) functions in `TurnstileExercises.java` to answer the following questions:
    - Which station had the most entries (in total) during the week? Which station had the least? How many?
    - Which station had the most exits (in total) during the week? Which station had the least? How many?
    - How many riders were there each day? (Decide how to count this and implement the `getCount` function
      in `DayCount.java`. Make sure to leave a comment explaining the choice you made.)

I left a number of stubs of private functions in `TurnstileExercises.java`. You can implement them if you want, modify
the signatures or ignore them completely. The most/least entries and exits functions are all *extremely* similar, and
those private functions are from pulling out the repetitive parts and/or making my code more readable.

`src/test/resources/sample.csv` contains a tiny made-up sample of data (only two stations, one day). `TurnstileExercisesTest`
does cover this dataset, but my sample dataset doesn't include Clark St or the Staten Island Railway. Feel free to
add some data for those if you want better tests. (Since your code should filter them out, the numbers won't change.)

### Grading

**Remember loops are banned.** That said, you can call `collect` on your streams as much as you'd like. There's no
need to have deeply nested collectors in the name of efficiency.

| Item                                                  | Points |
|-------------------------------------------------------|--------|
| Code compiles                                         | 1      |
| Correct artifacts submitted                           | 1      |
| Correct max entries                                   | 1      |
| Correct min entries                                   | 1      |
| Correct max exits                                     | 1      |
| Correct min exits                                     | 1      |
| Correct total riders                                  | 1      |
| Justification for total riders methodology            | 1      |
| No loops (no partial credit; this is the whole point) | 4      |
| Extra Credit                                          | 4      |

### Extra Credit

Write a custom `Collector` that finds the stations with the maximum and minimum entries and exits (i.e. the answers to
the first four questions) simultaneously. (You'll have to either implement your own class to hold the result value or add
a dependency on [javatuples](https://www.javatuples.org/) and use `Quartet`.) If you do this, add an additional function
in `TurnstileExercises` and update `AssignmentMain` to call it in addition to calling the "standard" max/min functions.
(If all went well, you'll get the same answers.)