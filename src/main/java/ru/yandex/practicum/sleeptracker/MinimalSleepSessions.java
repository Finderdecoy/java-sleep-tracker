package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;


public class MinimalSleepSessions implements Function<List<SleepingSession>, SleepAnalysisResult> {
    public static final String MINIMAL_SLEEP = "Самое маленькое время которое вы спали(в минутах)";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSession) {
        var result = sleepingSession.stream()
                .map(session -> Duration.between(session.getStartSleep(), session.getFinishSleep()))
                .min(Duration::compareTo).orElse(Duration.ofMinutes(0));


        return new SleepAnalysisResult(MINIMAL_SLEEP, result.toMinutes());
    }
}
