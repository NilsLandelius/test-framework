package io.testframework.test_framework.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.testframework.test_framework.model.ModelDTO;
import io.testframework.test_framework.repos.ModelRepository;
import io.testframework.test_framework.utilities.JsonResourceReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModelServiceTest {

    @Mock
    ModelRepository modelRepositoryMock;

    JsonResourceReader reader;

    ObjectMapper mapper = new ObjectMapper();

    ModelService sut;

    @BeforeEach
    public void init(){
        this.sut = new ModelService(modelRepositoryMock);
    }

    @Test
    public void firstTest() throws IOException {
        System.out.println(getTestModels("./testdata/modelDTO.json").get(0).getModelName());
    }


    private List<ModelDTO> getTestModels(String filepath) throws IOException {
        reader = new JsonResourceReader();
        JsonNode node = reader.getJsonFileAsJsonNode(filepath);
        List<ModelDTO> dtoList = new ArrayList<>();
        for (JsonNode model: node) {
            dtoList.add(mapper.treeToValue(model,ModelDTO.class));
        }
        return dtoList;
    }


}
