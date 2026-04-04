package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class UserClassification implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSession) {
        HashMap<String, Integer> classificateMap = new HashMap<>();

        sleepingSession.stream().filter(s -> {
            LocalDateTime start = s.getStartSleep();
            LocalDateTime end = s.getFinishSleep();
            return start.isBefore(LocalDateTime.of(end.toLocalDate(), LocalTime.of(6, 00))) && end.isAfter(LocalDateTime.of(end.toLocalDate(), LocalTime.MIDNIGHT));
        }).forEach(sp -> {
            LocalDateTime elevenOcklock = sp.getStartSleep().toLocalDate().atTime(23, 0);
            LocalDateTime ningeOcklock = sp.getFinishSleep().toLocalDate().atTime(9, 0);
            LocalDateTime tenOcklock = sp.getStartSleep().toLocalDate().atTime(22, 0);
            LocalDateTime sevenOcklock = sp.getFinishSleep().toLocalDate().atTime(7, 0);
            if (sp.getStartSleep().isAfter(elevenOcklock) && sp.getFinishSleep().isAfter(ningeOcklock)) {
                classificateMap.put("Сова", classificateMap.getOrDefault("Сова", 0) + 1);
            } else if (sp.getStartSleep().isBefore(tenOcklock) && sp.getFinishSleep().isBefore(sevenOcklock)) {
                classificateMap.put("Жаворонок", classificateMap.getOrDefault("Жаворонок", 0) + 1);
            } else {
                classificateMap.put("Голубь", classificateMap.getOrDefault("Голубь", 0) + 1);
            }
        });

        String resultType = classificateMap.entrySet().stream()
                .max((entry1, entry2) -> {
                    int compare = entry1.getValue().compareTo(entry2.getValue());
                    if (compare == 0) {
                        if (entry1.getKey().equals("Голубь")) return 1;
                        if (entry2.getKey().equals("Голубь")) return -1;
                    }
                    return compare;
                })
                .map(Map.Entry::getKey)
                .orElse("Голубь");

        return new SleepAnalysisResult("Ваш класс сна: ", resultType);
    }
}
