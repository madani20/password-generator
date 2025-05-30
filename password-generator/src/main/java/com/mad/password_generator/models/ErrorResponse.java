package com.mad.password_generator.models;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Generic error response")
public class ErrorResponse {

    @Schema( description = "Error message", example = "Password length must be between 6 and 128 characters.")
    private String message;

    public ErrorResponse() {    }

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
