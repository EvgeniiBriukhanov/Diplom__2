import BaseInfo.BaseForChangeAndOrdersAndReceiptTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.MethodsOrder;
import order.OrderInfo;
import org.junit.Test;
import user.LoginInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static constants.TextMessage.*;
import static constants.TextMessage.CREATE_ORDER_SUCCESS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class ReceiptOrderList extends BaseForChangeAndOrdersAndReceiptTest {

    protected OrderInfo orderInfo = new OrderInfo();
    protected MethodsOrder methodsOrder = new MethodsOrder();
    List<String> orderList = new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa72", "61c0c5a71d1f82001bdaaa73", "61c0c5a71d1f82001bdaaa6f"));
    @DisplayName("Получение заказов конкретного пользователя с авторизацией")
    @Description("Успешное получение заказов с авторизацией")
    @Test
    public void orderReceiptListWithAuthorizedUserTest() {
        LoginInfo loginInfo = LoginInfo.from(userInfo);
        ValidatableResponse userLogin = methodsUser.userAuthorization(loginInfo);
        userLogin.assertThat()
                .statusCode(200)
                .and().body("success", equalTo(LOGIN_USER_SUCCESS));

        accessToken = userLogin.extract().path("accessToken");

        orderInfo.setIngredients(orderList);
        ValidatableResponse createOrder = methodsOrder.createOrder(accessToken, orderInfo);
        createOrder.assertThat()
                .statusCode(200)
                .and().body("success", equalTo(CREATE_ORDER_SUCCESS));

        int orderNumber = createOrder.extract().path("order.number");
        String orderId = createOrder.extract().path("order._id");

        ValidatableResponse receiptList = methodsOrder.getOrdersList(accessToken);
        receiptList.assertThat()
                .statusCode(200)
                .and().body("success", equalTo(GET_ORDER_RECEIPT_SUCCESS))
                .and().body("orders[0]._id", equalTo(orderId))
                .and().body("orders[0].status", is(notNullValue()))
                .and().body("orders[0].createdAt", is(notNullValue()))
                .and().body("orders[0].updatedAt", is(notNullValue()))
                .and().body("orders[0].number", equalTo(orderNumber))
                .and().body("total", equalTo(orderNumber))
                .and().body("totalToday", is(notNullValue()))
        ;
    }

    @DisplayName("Получение заказов конкретного пользователя без авторизацией")
    @Description("Успешное получение заказов без авторизацией")
    @Test
    public void orderReceiptListWithoutAuthorizedUserTest() {
        orderInfo.setIngredients(orderList);
        ValidatableResponse createOrder = methodsOrder.createOrder(accessToken, orderInfo);
        createOrder.assertThat()
                .statusCode(200)
                .and().body("success", equalTo(CREATE_ORDER_SUCCESS));

        int orderNumber = createOrder.extract().path("order.number");
        String orderId = createOrder.extract().path("order._id");

        ValidatableResponse receiptList = methodsOrder.getOrdersList(accessToken);
        receiptList.assertThat()
                .statusCode(200)
                .and().body("success", equalTo(GET_ORDER_RECEIPT_SUCCESS))
                .and().body("orders[0]._id", equalTo(orderId))
                .and().body("orders[0].number", equalTo(orderNumber));
    }
    @DisplayName("Получение заказов несуществующего пользователя")
    @Description("Неудачное получение заказов ")
    @Test
    public void orderReceiptListWithUnknownUserTest() {
        ValidatableResponse receiptList = methodsOrder.getOrdersList(String.valueOf(random));
        receiptList.assertThat()
                .statusCode(401)
                .and().body("success", equalTo(GET_ORDER_RECEIPT_FAILED))
                .and().body("message", equalTo(GET_ORDER_RECEIPT_FAILED_401));
    }
}
