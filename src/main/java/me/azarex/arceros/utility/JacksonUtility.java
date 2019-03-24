package me.azarex.arceros.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class JacksonUtility {

    public static Map<String, Object> fromJson(Path path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<Map<String, String>> reference = new TypeReference<>(){};

            return mapper.readValue(path.toFile(), reference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
