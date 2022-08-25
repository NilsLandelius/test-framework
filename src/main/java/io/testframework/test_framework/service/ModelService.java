package io.testframework.test_framework.service;

import io.testframework.test_framework.domain.Model;
import io.testframework.test_framework.model.ModelDTO;
import io.testframework.test_framework.repos.ModelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ModelService {

    private final ModelRepository modelRepository;

    public ModelService(final ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    public List<ModelDTO> findAll() {
        return modelRepository.findAll()
                .stream()
                .map(model -> mapToDTO(model, new ModelDTO()))
                .collect(Collectors.toList());
    }

    public ModelDTO get(final UUID id) {
        return modelRepository.findById(id)
                .map(model -> mapToDTO(model, new ModelDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UUID create(final ModelDTO modelDTO) {
        final Model model = new Model();
        mapToEntity(modelDTO, model);
        return modelRepository.save(model).getId();
    }

    public void update(final UUID id, final ModelDTO modelDTO) {
        final Model model = modelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(modelDTO, model);
        modelRepository.save(model);
    }

    public void delete(final UUID id) {
        modelRepository.deleteById(id);
    }

    private ModelDTO mapToDTO(final Model model, final ModelDTO modelDTO) {
        modelDTO.setId(model.getId());
        modelDTO.setModelId(model.getModelId());
        modelDTO.setModelName(model.getModelName());
        modelDTO.setModelType(model.getModelType());
        return modelDTO;
    }

    private Model mapToEntity(final ModelDTO modelDTO, final Model model) {
        model.setModelId(modelDTO.getModelId());
        model.setModelName(modelDTO.getModelName());
        model.setModelType(modelDTO.getModelType());
        return model;
    }

}
