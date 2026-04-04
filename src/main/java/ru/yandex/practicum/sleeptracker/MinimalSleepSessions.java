package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.function.Function;
import java.util.List;


public class MinimalSleepSessions implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSession) {
        return sleepingSession.stream()
                .map(session -> Duration.between(session.getStartSleep(), session.getFinishSleep()))
                .min(Duration::compareTo)
                .map(duration ->
                        new SleepAnalysisResult("Самое маленькое время которое вы спали(в минутах)",
                                duration.toMinutes()))
                .orElse(new SleepAnalysisResult("Не найдена минимальная сессия сна", "-"));
    }
}
