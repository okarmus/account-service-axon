package org.okarmus.accountcore.core.web.endpoint;

import javaslang.control.Try;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
class ResponseHandler {

    <T> ResponseEntity<String> handleCommandGatewayResponse(Try<T> commandGatewayResult, String successMessage) {
        return commandGatewayResult.map(result -> successResponse(successMessage))
                .getOrElseGet(this::errorResponse);
    }

    private ResponseEntity<String> successResponse(String message) {
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    private ResponseEntity<String> errorResponse(Throwable throwable) {
        throwable.printStackTrace();
        return new ResponseEntity<>("Error occurred: " + throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
