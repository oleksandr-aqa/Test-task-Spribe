package api;

import dto.reponse.PlayerResponse;
import helpers.Config;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public abstract class AbstractRequest {

    protected static final Logger log = LoggerFactory.getLogger(PlayerControllerRequest.class);
    String baseURI = Config.get("base.URI");

    protected PlayerResponse infoLogger(Response response) {
        log.info("Response: {} - {}", response.getStatusCode(), response.getBody().asString());
        if (response.getBody().asString().isEmpty()) {
            log.debug("Response is empty");
            PlayerResponse emptyResponse = new PlayerResponse();
            emptyResponse.setContentLength(response.getHeader("content-length"));
            return emptyResponse;
        }
        return response.as(PlayerResponse.class);
    }

    protected Response get(String url, RequestSpecification requestSpec, int expectedStatusCode) {
        return sendRequest(url, requestSpec, Method.GET, expectedStatusCode);
    }

    protected Response put(String url, RequestSpecification requestSpec, int expectedStatusCode) {
        return sendRequest(url, requestSpec, Method.PUT, expectedStatusCode);
    }

    protected Response post(String url, RequestSpecification requestSpec, int expectedStatusCode) {
        return sendRequest(url, requestSpec, Method.POST, expectedStatusCode);
    }

    protected Response patch(String url, RequestSpecification requestSpec, int expectedStatusCode) {
        return sendRequest(url, requestSpec, Method.PATCH, expectedStatusCode);
    }

    protected Response delete(String url, RequestSpecification requestSpec, int expectedStatusCode) {
        return sendRequest(url, requestSpec, Method.DELETE, expectedStatusCode);
    }

    protected Response sendRequest(String url, RequestSpecification requestSpec, Method method, int ExpectedStatusCode) {
        requestSpec.contentType(ContentType.JSON);
        Response response = given()
                .spec(requestSpec)
                .log().ifValidationFails()
                .when()
                .request(method, baseURI + url);
        return response
                .then()
                .log().ifValidationFails()
                .statusCode(ExpectedStatusCode)
                .extract().response();
    }

}
