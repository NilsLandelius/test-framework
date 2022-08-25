package io.testframework.test_framework.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.testframework.test_framework.model.ModelDTO;
import io.testframework.test_framework.service.ModelService;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/models", produces = MediaType.APPLICATION_JSON_VALUE)
public class ModelResource {

    private final ModelService modelService;

    public ModelResource(final ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping
    public ResponseEntity<List<ModelDTO>> getAllModels() {
        return ResponseEntity.ok(modelService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelDTO> getModel(@PathVariable final UUID id) {
        return ResponseEntity.ok(modelService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createModel(@RequestBody @Valid final ModelDTO modelDTO) {
        return new ResponseEntity<>(modelService.create(modelDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateModel(@PathVariable final UUID id,
            @RequestBody @Valid final ModelDTO modelDTO) {
        modelService.update(id, modelDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteModel(@PathVariable final UUID id) {
        modelService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
