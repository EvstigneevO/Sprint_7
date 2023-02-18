package config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Config {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    public static final String COURIER_URL = "/api/v1/courier/";
    public static final String ORDERS_URL = "api/v1/orders";
    public static RequestSpecification getRqSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }
}


