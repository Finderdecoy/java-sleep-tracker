package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SleepingSession {
    private LocalDateTime startSleep;
    private LocalDateTime finishSleep;
    private QualitiSleep qualityOfSleep;
    private DateTimeFormatter formatterInput = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public SleepingSession(String startSleep, String finishSleep, QualitiSleep qualityOfSleep) {
        this.startSleep = LocalDateTime.parse(startSleep, formatterInput);
        this.finishSleep = LocalDateTime.parse(finishSleep, formatterInput);
        this.qualityOfSleep = qualityOfSleep;
    }

    public LocalDateTime getStartSleep() {
        return startSleep;
    }

    public LocalDateTime getFinishSleep() {
        return finishSleep;
    }

    public QualitiSleep getQualityOfSleep() {
        return qualityOfSleep;
    }

    @Override
    public String toString() {
        return "{" +
                "начало сна " + startSleep +
                ", окончание сна " + finishSleep +
                ", qualityOfSleep='" + qualityOfSleep + '\'' +
                '}';
    }
}
