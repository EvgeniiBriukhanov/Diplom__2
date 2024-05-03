import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import user.MethodsUser;

import static constants.Endpoints.BASE_URL;
import static constants.TextMessage.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class BaseTest {

    public String accessToken;
    protected MethodsUser methodsUser = new MethodsUser();

    @Before
    @Step("Базовые тестовых данные")
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = BASE_URL;
    }

    @After
    @Step("Удаление ранее созданного пользователя")
    public void deleteData() {
        if (accessToken == null) {
            System.out.println(USER_EMPTY);
        } else {
            String userName = methodsUser.getUserInfo(accessToken).extract().path("user.name");
            ValidatableResponse responseDelete = methodsUser.userDelete(String.valueOf(accessToken));
            responseDelete.assertThat()
                    .statusCode(202)
                    .body("success", equalTo(USER_DELETE_SUCCESS_202))
                    .and().body("message", equalTo(USER_DELETE_202));
            System.out.println(userName + " " + USER_DELETE_202);
        }
    }
}
