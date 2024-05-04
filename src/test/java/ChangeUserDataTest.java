import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.LoginInfo;
import user.MethodsUser;
import user.UserInfo;

import static constants.TextMessage.*;
import static constants.TextMessage.DELETE_USER_202;
import static org.hamcrest.CoreMatchers.equalTo;

public class ChangeUserDataTest {
    public String accessToken;
    protected MethodsUser methodsUser = new MethodsUser();
    private final int random = 1 + (int) (Math.random() * 100000);
    protected UserInfo userInfo = new UserInfo("zabuhalov+" + random + "@yandex.ru", "123456", "petrovich" + random);

    @Before
    @Step("Базовые тестовых данные")
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        ValidatableResponse responseCreate = methodsUser.createUser(userInfo);
        responseCreate.assertThat()
                .statusCode(200)
                .and().body("success", equalTo(CREATE_USER_SUCCESS_200));


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
                    .body("success", equalTo(DELETE_USER_SUCCESS_202))
                    .and().body("message", equalTo(DELETE_USER_202));
            System.out.println(userName + " " + DELETE_USER_202);
        }
    }

    @DisplayName("Изменение данных пользователя")
    @Description("Изменение данных пользователя с авторизацией")
    @Test
    public void changeUserDataWithAuthorizationTest() {
        LoginInfo loginInfo = LoginInfo.from(userInfo);
        ValidatableResponse userLogin = methodsUser.userAuthorization(loginInfo);
        userLogin.assertThat()
                .statusCode(200)
                .and().body("success", equalTo(LOGIN_USER_SUCCESSFUL_200));

        accessToken = userLogin.extract().path("accessToken");

        userInfo.setName("new"+random);
        userInfo.setEmail("new"+random+"@yandex.ru");
        ValidatableResponse changeUserData = methodsUser.userChangeData(accessToken, userInfo);
        changeUserData.assertThat()
                .statusCode(200)
                .and().body("success", equalTo(UPDATE_USER_DATE_200))
                .and().body("user.name", equalTo(userInfo.getName()))
                .and().body("user.email", equalTo(userInfo.getEmail()));
    }

    @DisplayName("Изменение данных пользователя")
    @Description("Изменение данных пользователя с авторизацией")
    @Test
    public void changeUserDataWithoutAuthorizationTest() {
        userInfo.setName("new"+random);
        userInfo.setEmail("new"+random+"@yandex.ru");
        ValidatableResponse changeUserData = methodsUser.userChangeData(accessToken, userInfo);
        changeUserData.assertThat()
                .statusCode(200)
                .and().body("success", equalTo(UPDATE_USER_DATE_200))
                .and().body("user.name", equalTo(userInfo.getName()))
                .and().body("user.email", equalTo(userInfo.getEmail()));
    }
}
