package ru.yandex.practicum.sleeptracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
                QualitiSleep qual = null;
                switch (sp[2]) {
                    case "GOOD" -> qual = QualitiSleep.GOOD;
                    case "BAD" -> qual = QualitiSleep.BAD;
                    case "NORMAL" -> qual = QualitiSleep.NORMAL;
                }
                return new SleepingSession(sp[0], sp[1], qual);
            }).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        functions.add(new AmountSessions());
        functions.add(new MaximumSleepSessions());
        functions.add(new MinimalSleepSessions());
        functions.add(new AvarageMinutsSession());
        functions.add(new BadSleepQuality());
        functions.add(new SleeplessNight());
        functions.add(new UserClassification());

        functions.stream().forEach(functions ->
                System.out.println(functions.apply(sleepingSessions)));

    }
}