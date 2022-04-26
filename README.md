# CSc 22100 Assignment 6

The focus of this assignment is the Streams API and Functional Interfaces. This means that **loops are banned** in this assignment,
you have to use streams rather than `for` or `while`. We will play around [MTA Turnstile Data](http://web.mta.info/developers/turnstile.html) from last week.

The turnstile data is published weekly as a CSV. The definitions of the columns can be found [here](http://web.mta.info/developers/resources/nyct/turnstile/ts_Field_Description.txt). Some things to know:
- Turnstiles are uniquely identified by the tuple `(C/A, UNIT, SCP)`. Stations are uniquely identified by the tuple `(STATION, LINENAME)` (in other words, Penn Station on the A/C/E and Penn Station on the 1/2/3 are different).
- The `entries` and `exits` are counts of how many times the turnstile rotated in a given direction, which means to find how many people entered/exited in a given period, you need to subtract two consecutive entry/exit counts. For example, here are two consecutive records for (one turnstile at) 53rd Street on the R:
    ```
    C020	R233	00-03-01	53 ST	R	BMT	REGULAR	196568	347669	2022-04-16 08:00:00.000
    C020	R233	00-03-01	53 ST	R	BMT	REGULAR	196619	347726	2022-04-16 12:00:00.000
    ```
    At 8am, the turstile had seen 196568 entries and at 12pm it had seen 196619 entries, i.e. 51 people entered in those four hours.
- Some turnstiles seem to count down rather than up. Take the absolute value.
- There is the possibility of the counts overflowing (they handle up to 10 digits), but that didn't happen last week.
- Some of the data is obviously incorrect. I feel comfortable assuming 926,340,484 people did not enter the subway at Hunts Point in four hours last Wednesday morning!


## Instructions

1. Like the last assignment, start off by downloading the data published 4/23 by running `./gradlew download` (or just grab it from the MTA site).
2. Filter out any cases where more than 2000 entries or exits are recorded in a "period".
3. Implement the various functions in `TurnstileExercises.java` to answer the following questions:
    - Which station had the most entries (in total) during the week? Which station had the least? How many?
    - Which station had the most exits (in total) during the week? Which station had the least? How many?
    - How many riders were there each day? Make sure you include a comment saying how you chose to calculate this and why.
