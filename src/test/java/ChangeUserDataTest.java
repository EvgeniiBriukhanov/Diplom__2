import BaseInfo.BaseForChangeAndOrdersAndReceiptTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import user.LoginInfo;
import user.MethodsUser;
import user.UserInfo;

import static constants.TextMessage.*;
import static constants.TextMessage.DELETE_USER_202;
import static org.hamcrest.CoreMatchers.equalTo;

public class ChangeUserDataTest extends BaseForChangeAndOrdersAndReceiptTest {
    protected MethodsUser methodsUser = new MethodsUser();

    @DisplayName("Изменение данных пользователя")
    @Description("Успешное изменение данных пользователя с авторизацией")
    @Test
    public void userChangeDataWithAuthorizationTest() {
        LoginInfo loginInfo = LoginInfo.from(userInfo);
        ValidatableResponse userLogin = methodsUser.userAuthorization(loginInfo);
        userLogin.assertThat()
                .statusCode(200)
                .and().body("success", equalTo(LOGIN_USER_SUCCESS));

        accessToken = userLogin.extract().path("accessToken");

        userInfo.setName("new"+random);
        userInfo.setEmail("new"+random+"@yandex.ru");
        ValidatableResponse changeUserData = methodsUser.userChangeData(accessToken, userInfo);
        changeUserData.assertThat()
                .statusCode(200)
                .and().body("success", equalTo(UPDATE_USER_DATE_SUCCESS))
                .and().body("user.name", equalTo(userInfo.getName()))
                .and().body("user.email", equalTo(userInfo.getEmail()));
    }

    @DisplayName("Изменение данных пользователя")
    @Description("Успешное изменение данных пользователя без авторизацией")
    @Test
    public void userChangeDataWithoutAuthorizationTest() {
        userInfo.setName("new"+random);
        userInfo.setEmail("new"+random+"@yandex.ru");
        ValidatableResponse changeUserData = methodsUser.userChangeData(accessToken,userInfo);
        changeUserData.assertThat()
                .statusCode(200)
                .and().body("success", equalTo(UPDATE_USER_DATE_SUCCESS))
                .and().body("user.name", equalTo(userInfo.getName()))
                .and().body("user.email", equalTo(userInfo.getEmail()));
    }
    @DisplayName("Изменение данных неизвестного пользователя")
    @Description("Неудачное изменение данных пользователя")
    @Test
    public void unknownUserChangeDataTest() {
        userInfo.setName("new"+random);
        userInfo.setEmail("new"+random+"@yandex.ru");
        ValidatableResponse changeUserData = methodsUser.userChangeData(String.valueOf(random),userInfo);
        changeUserData.assertThat()
                .statusCode(401)
                .and().body("success", equalTo(UPDATE_USER_DATE_FAILED))
                .and().body("message", equalTo(UPDATE_USER_DATE_FALSE_401));
    }
    @DisplayName("Изменение данных пользователя на существующие ")
    @Description("Неудачное изменение данных пользователя")
    @Test
    public void userChangeDateToExistingTest() {
        UserInfo userTwoInfo = new UserInfo( "two+" + random + "@yandex.ru", "123456", "two" + random);
        ValidatableResponse responseCreateTwo = methodsUser.createUser(userTwoInfo);
        responseCreateTwo.assertThat()
                .statusCode(200)
                .and().body("success", equalTo(CREATE_USER_SUCCESS));

        String accessTokenTwo = responseCreateTwo.extract().path("accessToken");

        ValidatableResponse changeUserData = methodsUser.userChangeData(accessTokenTwo,userInfo);
        changeUserData.assertThat()
                .statusCode(403)
                .and().body("success", equalTo(UPDATE_USER_DATE_FAILED))
                .and().body("message", equalTo(UPDATE_USER_DATE_FALSE_403));

        String userName = methodsUser.getUserInfo(accessTokenTwo).extract().path("user.name");
        ValidatableResponse responseDelete = methodsUser.userDelete(String.valueOf(accessTokenTwo));
        responseDelete.assertThat()
                .statusCode(202)
                .body("success", equalTo(DELETE_USER_SUCCESS))
                .and().body("message", equalTo(DELETE_USER_202));
        System.out.println(userName + " " + DELETE_USER_202);
    }
}
