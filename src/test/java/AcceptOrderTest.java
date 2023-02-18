import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.Courier;
import models.CourierCreds;
import models.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierStep;
import steps.OrdersStep;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

@Feature("Принятие заказа /api/v1/orders/accept/:id")
public class AcceptOrderTest {
    Courier courier;
    Order order;
    String courierId;
    String orderId;

    @Before
    public void setUp() {
        courier = Courier.generateRandomCourier();
        order = new Order("Вася", "Иванов", "Колотушкина 13", "Выхино", "79265553535", 5, "01-02-2023",
                "some comment", List.of("Black"));
        CourierStep.create(courier);
        courierId = CourierStep.login(CourierCreds.getCredentials(courier))
                .extract().path("id").toString();
        orderId = OrdersStep.createOrder(order).extract().path("track").toString();
    }

    @After
    public void cleanUp() {
        CourierStep.delete(courierId);
    }

    @Test
    @DisplayName("Успешное принятие заказа")
    public void successfullyAcceptOrderTest() {
        ValidatableResponse response = OrdersStep.acceptOrder(orderId, courierId);
        response.assertThat()
                .statusCode(200)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Принятие заказа с несуществующим id курьера")
    public void AcceptOrderWithInvalidCourierIdTest() {
        ValidatableResponse response = OrdersStep.acceptOrder(orderId, "0");
        response.assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Курьера с таким id не существует"));
    }

    @Test
    @DisplayName("Принятие заказа без id курьера")
    public void AcceptOrderWithoutCourierIdTest() {
        ValidatableResponse response = OrdersStep.acceptOrder(orderId, "");
        response.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Test
    @DisplayName("Принятие заказа c несуществующим id заказа")
    public void AcceptOrderWithInvalidOrderIdTest() {
        ValidatableResponse response = OrdersStep.acceptOrder("0", courierId);
        response.assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Заказа с таким id не существует"));
    }
    @Test
    @DisplayName("Принятие заказа без id заказа")
    public void AcceptOrderWithoutOrderIdTest() {
        ValidatableResponse response = OrdersStep.acceptOrder("", courierId);
        response.assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для поиска"));
    }
}
