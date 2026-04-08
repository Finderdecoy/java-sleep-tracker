package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class AmountSessions implements Function<List<SleepingSession>, SleepAnalysisResult> {
    public static final String AMOUNT_SESSION = "Количество сессий";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSession) {
        return new SleepAnalysisResult(AMOUNT_SESSION, sleepingSession.size());
    }
}
