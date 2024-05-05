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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class OrdersTest extends BaseForChangeAndOrdersAndReceiptTest {

    protected OrderInfo orderInfo = new OrderInfo();
    protected MethodsOrder methodsOrder = new MethodsOrder();
    List<String> orderList = new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa72", "61c0c5a71d1f82001bdaaa73", "61c0c5a71d1f82001bdaaa6f"));

    @DisplayName("Создание заказа с авторизацией пользователя")
    @Description("Успешное создание заказа с авторизацией")
    @Test
    public void orderCreateWithAuthorizedUserTest() {
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
                .and().body("success", equalTo(CREATE_ORDER_SUCCESS))
                .and().body("name", is(notNullValue()))
                .and().body("order._id", is(notNullValue()))
                .and().body("order.status", is(notNullValue()))
                .and().body("order.createdAt", is(notNullValue()))
                .and().body("order.updatedAt", is(notNullValue()))
                .and().body("order.number", is(notNullValue()))
                .and().body("order.price", is(notNullValue()))
                .and().body("order.owner.name", equalTo(userInfo.getName()))
                .and().body("order.owner.email", equalTo(userInfo.getEmail()));
    }

    @DisplayName("Создание заказа без авторизацией пользователя")
    @Description("Успешное создание заказа без авторизацией")
    @Test
    public void orderCreateWithoutAuthorizedUserTest() {
        orderInfo.setIngredients(orderList);
        ValidatableResponse createOrder = methodsOrder.createOrder(accessToken, orderInfo);
        createOrder.assertThat()
                .statusCode(200)
                .and().body("success", equalTo(CREATE_ORDER_SUCCESS))
                .and().body("name", is(notNullValue()))
                .and().body("order._id", is(notNullValue()))
                .and().body("order.status", is(notNullValue()))
                .and().body("order.createdAt", is(notNullValue()))
                .and().body("order.updatedAt", is(notNullValue()))
                .and().body("order.number", is(notNullValue()))
                .and().body("order.price", is(notNullValue()))
                .and().body("order.owner.name", equalTo(userInfo.getName()))
                .and().body("order.owner.email", equalTo(userInfo.getEmail()))
                .and().body("order.ingredients[0]._id", equalTo(orderInfo.getIngredients().get(0)))
                .and().body("order.ingredients[1]._id", equalTo(orderInfo.getIngredients().get(1)))
                .and().body("order.ingredients[2]._id", equalTo(orderInfo.getIngredients().get(2)));
    }

    @DisplayName("Создание заказа с ингредиентами")
    @Description("Успешное создание заказа с ингредиентами")
    @Test
    public void orderCreateWithIngredientsTest() {
        orderInfo.setIngredients(orderList);
        ValidatableResponse createOrder = methodsOrder.createOrder(accessToken, orderInfo);
        createOrder.assertThat()
                .statusCode(200)
                .and().body("success", equalTo(CREATE_ORDER_SUCCESS))
                .and().body("name", is(notNullValue()))
                .and().body("order._id", is(notNullValue()))
                .and().body("order.status", is(notNullValue()))
                .and().body("order.number", is(notNullValue()))
                .and().body("order.price", is(notNullValue()))
                .and().body("order.ingredients[0]._id", equalTo(orderInfo.getIngredients().get(0)))
                .and().body("order.ingredients[1]._id", equalTo(orderInfo.getIngredients().get(1)))
                .and().body("order.ingredients[2]._id", equalTo(orderInfo.getIngredients().get(2)));
    }

    @DisplayName("Создание заказа без ингредиентов")
    @Description("Неудачное создание заказа без ингредиентов")
    @Test
    public void orderCreateWithoutIngredientsTest() {
        ValidatableResponse createOrder = methodsOrder.createOrder(accessToken, orderInfo);
        createOrder.assertThat()
                .statusCode(400)
                .and().body("success", equalTo(CREATE_ORDER_FAILED))
                .and().body("message", equalTo(CREATE_ORDER_WITHOUT_INGREDIENT_FALSE_400));
    }

    @DisplayName("Создание заказа с неизвестными ингредиентами")
    @Description("Неудачное создание заказа с неизвестными ингредиентами")
    @Test
    public void orderCreateWithUnknownIngredients() {
        List<String> orderWithUnknownIngredientsList = new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa72"+random,"61c0c5a71d1f82001bdaaa73"+random));
        orderInfo.setIngredients(orderWithUnknownIngredientsList);

        ValidatableResponse createOrder = methodsOrder.createOrder(accessToken, orderInfo);
        createOrder.assertThat()
                .statusCode(500);
    }
}
