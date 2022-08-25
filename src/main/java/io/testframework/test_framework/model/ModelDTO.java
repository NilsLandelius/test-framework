package io.testframework.test_framework.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.UUID;


@Getter
@Setter
public class ModelDTO {

    private UUID id;

    private Integer modelId;

    @Size(max = 255)
    private String modelName;

    @Size(max = 255)
    private String modelType;

}
