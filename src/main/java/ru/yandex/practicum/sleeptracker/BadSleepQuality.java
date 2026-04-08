package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadSleepQuality implements Function<List<SleepingSession>, SleepAnalysisResult> {
    public static final String BAD_SESSION_SLEEP = "Сессии с плохим качеством сна";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        long result = sleepingSessions.stream().filter(sp -> sp.getQualityOfSleep()
                .equals(QualitiSleep.BAD)).count();

        return new SleepAnalysisResult(BAD_SESSION_SLEEP, (int) result);
    }
}
