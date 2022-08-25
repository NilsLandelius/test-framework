package io.testframework.test_framework.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonResourceReader implements ResourceReader  {

    public JsonNode getJsonFileAsJsonNode(String filename) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = getResourceFileAsString(filename);
        JsonNode node = mapper.readTree(jsonString);
        return node;
    }
}
