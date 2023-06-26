package utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonUtils {

    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

    private static JsonReader getJsonReader(String jsonPath) {
        try {
            JsonReader reader;
            reader = new JsonReader(new FileReader(jsonPath));
            return reader;
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }
        throw new RuntimeException("Cannot read json file from " + jsonPath);
    }

    /**
     * Converting a JSON string to equivalent Array Java Object from json file
     */
    public static <T> T[] toArrayObject(String jsonPath, Class<T[]> clazz) {
        log.debug("Load array object from {}", jsonPath);
        JsonReader reader = getJsonReader(jsonPath);
        Gson gson = new Gson();
        return gson.fromJson(reader, clazz);
    }

    /**
     * Converting a JSON string to equivalent Java object from json file
     */
    public static <T> T toObject(String jsonPath, Class<T> clazz) {
        log.info("Load object from {}", jsonPath);
        JsonReader reader = getJsonReader(jsonPath);
        Gson gson = new Gson();
        return gson.fromJson(reader, clazz);
    }
}
