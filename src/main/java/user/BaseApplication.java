package user;

import io.restassured.specification.RequestSpecification;

import static constants.Endpoints.BASE_URL;
import static io.restassured.RestAssured.given;

public class BaseApplication {
    public RequestSpecification requestSpecification(){
        return given().log().all()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL);
    }
}
