import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import user.LoginInfo;
import user.MethodsUser;
import user.UserInfo;

import static constants.TextMessage.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;


public class UserLoginTest extends BaseTest {
    private final int random = 1 + (int) (Math.random() * 100000);

    protected UserInfo userInfo = new UserInfo("zabuhalov+" + random + "@yandex.ru", "000000", "petrovich" + random);

    protected MethodsUser methodsUser = new MethodsUser();

    @DisplayName("Авторизация пользователя с валидными данными")
    @Description("Успешная авторизация пользователя")
    @Test
    public void successLoginUserTest() {
        ValidatableResponse responseCreate = methodsUser.createUser(userInfo);
        responseCreate.assertThat()
                .statusCode(200)
                .and().body("success", equalTo(CREATE_USER_SUCCESS_200));

        LoginInfo loginInfo = LoginInfo.from(userInfo);
        ValidatableResponse userLogin = methodsUser.userAuthorization(loginInfo);
        userLogin.assertThat()
                .statusCode(200)
                .and().body("success", equalTo(LOGIN_USER_SUCCESSFUL_200))
                .and().body("accessToken", is(notNullValue()))
                .and().body("refreshToken", is(notNullValue()))
                .and().body("user.name", equalTo(userInfo.getName()))
                .and().body("user.email", equalTo(userInfo.getEmail()));

        accessToken = userLogin.extract().path("accessToken");
    }

    @DisplayName("Авторизация пользователя с несуществующими данными")
    @Description("Проверка получение ошибки при авторизации с несуществующими данными")
    @Test
    public void failedLoginUserWithUnknownDataTest() {
        LoginInfo loginInfo = LoginInfo.from(userInfo);
        loginInfo.setEmail("Unknown");
        loginInfo.setPassword("Unknown");

        ValidatableResponse userLogin = methodsUser.userAuthorization(loginInfo);
        userLogin.assertThat()
                .statusCode(401)
                .and().body("success", equalTo(LOGIN_USER_FAILED_401))
                .and().body("message", equalTo(LOGIN_USER_INCORRECT_DATA_401));
    }

    @DisplayName("Авторизация пользователя без Email")
    @Description("Проверка получение ошибки при авторизации без Email")
    @Test
    public void failedLoginUserWithEmptyEmailTest() {
        LoginInfo loginInfo = LoginInfo.from(userInfo);
        loginInfo.setEmail(null);

        ValidatableResponse userLogin = methodsUser.userAuthorization(loginInfo);
        userLogin.assertThat()
                .statusCode(401)
                .and().body("success", equalTo(LOGIN_USER_FAILED_401))
                .and().body("message", equalTo(LOGIN_USER_INCORRECT_DATA_401));
    }

    @DisplayName("Авторизация пользователя без Password")
    @Description("Проверка получение ошибки при авторизации без Password")
    @Test
    public void failedLoginUserWithEmptyPasswordTest() {
        LoginInfo loginInfo = LoginInfo.from(userInfo);
        loginInfo.setPassword(null);

        ValidatableResponse userLogin = methodsUser.userAuthorization(loginInfo);
        userLogin.assertThat()
                .statusCode(401)
                .and().body("success", equalTo(LOGIN_USER_FAILED_401))
                .and().body("message", equalTo(LOGIN_USER_INCORRECT_DATA_401));
    }
}
