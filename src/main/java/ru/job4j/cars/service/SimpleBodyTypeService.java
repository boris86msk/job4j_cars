package ru.job4j.cars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.BodyType;
import ru.job4j.cars.repository.BodyTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SimpleBodyTypeService implements BodyTypeService {
    private final BodyTypeRepository bodyTypeRepository;

    public SimpleBodyTypeService(BodyTypeRepository bodyTypeRepository) {
        this.bodyTypeRepository = bodyTypeRepository;
    }

    @Override
    public List<BodyType> findAllType() {
        return bodyTypeRepository.findAllType();
    }

    @Override
    public Optional<BodyType> findById(int id) {
        return bodyTypeRepository.findById(id);
    }
}
