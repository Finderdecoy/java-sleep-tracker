package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class AvarageMinutsSession implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        var avarageMinuts = sleepingSessions.stream()
                .mapToDouble(sp -> Duration.between(sp.getStartSleep(), sp.getFinishSleep()).toMinutes())
                .average();

        if (avarageMinuts.isPresent()) {
            return new SleepAnalysisResult("Среднее количество минут которое вы спали", (int) avarageMinuts.getAsDouble());
        }
        return new SleepAnalysisResult("Не возможно вычислить среднее количество минут", -1);
    }
}
