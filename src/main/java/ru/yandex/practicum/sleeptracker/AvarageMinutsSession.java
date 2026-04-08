package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class AvarageMinutsSession implements Function<List<SleepingSession>, SleepAnalysisResult> {
    public static final String AVARAGE_SLEEP = "Средняя продолжительность сна";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        double result = sleepingSessions.stream()
                .mapToDouble(sp -> Duration.between(sp.getStartSleep(), sp.getFinishSleep()).toMinutes())
                .average().orElse(0);


        return new SleepAnalysisResult(AVARAGE_SLEEP, (int) result);
    }
}
