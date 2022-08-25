package io.testframework.test_framework.service;

import io.testframework.test_framework.domain.Article;
import io.testframework.test_framework.domain.Asset;
import io.testframework.test_framework.domain.Model;
import io.testframework.test_framework.model.AssetDTO;
import io.testframework.test_framework.repos.ArticleRepository;
import io.testframework.test_framework.repos.AssetRepository;
import io.testframework.test_framework.repos.ModelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class AssetService {

    private final AssetRepository assetRepository;
    private final ArticleRepository articleRepository;
    private final ModelRepository modelRepository;

    public AssetService(final AssetRepository assetRepository,
            final ArticleRepository articleRepository,final ModelRepository modelRepository) {
        this.assetRepository = assetRepository;
        this.articleRepository = articleRepository;
        this.modelRepository = modelRepository;
    }

    public List<AssetDTO> findAll() {
        return assetRepository.findAll()
                .stream()
                .map(asset -> mapToDTO(asset, new AssetDTO()))
                .collect(Collectors.toList());
    }

    public AssetDTO get(final UUID id) {
        return assetRepository.findById(id)
                .map(asset -> mapToDTO(asset, new AssetDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UUID create(final AssetDTO assetDTO) {
        final Asset asset = new Asset();
        mapToEntity(assetDTO, asset);
        return assetRepository.save(asset).getId();
    }

    public void update(final UUID id, final AssetDTO assetDTO) {
        final Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(assetDTO, asset);
        assetRepository.save(asset);
    }

    public void delete(final UUID id) {
        assetRepository.deleteById(id);
    }

    private AssetDTO mapToDTO(final Asset asset, final AssetDTO assetDTO) {
        assetDTO.setId(asset.getId());
        assetDTO.setName(asset.getName());
        //assetDTO.setArticleId(asset.getArticleId());
        //assetDTO.setModelId(asset.getModelId());
        assetDTO.setModelId(asset.getModelId() == null ? null : asset.getModelId().getId());
        assetDTO.setAssetArticle(asset.getAssetArticle() == null ? null : asset.getAssetArticle().getId());
        return assetDTO;
    }

    private Asset mapToEntity(final AssetDTO assetDTO, final Asset asset) {
        asset.setName(assetDTO.getName());
        //asset.setAssetArticle(assetDTO.getAssetArticle());
        //asset.setModelId(assetDTO.getModelId());
        if (assetDTO.getModelId() != null && (asset.getModelId() == null || !asset.getModelId().getId().equals(assetDTO.getModelId()))) {
            final Model modelId = modelRepository.findById(assetDTO.getModelId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "modelId not found"));
            asset.setModelId(modelId);
        }
        if (assetDTO.getAssetArticle() != null && (asset.getAssetArticle() == null || !asset.getAssetArticle().getId().equals(assetDTO.getAssetArticle()))) {
            final Article assetArticle = articleRepository.findById(assetDTO.getAssetArticle())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "assetArticle not found"));
            asset.setAssetArticle(assetArticle);
        }
        return asset;
    }

}
