package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import user.BaseApplication;

import static constants.Endpoints.GET_USER_ORDER;
import static constants.Endpoints.POST_ORDER_CREATE;
import static io.restassured.RestAssured.given;

public class MethodsOrder extends BaseApplication {

    @Step("Создание заказа")
    public ValidatableResponse createOrder(String accessToken, OrderInfo orderInfo){
        return given()
                .spec(requestSpecification())
                .header("Authorization", accessToken)
                .body(orderInfo)
                .when()
                .post(POST_ORDER_CREATE).then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrdersList(String accessToken){
        return given()
                .spec(requestSpecification())
                .header("Authorization", accessToken)
                .when()
                .get(GET_USER_ORDER).then();
    }
}
