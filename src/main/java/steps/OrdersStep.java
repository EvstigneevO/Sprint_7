package steps;

import config.Config;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import models.Order;

import static io.restassured.RestAssured.given;


public class OrdersStep extends Config {
    @Step("Создание заказа")
    public static ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getRqSpec())
                .body(order)
                .when()
                .post(ORDERS_URL).then().log().all();
    }

    @Step("Получение списка всех заказов")
    public static ValidatableResponse getOrders() {
        return given()
                .spec(getRqSpec())
                .when()
                .get(ORDERS_URL).then().log().all();
    }

    @Step("Принятие заказа")
    public static ValidatableResponse acceptOrder(String orderId, String courierId) {
        return given()
                .spec(getRqSpec())
                .queryParam("courierId", courierId).log().all()
                .when()
                .put(ORDERS_URL + "/accept/" + orderId).then().log().all();

    }

}
