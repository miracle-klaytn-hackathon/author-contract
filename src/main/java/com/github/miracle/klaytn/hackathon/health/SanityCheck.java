package com.github.miracle.klaytn.hackathon.health;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class SanityCheck implements HealthCheck {

    private static final String NAME = SanityCheck.class.getName();

    private HealthCheckResponseBuilder responseBuilder;

    @PostConstruct
    public void postConstruct() {
        responseBuilder = HealthCheckResponse.builder().name(NAME);
    }

    @Override
    public HealthCheckResponse call() {
        return responseBuilder.up()
                .withData("message", "Application is reachable")
                .build();
    }

}
