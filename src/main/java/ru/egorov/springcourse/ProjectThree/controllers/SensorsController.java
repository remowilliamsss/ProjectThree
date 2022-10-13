package ru.egorov.springcourse.ProjectThree.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.egorov.springcourse.ProjectThree.dto.SensorDTO;
import ru.egorov.springcourse.ProjectThree.models.Sensor;
import ru.egorov.springcourse.ProjectThree.services.SensorsService;
import ru.egorov.springcourse.ProjectThree.util.ErrorMessageBuilder;
import ru.egorov.springcourse.ProjectThree.util.SensorErrorResponse;
import ru.egorov.springcourse.ProjectThree.util.SensorNotRegisteredException;
import ru.egorov.springcourse.ProjectThree.util.SensorValidator;

import javax.validation.Valid;

import static ru.egorov.springcourse.ProjectThree.util.ErrorMessageBuilder.getMessage;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(SensorsService sensorsService,
                             ModelMapper modelMapper,
                             SensorValidator sensorValidator) {
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
                                                   BindingResult bindingResult) {
        Sensor sensor = convertToSensor(sensorDTO);

        sensorValidator.validate(sensor, bindingResult);

        if (bindingResult.hasErrors())
            throw new SensorNotRegisteredException(getMessage(bindingResult));

        sensorsService.register(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotRegisteredException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
