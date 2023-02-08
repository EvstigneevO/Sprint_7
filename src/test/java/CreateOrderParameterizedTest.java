import config.Config;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.OrdersStep;

import java.util.List;


import static org.hamcrest.Matchers.notNullValue;

@Feature("Создание заказа /api/v1/orders")
@RunWith(Parameterized.class)
public class CreateOrderParameterizedTest {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public CreateOrderParameterizedTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime,
                                        String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Testdata: {0}, {1}, {8}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Вася", "Иванов", "Колотушкина 13", "Выхино", "79265553535", 5, "01-02-2023",
                        "some comment", List.of("Black")},
                {"John", "Rambo", "13 Basket str", "No Subway", "+3123456789", 3, "07-02-2023",
                        "My war is over", List.of("Gray")},
                {"Сергей", "Сергеев", "Самая длинная улица в этом городе 15", "Библиотека имени Ленина", "89265553535", 5, "09-02-2023",
                        "Пазваааани как приедешь", List.of("Black", "Gray")},
                {"Марк", "Степанов", "Вокзальная 15", "Китай-город", "123456789", 1, "12-02-2023",
                        "ок", null},
        };
    }

    @Test
    @DisplayName("Успешное создание заказа с валидными данными")
    public void successfullyCreateOrderWitchValidDataTest() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ValidatableResponse response = OrdersStep.createOrder(order);
        response.statusCode(201)
                .assertThat()
                .body("track", notNullValue());
    }
}
