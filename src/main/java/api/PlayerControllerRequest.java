package api;

import dto.reponse.PlayerResponse;
import dto.request.PlayerRequest;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PlayerControllerRequest extends AbstractRequest {

    private final String url = "/player";

    @Step("Creating the new player by role \"{editor}\"")
    public PlayerResponse createPlayer(PlayerRequest playerRequest, int expectedCode) {
        RequestSpecification requestSpecification = given()
                .queryParam("age", playerRequest.getAge())
                .queryParam("gender", playerRequest.getGender())
                .queryParam("login", playerRequest.getLogin())
                .queryParam("password", playerRequest.getPassword())
                .queryParam("role", playerRequest.getRole())
                .queryParam("screenName", playerRequest.getScreenName())
                .pathParam("editor", playerRequest.getEditor());

        return infoLogger(get(url + "/create/{editor}", requestSpecification, expectedCode));
    }

    @Step("Delete the player with id \"{playerId}\" by role \"{editor}\"")
    public void deletePlayer(String playerId, String editor, int expectedCode) {
        Map<String, String> body = Map.of("playerId", playerId);
        RequestSpecification requestSpecification = given()
                .body(body)
                .pathParam("editor", editor);

        delete(url + "/delete/{editor}", requestSpecification, expectedCode);
    }

    @Step("Get information about the player with id \"{playerId}\"")
    public PlayerResponse getPlayerDataById(String playerId, int expectedCode) {
        Map<String, String> body = Map.of("playerId", playerId);
        RequestSpecification requestSpecification = given()
                .body(body);

        return infoLogger(post(url + "/get", requestSpecification, expectedCode));
    }

    @Step("Get information about all players")
    public List<PlayerResponse> getAllPlayers(int expectedCode) {
        RequestSpecification requestSpecification = given();
        return get(url + "/get/all", requestSpecification, expectedCode)
                .jsonPath().getList("players", PlayerResponse.class);
    }

    @Step("Update the player with id \"{playerId}\" by role \"{editor}\"")
    public PlayerResponse updatePlayer(PlayerRequest playerRequest, String editor, String playerId, int expectedCode) {
        RequestSpecification requestSpecification = given()
                .body(playerRequest)
                .pathParam("editor", editor)
                .pathParam("id", playerId);

        return infoLogger(patch(url + "/update/{editor}/{id}", requestSpecification, expectedCode));
    }
}
