package api;

import dto.reponse.PlayerResponse;
import dto.request.PlayerRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static dto.request.PlayerRequest.getDefaultPlayerAdminRole;
import static dto.request.PlayerRequest.getDefaultPlayerUserRole;
import static io.restassured.RestAssured.given;

public class PlayerControllerRequest extends MainRequest {

    private static final Logger log = LoggerFactory.getLogger(PlayerControllerRequest.class);
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
        Response response = get(url + "/create/{editor}", requestSpecification, expectedCode);
        return infoLogger(response);
    }

    @Step("Delete the player with id \"{playerId}\" by role \"{editor}\"")
    public void deletePlayer(String playerId, String editor, int expectedCode) {
        RequestSpecification requestSpecification = given()
                .body("{ \"playerId\": \"" + playerId + "\" }")
                .pathParam("editor", editor);
        delete(url + "/delete/{editor}", requestSpecification, expectedCode);
    }

    @Step("Get information about the player with id \"{playerId}\"")
    public PlayerResponse getPlayerDataById(String playerId, int expectedCode) {
        RequestSpecification requestSpecification = given()
                .body("{ \"playerId\": \"" + playerId + "\" }");
        Response response = post(url + "/get", requestSpecification, expectedCode);
        return infoLogger(response);
    }

    @Step("Get information about all players")
    public List<PlayerResponse> getAllPlayers(int expectedCode) {
        RequestSpecification requestSpecification = given();
        Response response = get(url + "/get/all", requestSpecification, expectedCode);
        log.info("Response: {} - {}", response.getStatusCode(), response.getBody().asString());
        return response.jsonPath().getList("players", PlayerResponse.class);
    }

    @Step("Update the player with id \"{playerId}\" by role \"{editor}\"")
    public PlayerResponse updatePlayer(PlayerRequest playerRequest, String editor, String playerId, int expectedCode) {
        RequestSpecification requestSpecification = given()
                .body(playerRequest)
                .pathParam("editor", editor)
                .pathParam("id", playerId);
        Response response = patch(url + "/update/{editor}/{id}", requestSpecification, expectedCode);
        return infoLogger(response);
    }

    public PlayerResponse createValidPlayerWithAdminRole() {
        return createPlayer(getDefaultPlayerAdminRole(), 200);
    }

    public PlayerResponse createValidPlayerWithUserRole() {
        return createPlayer(getDefaultPlayerUserRole(), 200);
    }

    private PlayerResponse infoLogger(Response response) {
        log.info("Response: {} - {}", response.getStatusCode(), response.getBody().asString());
        if (response.getBody().asString().isEmpty()) {
            log.debug("Response is empty");
            PlayerResponse emptyResponse = new PlayerResponse();
            emptyResponse.setContentLength(response.getHeader("content-length"));
            return emptyResponse;
        }
        return response.as(PlayerResponse.class);
    }
}