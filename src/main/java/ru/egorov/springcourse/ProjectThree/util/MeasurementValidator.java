package ru.egorov.springcourse.ProjectThree.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.egorov.springcourse.ProjectThree.models.Measurement;
import ru.egorov.springcourse.ProjectThree.services.SensorsService;

@Component
public class MeasurementValidator implements Validator {
    private final SensorsService sensorsService;

    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        if (sensorsService.getSensorByName(measurement.getSensor().getName()).isEmpty())
            errors.rejectValue("sensor", "", "A sensor with this name is not registered!");
    }
}
