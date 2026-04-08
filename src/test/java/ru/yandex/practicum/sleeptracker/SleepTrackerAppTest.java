package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest {
    public static List<SleepingSession> sleepSessionsTest;
    public static List<SleepingSession> zeroSessions;

    @BeforeEach
    public void before() {
        sleepSessionsTest = new ArrayList<>(List.of(
                new SleepingSession("01.02.26 22:00", "02.02.26 07:00", QualitiSleep.GOOD),
                new SleepingSession("02.02.26 17:00", "02.02.26 23:00", QualitiSleep.BAD),
                new SleepingSession("03.02.26 23:15", "04.02.26 09:30", QualitiSleep.GOOD),
                new SleepingSession("04.02.26 00:00", "05.02.26 13:30", QualitiSleep.NORMAL),
                new SleepingSession("06.02.26 06:15", "06.02.26 14:30", QualitiSleep.GOOD))) {
        };
        zeroSessions = new ArrayList<>();
    }

    // тест среднего времени
    @Test
    public void testAvarageTime() {
        SleepAnalysisResult resultMinuts = new AvarageMinutsSession().apply(sleepSessionsTest);
        assertEquals(852, resultMinuts.getResult(), "Среднее время приведенное к Int");
        //добавим еще 1 запись на 6 часов
        sleepSessionsTest.add(new SleepingSession("06.02.26 06:15", "06.02.26 07:15", QualitiSleep.GOOD));
        resultMinuts = new AvarageMinutsSession().apply(sleepSessionsTest);
        assertEquals(720, resultMinuts.getResult(), "Добавили 60 минут сна.");

        resultMinuts = new AvarageMinutsSession().apply(zeroSessions);
        assertEquals(0, resultMinuts.getResult(), "0 сессий");

    }

    //тест плохого качества сна
    @Test
    public void testBADQualitiSleep() {
        SleepAnalysisResult result = new BadSleepQuality().apply(sleepSessionsTest);
        assertEquals( 1, result.getResult(), "Должен посчитать сессии с плохим качеством сна. Сейчас BAD = 1");

        sleepSessionsTest.add(new SleepingSession("06.02.26 06:15", "06.02.26 08:15", QualitiSleep.BAD));
        result = new BadSleepQuality().apply(sleepSessionsTest);
        assertEquals(2, result.getResult(), "Увеличили количество сессий с плохим сном. Сейчас BAD = 2");
    }

    @Test
    public void testWhithOutBADQualitiSleep() {
        sleepSessionsTest.set(1, new SleepingSession("02.02.26 17:00", "02.02.26 23:00", QualitiSleep.GOOD));
        SleepAnalysisResult result = new BadSleepQuality().apply(zeroSessions);

        assertEquals(0,result.getResult(), "Сессии пустые , соответственно там должен быть 0");
    }

    //Тестировка количества сессий сна
    @Test
    public void testAmountSession() {
        SleepAnalysisResult testResult = new AmountSessions().apply(sleepSessionsTest);
        SleepAnalysisResult expectedResult = new SleepAnalysisResult("Количество сессий", 5);

        assertEquals(expectedResult, testResult, "Должно вернутся SleepingAnalysisResult c верным количество сессий сна и описанием.");
    }

    @Test
    public void testAmountWhitZeroSessions() {
        SleepAnalysisResult testResult = new AmountSessions().apply(zeroSessions);
        SleepAnalysisResult expectedResult = new SleepAnalysisResult("Количество сессий", 0);

        assertEquals(expectedResult, testResult, "Вернется 0 сессий так как список пуст");
    }

    // Тестировка минимальной сессии
    @Test
    public void testMinimalSession() {
        SleepAnalysisResult testResult = new MinimalSleepSessions().apply(sleepSessionsTest);
        SleepAnalysisResult expectedResult = new SleepAnalysisResult(
                "Самое маленькое время которое вы спали(в минутах)", Duration.ofMinutes(360).toMinutes());

        assertEquals(expectedResult, testResult, "Возвращает минимальное время в минутах.");
    }

    @Test
    public void testMinimalOfZeroSession() {
        SleepAnalysisResult testResult = new MinimalSleepSessions().apply(zeroSessions);
        SleepAnalysisResult expectedResult = new SleepAnalysisResult(
                "Самое маленькое время которое вы спали(в минутах)", Duration.ofMinutes(0).toMinutes());

        assertEquals(expectedResult, testResult, "Должно быть 0 ");
    }

    //тест поиска максимального времени продолжительности сна
    @Test
    public void testMaximalSession() {
        SleepAnalysisResult testResult = new MaximumSleepSessions().apply(sleepSessionsTest);
        SleepAnalysisResult expectedResult = new SleepAnalysisResult(
                "Самое большое время которое вы спали(в минутах)", Duration.ofMinutes(2250).toMinutes());

        assertEquals(expectedResult, testResult, "Возвращает максимальное время сна в минутах.");
    }

    @Test
    public void testMaximalSessionWhitZeroSessions() {
        SleepAnalysisResult testResult = new MaximumSleepSessions().apply(zeroSessions);
        SleepAnalysisResult expectedResult = new SleepAnalysisResult(
                "Самое большое время которое вы спали(в минутах)", Duration.ofMinutes(0).toMinutes());

        assertEquals(expectedResult, testResult,
                "Должен вернуть совсем другое описание т.к в листе сессий пусто и максимальную сессию не возможно найти.");
    }

    //тест на бессоные ночи
    @Test
    public void testSleeplesNight() {
        SleepAnalysisResult result = new SleeplessNight().apply(sleepSessionsTest);
        SleepAnalysisResult expected = new SleepAnalysisResult("Количество бессонных ночей", 2L);

        assertEquals(expected, result, "Смотрим количество бессоных ночей...");
    }

    @Test
    public void testSleeplessNightBetweenMonth() {
        sleepSessionsTest.add(0, new SleepingSession("31.01.26 13:00", "01.02.26 00:00", QualitiSleep.GOOD));

        SleepAnalysisResult result = new SleeplessNight().apply(sleepSessionsTest);
        SleepAnalysisResult expected = new SleepAnalysisResult("Количество бессонных ночей", 3L);

        assertEquals(expected, result, "Смотрим количество бессоных ночей... Включая переходящий месяц");
    }

    @Test
    public void testSleeplessNightWhitOutSessions() {
        SleepAnalysisResult result = new SleeplessNight().apply(zeroSessions);
        SleepAnalysisResult expected = new SleepAnalysisResult("Количество бессонных ночей", 0);

        assertEquals(expected, result, "Смотрим количество бессоных ночей... Включая переходящий месяц");
    }

    @Test
    public void testClassificateUserPegion() {
        SleepAnalysisResult result = new UserClassification().apply(sleepSessionsTest);

        assertEquals("Голубь", result.getResult(), "Проверка классификации пользователя. На голубя");
    }

    @Test
    public void testClassificateUserOwl() {
        sleepSessionsTest.add(new SleepingSession("06.02.26 23:15", "07.02.26 10:00", QualitiSleep.GOOD));
        sleepSessionsTest.add(new SleepingSession("07.02.26 23:15", "08.02.26 10:00", QualitiSleep.GOOD));
        sleepSessionsTest.add(new SleepingSession("08.02.26 23:15", "09.02.26 10:00", QualitiSleep.GOOD));
        SleepAnalysisResult result = new UserClassification().apply(sleepSessionsTest);

        assertEquals("Сова", result.getResult(), "Проверка классификации пользователя. На сову");
    }

    @Test
    public void testClassificateUserLark() {
        sleepSessionsTest.add(new SleepingSession("06.02.26 21:15", "07.02.26 06:50", QualitiSleep.GOOD));
        sleepSessionsTest.add(new SleepingSession("07.02.26 21:15", "08.02.26 06:50", QualitiSleep.GOOD));
        sleepSessionsTest.add(new SleepingSession("08.02.26 21:15", "09.02.26 06:50", QualitiSleep.GOOD));
        SleepAnalysisResult result = new UserClassification().apply(sleepSessionsTest);

        assertEquals("Жаворонок", result.getResult(), "Проверка классификации пользователя. На жаворонок");
    }
}