package com.github.miracle.klaytn.hackathon.api;

import com.github.miracle.klaytn.hackathon.contracts.SmartContractException;
import com.github.miracle.klaytn.hackathon.openapi.model.ErrorResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@ApplicationScoped
public class ExceptionMapper {

    @ServerExceptionMapper
    public Response mapException(SmartContractException exception) {
        return Response.serverError()
                .type(MediaType.APPLICATION_JSON)
                .entity(toErrorResponse(exception))
                .build();
    }

    private static ErrorResponse toErrorResponse(SmartContractException exception) {
        return ErrorResponse.builder()
                .code("General Error")
                .message(exception.getMessage())
                .build();
    }

}
