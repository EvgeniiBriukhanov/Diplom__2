package user;

import io.qameta.allure.Step;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.ValidatableResponse;

import static constants.Endpoints.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

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
    public ValidatableResponse userDelete(String accessToken) {
        return given()
                .header("authorization", accessToken)
//                .and()
//                .body(accessToken)
//                .when()
                .delete(DELETE_USER).then();
    }

    @Step("получения данных о пользователе")
    public ValidatableResponse getUserInfo(String accessToken) {
        return given()
                .header("authorization",accessToken)
//                .body("authorization"+accessToken)
                .get(GET_USER_INFO).then();
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
