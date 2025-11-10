package helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class JsonReader {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String DEFAULT_FILE = "src/test/resources/" + Config.get("players.data.file.name");

    public static <T> T getPlayer(String playerKey, Class<T> clazz) {
        try {
            JsonNode root = mapper.readTree(new File(DEFAULT_FILE));

            if (!root.has(playerKey)) {
                throw new IllegalArgumentException("Key '" + playerKey + "' not found in JSON");
            }

            JsonNode playerNode = root.get(playerKey);

            return mapper.treeToValue(playerNode, clazz);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
