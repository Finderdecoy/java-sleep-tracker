package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


public class UserClassification implements Function<List<SleepingSession>, SleepAnalysisResult> {
    public static final String CLASSIFICATE_SLEEP = "Ваш класс сна";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSession) {
        HashMap<Chronotype, Integer> classificateMap = new HashMap<>();

        sleepingSession.stream().filter(s -> {
            LocalDateTime midnight = s.getFinishSleep().toLocalDate().atStartOfDay();
            LocalDateTime sixOclock = s.getFinishSleep().toLocalDate().atTime(6, 0);

            return s.getStartSleep().isBefore(sixOclock) && s.getFinishSleep().isAfter(midnight);
        }).forEach(sp -> {
            LocalDateTime elevenOcklock = sp.getStartSleep().toLocalDate().atTime(23, 0);
            LocalDateTime ningeOcklock = sp.getFinishSleep().toLocalDate().atTime(9, 0);
            LocalDateTime tenOcklock = sp.getStartSleep().toLocalDate().atTime(22, 0);
            LocalDateTime sevenOcklock = sp.getFinishSleep().toLocalDate().atTime(7, 0);

            if (sp.getStartSleep().isAfter(elevenOcklock) && sp.getFinishSleep().isAfter(ningeOcklock)) {
                classificateMap.put(Chronotype.OWL, classificateMap.getOrDefault(Chronotype.OWL, 0) + 1);
            } else if (sp.getStartSleep().isBefore(tenOcklock) && sp.getFinishSleep().isBefore(sevenOcklock)) {
                classificateMap.put(Chronotype.LARK, classificateMap.getOrDefault(Chronotype.LARK, 0) + 1);
            } else {
                classificateMap.put(Chronotype.PEGION, classificateMap.getOrDefault(Chronotype.PEGION, 0) + 1);
            }
        });

        Chronotype resultType = classificateMap.entrySet().stream()
                .max((entry1, entry2) -> {
                    int compare = entry1.getValue().compareTo(entry2.getValue());
                    if (compare == 0) {
                        if (entry1.getKey().equals(Chronotype.PEGION)) return 1;
                        if (entry2.getKey().equals(Chronotype.PEGION)) return -1;
                    }
                    return compare;
                })
                .map(Map.Entry::getKey)
                .orElse(Chronotype.PEGION);

        String result;
        switch (resultType) {
            case OWL -> result = "Сова";
            case LARK -> result = "Жаворонок";
            default -> result = "Голубь";
        }
        return new SleepAnalysisResult(CLASSIFICATE_SLEEP, result);
    }
}
