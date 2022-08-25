package io.testframework.test_framework.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;


@Getter
@Setter
public class AssetDTO {

    private UUID id;

    @Size(max = 255)
    private String name;

//    @NotNull
//    private Integer articleId;

    @NotNull
    private UUID modelId;

    @NotNull
    private UUID assetArticle;

}
