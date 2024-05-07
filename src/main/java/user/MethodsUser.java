package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static constants.Endpoints.*;
import static io.restassured.RestAssured.given;

public class MethodsUser extends BaseApplication {

    @Step("регистрация нового пользователя")
    public ValidatableResponse createUser(UserInfo userInfo) {
        return given()
                .spec(requestSpecification())
                .body(userInfo)
                .when()
                .post(POST_USER_CREATE).then();
    }

    @Step("удаление пользователя")
    public ValidatableResponse userDelete(String accessToken) {
        return given()
                .spec(requestSpecification())
                .header("authorization", accessToken)
                .when()
                .delete(DELETE_USER).then();
    }

    @Step("получения данных о пользователе")
    public ValidatableResponse getUserInfo(String accessToken) {
        return given()
                .spec(requestSpecification())
                .header("authorization", accessToken)
                .when()
                .get(GET_USER_INFO).then();
    }

    @Step("Логин пользователя")
    public ValidatableResponse userAuthorization(LoginInfo loginInfo) {
        return given()
                .spec(requestSpecification())
                .body(loginInfo)
                .when()
                .post(POST_USER_LOGIN).then();
    }

    @Step("Изменение данных пользователя с токен")
    public ValidatableResponse userChangeData(String accessToken, UserInfo userInfo) {
        return given()
                .spec(requestSpecification())
                .header("authorization", accessToken)
                .body(userInfo)
                .when()
                .patch(PATCH_UPDATE_USER_INFO).then();
    }
}
