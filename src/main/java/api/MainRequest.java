package api;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.Config;

import static io.restassured.RestAssured.given;

public abstract class MainRequest {

    String baseURI = Config.get("base.URI");

    protected Response get(String url, RequestSpecification requestSpec, int expectedStatusCode) {
        return baseMethod(url, requestSpec, Method.GET, expectedStatusCode);

    }

    protected Response put(String url, RequestSpecification requestSpec, int expectedStatusCode) {
        return baseMethod(url, requestSpec, Method.PUT, expectedStatusCode);
    }

    protected Response post(String url, RequestSpecification requestSpec, int expectedStatusCode) {
        return baseMethod(url, requestSpec, Method.POST, expectedStatusCode);
    }

    protected Response patch(String url, RequestSpecification requestSpec, int expectedStatusCode) {
        return baseMethod(url, requestSpec, Method.PATCH, expectedStatusCode);
    }

    protected Response delete(String url, RequestSpecification requestSpec, int expectedStatusCode) {
        return baseMethod(url, requestSpec, Method.DELETE, expectedStatusCode);
    }

    protected Response baseMethod(String url, RequestSpecification requestSpec, Method method, int ExpectedStatusCode) {
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
