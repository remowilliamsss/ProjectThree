package ru.egorov.springcourse.ProjectThree.util;

public class MeasurementNotAddedException extends RuntimeException {
    public MeasurementNotAddedException(String message) {
        super(message);
    }
}
