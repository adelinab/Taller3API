
package factoryRequest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RequestDELETE implements IRequest {
    @Override
    public Response send(RequestInformation information) {
        Response response = given()
                .headers(information.getHeaders())
                .log().all()
                .when()
                .delete(information.getUrl());
        response.then().log().all();
        return response;
    }
}