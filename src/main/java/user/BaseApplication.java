package user;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static constants.Endpoints.BASE_URL;
import static io.restassured.RestAssured.given;

public class BaseApplication {
    public RequestSpecification requestSpecification(){
        return new RequestSpecBuilder()
        .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build();

    }
}
