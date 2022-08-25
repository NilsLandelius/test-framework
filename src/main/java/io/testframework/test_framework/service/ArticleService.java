package io.testframework.test_framework.service;

import io.testframework.test_framework.domain.Article;
import io.testframework.test_framework.domain.Model;
import io.testframework.test_framework.model.ArticleDTO;
import io.testframework.test_framework.repos.ArticleRepository;
import io.testframework.test_framework.repos.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelRepository modelRepository;

    @Autowired
    public ArticleService(final ArticleRepository articleRepository,
            final ModelRepository modelRepository) {
        this.articleRepository = articleRepository;
        this.modelRepository = modelRepository;
    }

    public List<ArticleDTO> findAll() {
        return articleRepository.findAll()
                .stream()
                .map(article -> mapToDTO(article, new ArticleDTO()))
                .collect(Collectors.toList());
    }

    public ArticleDTO get(final UUID id) {
        return articleRepository.findById(id)
                .map(article -> mapToDTO(article, new ArticleDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UUID create(final ArticleDTO articleDTO) {
        final Article article = new Article();
        mapToEntity(articleDTO, article);
        return articleRepository.save(article).getId();
    }

    public void update(final UUID id, final ArticleDTO articleDTO) {
        final Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(articleDTO, article);
        articleRepository.save(article);
    }

    public void delete(final UUID id) {
        articleRepository.deleteById(id);
    }

    private ArticleDTO mapToDTO(final Article article, final ArticleDTO articleDTO) {
        articleDTO.setId(article.getId());
        //articleDTO.setArticleId(article.getArticleId());
        articleDTO.setArticleType(article.getArticleType());
        articleDTO.setModelArticle(article.getModelArticle() == null ? null : article.getModelArticle().getId());
        return articleDTO;
    }

    private Article mapToEntity(final ArticleDTO articleDTO, final Article article) {
        //article.setArticleId(articleDTO.getArticleId());
        article.setArticleType(articleDTO.getArticleType());
        if (articleDTO.getModelArticle() != null && (article.getModelArticle() == null || !article.getModelArticle().getId().equals(articleDTO.getModelArticle()))) {
            final Model modelArticle = modelRepository.findById(articleDTO.getModelArticle())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "modelArticle not found"));
            article.setModelArticle(modelArticle);
        }
        return article;
    }

}
