package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class MaximumSleepSessions implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSession) {
        return sleepingSession.stream()
                .map(session -> Duration.between(session.getStartSleep(), session.getFinishSleep()))
                .max(Duration::compareTo)
                .map(duration ->
                        new SleepAnalysisResult("Самое большое время которое вы спали(в минутах)",
                                duration.toMinutes()))
                .orElse(new SleepAnalysisResult("Не найденна сессия с самым большим временем", "-"));
    }
}
