package ru.egorov.springcourse.ProjectThree.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egorov.springcourse.ProjectThree.models.Sensor;
import ru.egorov.springcourse.ProjectThree.repositories.SensorsRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    @Transactional
    public Sensor register(Sensor sensor) {
        return sensorsRepository.save(sensor);
    }

    public Optional<Sensor> getSensorByName(String name) {
        return sensorsRepository.getSensorByName(name);
    }
}
