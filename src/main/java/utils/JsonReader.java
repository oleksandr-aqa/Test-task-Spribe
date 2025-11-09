package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.reponse.PlayerResponse;
import dto.request.PlayerRequest;

import java.io.File;

public class JsonReader {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String DEFAULT_FILE = "src/test/resources/" + Config.get("players.data.file.name");

    public static PlayerResponse getPlayerResponse(String playerKey) {
        try {
            JsonNode root = mapper.readTree(new File(DEFAULT_FILE));

            if (!root.has(playerKey)) {
                throw new IllegalArgumentException("Key '" + playerKey + "' not found in JSON");
            }

            JsonNode playerNode = root.get(playerKey);

            if (!playerNode.has("id")) {
                throw new IllegalArgumentException("Player '" + playerKey + "' does not have id field");
            }

            return mapper.treeToValue(playerNode, PlayerResponse.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PlayerRequest getPlayerRequest(String playerKey) {
        try {
            JsonNode root = mapper.readTree(new File(DEFAULT_FILE));

            if (!root.has(playerKey)) {
                throw new IllegalArgumentException("Key '" + playerKey + "' not found in JSON");
            }

            JsonNode playerNode = root.get(playerKey);

            return mapper.treeToValue(playerNode, PlayerRequest.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
