package me.mcdobr.customer;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class CustomerResourceTest {
    @Test
    void shouldFindAll() {
        RestAssured.given()
                .when().get("/customers")
                .then().statusCode(200)
                .body(CoreMatchers.anything());
    }
}