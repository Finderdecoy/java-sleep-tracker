package ru.yandex.practicum.sleeptracker;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class SleepQuality implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        HashMap<String, Integer> mapQualiti = new HashMap<>();
        sleepingSessions.stream().forEach(sp -> {
            switch (sp.getQualityOfSleep()) {
                case "GOOD" -> mapQualiti.put("GOOD", mapQualiti.getOrDefault("GOOD", 0) + 1);
                case "NORMAL" -> mapQualiti.put("NORMAL", mapQualiti.getOrDefault("NORMAL", 0) + 1);
                case "BAD" -> mapQualiti.put("BAD", mapQualiti.getOrDefault("BAD", 0) + 1);
            }
        });

        return new SleepAnalysisResult("Сессии с плохим качеством сна", mapQualiti.get("BAD"));
    }
}
