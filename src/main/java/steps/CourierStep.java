package steps;

import config.Config;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import models.Courier;
import models.CourierCreds;

import static io.restassured.RestAssured.given;

public class CourierStep extends Config {

    @Step("Регистрация курьера")
    public static ValidatableResponse create(Courier courier) {
        return given()
                .spec(getRqSpec())
                .when()
                .body(courier).log().all()
                .post(COURIER_URL).then().log().all();

    }

    @Step("Авторизация курьера")
    public static ValidatableResponse login(CourierCreds courierCreds) {
        return given()
                .spec(getRqSpec())
                .when()
                .body(courierCreds).log().all()
                .post(COURIER_URL + "login").then().log().all();

    }

    @Step("Удаление курьера")
    public static ValidatableResponse delete(String courierId) {
        return given()
                .spec(getRqSpec())
                .when().log().all()
                .delete(COURIER_URL + courierId).then().log().all();

    }
}
