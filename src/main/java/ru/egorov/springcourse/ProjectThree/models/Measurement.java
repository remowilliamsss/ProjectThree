package ru.egorov.springcourse.ProjectThree.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
public class Measurement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "value")
    @NotNull(message = "The \"value\" field cannot be empty")
    @Min(value = -100, message = "Value should be between -100 and 100")
    @Max(value = 100, message = "Value should be between -100 and 100")
    private Double value;

    @Column(name = "raining")
    @NotNull(message = "The \"raining\" field cannot be empty")
    private Boolean raining;

    @Column(name = "measurement_date_time")
    @NotNull(message = "The \"measurement date time\" field cannot be empty")
    private LocalDateTime measurementDateTime;

    @NotNull(message = "The \"sensor\" field cannot be empty")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sensor", referencedColumnName = "name")
    private Sensor sensor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean isRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public LocalDateTime getMeasurementDateTime() {
        return measurementDateTime;
    }

    public void setMeasurementDateTime(LocalDateTime measurementDateTime) {
        this.measurementDateTime = measurementDateTime;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
