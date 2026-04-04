package ru.yandex.practicum.sleeptracker;

import java.time.*;
import java.util.List;
import java.util.function.Function;

public class SleeplessNight implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {

        if (sleepingSessions.isEmpty()) {
            return new SleepAnalysisResult("Список пуст.Нельзя посчитать количество бессоных ночей", -1);
        }

        LocalDate firstDayOfSession;
        if (sleepingSessions.getFirst().getStartSleep().toLocalTime().isAfter(LocalTime.NOON)) {
            firstDayOfSession = sleepingSessions.getFirst().getStartSleep().toLocalDate().plusDays(1);
        } else {
            firstDayOfSession = sleepingSessions.getFirst().getStartSleep().toLocalDate();
        }
        LocalDate lastDayOfSession = sleepingSessions.getLast().getStartSleep().toLocalDate().plusDays(1);

        long sleeplessNightsCount = firstDayOfSession.datesUntil(lastDayOfSession, Period.ofDays(1))
                .filter(date -> {
                    LocalDateTime twentyClocks = date.atStartOfDay();
                    LocalDateTime sixClocks = date.atTime(6, 0);

                    return sleepingSessions.stream().noneMatch(sp ->
                            (sp.getStartSleep().isBefore(sixClocks) && sp.getFinishSleep().isAfter(twentyClocks)
                            ));
                }).count();
        return new SleepAnalysisResult("Количество бессонных ночей", sleeplessNightsCount);
    }
}
