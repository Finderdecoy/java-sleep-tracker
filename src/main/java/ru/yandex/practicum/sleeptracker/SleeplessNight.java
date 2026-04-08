package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;
import java.util.function.Function;

public class SleeplessNight implements Function<List<SleepingSession>, SleepAnalysisResult> {
    public static final String COUNT_SLEEPLESS_SESSION = "Количество бессонных ночей";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {

        if (sleepingSessions.isEmpty()) {
            return new SleepAnalysisResult(COUNT_SLEEPLESS_SESSION, 0);
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
        return new SleepAnalysisResult(COUNT_SLEEPLESS_SESSION, sleeplessNightsCount);
    }
}
