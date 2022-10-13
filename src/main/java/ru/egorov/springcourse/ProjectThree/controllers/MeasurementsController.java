package ru.egorov.springcourse.ProjectThree.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.egorov.springcourse.ProjectThree.dto.MeasurementDTO;
import ru.egorov.springcourse.ProjectThree.dto.MeasurementsResponse;
import ru.egorov.springcourse.ProjectThree.models.Measurement;
import ru.egorov.springcourse.ProjectThree.services.MeasurementsService;
import ru.egorov.springcourse.ProjectThree.util.*;

import javax.validation.Valid;

import java.util.stream.Collectors;

import static ru.egorov.springcourse.ProjectThree.util.ErrorMessageBuilder.getMessage;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementValidator measurementValidator;
    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementValidator measurementValidator, MeasurementsService measurementsService,
                                  ModelMapper modelMapper) {
        this.measurementValidator = measurementValidator;
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult) {
        Measurement measurement = convertToMeasurement(measurementDTO);

        measurementValidator.validate(measurement, bindingResult);

        if (bindingResult.hasErrors())
            throw new MeasurementNotAddedException(getMessage(bindingResult));

        measurementsService.add(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    public MeasurementsResponse getMeasurements() {
        return new MeasurementsResponse(measurementsService.findAll().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public Integer countRainyDays () {
        return measurementsService.countRainyDays();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotAddedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
