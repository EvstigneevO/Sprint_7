import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.Courier;
import models.CourierCreds;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierStep;

import static config.Config.COURIER_URL;
import static config.Config.getRqSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
@Feature("Логин курьера в системе /api/v1/courier/login")
public class LoginCourierTest {
    Courier courier;
    String id;

    @Before
    public void setUp(){
        courier = Courier.generateRandomCourier();
        CourierStep.create(courier);
        id = given()
                .spec(getRqSpec())
                .when()
                .body(CourierCreds.getCredentials(courier))
                .post(COURIER_URL + "login")
                .then().extract().path("id").toString();

    }

    @After
    public void cleanUp() {
        if (id != null) {
            CourierStep.delete(id);
        }
    }

    @Test
    @DisplayName("Успешный логин в системе")
    public void successfullyLoginTest(){
        ValidatableResponse response = CourierStep.login(CourierCreds.getCredentials(courier));
        response
                .statusCode(200)
                .assertThat()
                .body("id",is(notNullValue()));
    }

    @Test
    @DisplayName("Авторизация незарегистрированного курьера")
    public void loginWithNonExistentCourierTest(){
        courier = Courier.generateRandomCourier();
        ValidatableResponse response = CourierStep.login(CourierCreds.getCredentials(courier));
        response
                .statusCode(404)
                .assertThat()
                .body("code",equalTo(404))
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Авторизация c неверным логином")
    public void loginWithInvalidLoginTest(){
        courier.setLogin("Bzz");
        ValidatableResponse response = CourierStep.login(CourierCreds.getCredentials(courier));
        response
                .statusCode(404)
                .assertThat()
                .body("code",equalTo(404))
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация c неверным паролем")
    public void loginWithInvalidPasswordTest(){
        courier.setPassword("BzzZxz");
        ValidatableResponse response = CourierStep.login(CourierCreds.getCredentials(courier));
        response
                .statusCode(404)
                .assertThat()
                .body("code",equalTo(404))
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация без пароля")
    public void loginWithoutPasswordTest(){
        courier.setPassword("");
        ValidatableResponse response = CourierStep.login(CourierCreds.getCredentials(courier));
        response
                .statusCode(400)
                .assertThat()
                .body("code",equalTo(400))
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация без логина")
    public void loginWithoutLoginTest(){
        courier.setLogin("");
        ValidatableResponse response = CourierStep.login(CourierCreds.getCredentials(courier));
        response
                .statusCode(400)
                .assertThat()
                .body("code",equalTo(400))
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}
