import BaseInfo.BaseForCreateAndLoginTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static constants.TextMessage.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class CreateUserTest extends BaseForCreateAndLoginTest {

    @DisplayName("Создание пользователя с валидными данными")
    @Description("Успешного создание пользователя")
    @Test
    public void successCreateNewUserTest() {
        ValidatableResponse responseCreate = methodsUser.createUser(userInfo);

        responseCreate.assertThat()
                .statusCode(200)
                .and().body("success", equalTo(CREATE_USER_SUCCESS))
                .and().body("accessToken", is(notNullValue()))
                .and().body("refreshToken", is(notNullValue()))
                .and().body("user.name", equalTo(userInfo.getName()))
                .and().body("user.email", equalTo(userInfo.getEmail()));

        accessToken = responseCreate.extract().path("accessToken");
    }

    @DisplayName("Создание пользователя без Email")
    @Description("Проверка получение ошибки при создании пользователя без Email")
    @Test
    public void failedCreatingUserWithoutLoginTest() {
        userInfo.setEmail(null);
        ValidatableResponse responseCreate = methodsUser.createUser(userInfo);

        responseCreate.assertThat()
                .statusCode(403)
                .and().body("success", equalTo(CREATE_USER_FAILED))
                .and().body("message", equalTo(CREATE_USER_ONE_FIELD_EMPTY_403));
    }

    @DisplayName("Создание пользователя без Password")
    @Description("Проверка получение ошибки при создании пользователя без Password")
    @Test
    public void failedCreatingUserWithoutPasswordTest() {
        userInfo.setPassword(null);
        ValidatableResponse responseCreate = methodsUser.createUser(userInfo);

        responseCreate.assertThat()
                .statusCode(403)
                .and().body("success", equalTo(CREATE_USER_FAILED))
                .and().body("message", equalTo(CREATE_USER_ONE_FIELD_EMPTY_403));
    }

    @DisplayName("Создание пользователя без Name")
    @Description("Проверка получение ошибки при создании пользователя без Name")
    @Test
    public void failedCreatingUserWithoutNameTest() {
        userInfo.setName(null);
        ValidatableResponse responseCreate = methodsUser.createUser(userInfo);

        responseCreate.assertThat()
                .statusCode(403)
                .and().body("success", equalTo(CREATE_USER_FAILED))
                .and().body("message", equalTo(CREATE_USER_ONE_FIELD_EMPTY_403));
    }

    @DisplayName("Создание двух одинаковых пользователей")
    @Description("Проверка получение ошибки при создании двух одинаковых пользователей")
    @Test
    public void failedCreatingTwoIdenticalCouriersTest() {
        ValidatableResponse responseCreate = methodsUser.createUser(userInfo);

        ValidatableResponse responseCreateDouble = methodsUser.createUser(userInfo);
        responseCreateDouble.assertThat()
                .statusCode(403)
                .and().body("message", equalTo(CREATE_USER_DOUBLE_403));

        accessToken = responseCreate.extract().path("accessToken");
    }
}