import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.Courier;
import models.CourierCreds;
import org.junit.Test;
import steps.CourierStep;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

@Feature("Удаление курьера /api/v1/courier/:id")
public class DeleteCourierTest {
    Courier courier;
    String id;

    @Test
    @DisplayName("Успешное удаление курьера c валидными данными")
    public void successfullyDeleteCourierTest(){
        courier = Courier.generateRandomCourier();
        CourierStep.create(courier);
        id = CourierStep.login(CourierCreds.getCredentials(courier))
                .extract().path("id").toString();
        ValidatableResponse response = CourierStep.delete(id);
        response.statusCode(200)
                .assertThat()
                .body("ok", equalTo(true));
    }
    @Test
    @DisplayName("Удаление курьера без id")
    public void DeleteCourierWithoutIdTest(){
        ValidatableResponse response = CourierStep.delete("");
        response.assertThat()
                .statusCode(404)
                .and()
                .body("code", equalTo(404))
                .and()
                .body("message", equalTo("Not Found."));
    }
    @Test
    @DisplayName("Удаление курьера c несуществующим id")
    public void DeleteCourierInvalidIdTest(){
        ValidatableResponse response = CourierStep.delete("0");
        response.assertThat()
                .statusCode(404)
                .and()
                .body("code", equalTo(404))
                .and()
                .body("message", equalTo("Курьера с таким id нет."));
    }

}
