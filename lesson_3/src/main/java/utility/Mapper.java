package utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;

@UtilityClass
public class Mapper {

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> Map<String, String> convertObjectToMap(T t) {
        return mapper.convertValue(t, Map.class);
    }

    @SneakyThrows
    public <T> List<T> convertStringToList(String jsonString, Class<T> tClass) {
        return mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, tClass));
    }
}
