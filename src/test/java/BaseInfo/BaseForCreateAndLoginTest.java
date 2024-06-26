package BaseInfo;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import user.MethodsUser;
import user.UserInfo;

import static constants.TextMessage.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class BaseForCreateAndLoginTest {

    private final int random = 1 + (int) (Math.random() * 100000);
    protected UserInfo userInfo = new UserInfo("zabuhalov+" + random + "@yandex.ru", "000000", "petrovich" + random);
    public String accessToken;
    protected MethodsUser methodsUser = new MethodsUser();

    @Before
    @Step("Базовые тестовых данные")
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
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
                    .body("success", equalTo(DELETE_USER_SUCCESS))
                    .and().body("message", equalTo(DELETE_USER_202));
            System.out.println(userName + " " + DELETE_USER_202);
        }
    }
}
