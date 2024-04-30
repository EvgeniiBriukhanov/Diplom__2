package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static constants.Endpoints.*;
import static io.restassured.RestAssured.given;

public class MethodsUser extends BaseApplication {

    @Step("регистрация нового пользователя")
    public ValidatableResponse createUser(UserInfo userInfo) {
        return given()
                .header("Content-type", "application/json")
                .body(userInfo)
                .when()
                .post(POST_USER_CREATE).then();
    }

    @Step("удаление пользователя")
    public ValidatableResponse userDelete(int userId) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(DELETE_USER + userId).then();
    }

    @Step("Логин пользователя")
    public ValidatableResponse userAuthorization(LoginInfo loginInfo) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(loginInfo)
                .when()
                .post(POST_USER_LOGIN).then();
    }
}
