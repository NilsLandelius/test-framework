package io.testframework.test_framework.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.testframework.test_framework.model.AssetDTO;
import io.testframework.test_framework.service.AssetService;
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
@RequestMapping(value = "/api/assets", produces = MediaType.APPLICATION_JSON_VALUE)
public class AssetResource {

    private final AssetService assetService;

    public AssetResource(final AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping
    public ResponseEntity<List<AssetDTO>> getAllAssets() {
        return ResponseEntity.ok(assetService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetDTO> getAsset(@PathVariable final UUID id) {
        return ResponseEntity.ok(assetService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createAsset(@RequestBody @Valid final AssetDTO assetDTO) {
        return new ResponseEntity<>(assetService.create(assetDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAsset(@PathVariable final UUID id,
            @RequestBody @Valid final AssetDTO assetDTO) {
        assetService.update(id, assetDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAsset(@PathVariable final UUID id) {
        assetService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
