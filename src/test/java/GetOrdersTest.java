import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import steps.OrdersStep;


import static org.hamcrest.Matchers.notNullValue;

@Feature("Получение списка заказов /api/v1/orders")
public class GetOrdersTest {

    @Test
    @DisplayName("Получение списка всех заказов")
    public void getAllOrdersTest(){
        ValidatableResponse response = OrdersStep.getOrders();
        response.statusCode(200)
                .assertThat()
                .body("orders", notNullValue());

    }

}
