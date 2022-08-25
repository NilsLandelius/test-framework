package io.testframework.test_framework.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;


@Getter
@Setter
public class ArticleDTO {

    private UUID id;

//    @NotNull
//    private Integer articleId;

    @NotNull
    @Size(max = 255)
    private String articleType;

    @NotNull
    private UUID modelArticle;

}
