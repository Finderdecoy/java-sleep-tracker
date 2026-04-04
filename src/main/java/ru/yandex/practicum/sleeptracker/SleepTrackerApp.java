package ru.yandex.practicum.sleeptracker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class SleepTrackerApp {

    public static List<SleepingSession> sleepingSessions;
    public static List<Function<List<SleepingSession>, SleepAnalysisResult>> functions = new LinkedList<>();

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(
                new FileReader("src/main/resources/sleep_log.txt", StandardCharsets.UTF_8))) {
            sleepingSessions = br.lines().map(s -> {
                String[] sp = s.split(";");
                return new SleepingSession(sp[0], sp[1], sp[2]);
            }).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        functions.add(new AmountSessions());
        functions.add(new MaximumSleepSessions());
        functions.add(new MinimalSleepSessions());
        functions.add(new AvarageMinutsSession());
        functions.add(new SleepQuality());
        functions.add(new SleeplessNight());
        functions.add(new UserClassification());

        functions.stream().forEach(functions ->
                System.out.println(functions.apply(sleepingSessions)));

    }
}