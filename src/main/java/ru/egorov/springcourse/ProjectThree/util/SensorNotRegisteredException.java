package ru.egorov.springcourse.ProjectThree.util;

public class SensorNotRegisteredException extends RuntimeException {
    public SensorNotRegisteredException(String message) {
        super(message);
    }
}
