package com.github.miracle.klaytn.hackathon;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class SanityCheckTest {

    @Test
    public void testHealthCheck() {
        given()
          .when().get("/q/health/ready")
          .then()
             .statusCode(200);
    }

}