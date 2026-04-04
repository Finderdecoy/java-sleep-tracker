package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult<T> {
    private String description;
    private T result;

    public SleepAnalysisResult(String description, T result) {
        this.description = description;
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return description + ": " + result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        SleepAnalysisResult<?> that = (SleepAnalysisResult<?>) o;
        return description.equals(that.description) && getResult().equals(that.getResult());
    }

    @Override
    public int hashCode() {
        int result1 = description.hashCode();
        result1 = 31 * result1 + getResult().hashCode();
        return result1;
    }
}
