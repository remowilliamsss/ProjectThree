package ru.egorov.springcourse.ProjectThree.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.egorov.springcourse.ProjectThree.models.Sensor;
import ru.egorov.springcourse.ProjectThree.services.SensorsService;

@Component
public class SensorValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if (sensorsService.getSensorByName((sensor.getName())).isPresent())
            errors.rejectValue("name", "", "A sensor with this name already exists");
    }
}
