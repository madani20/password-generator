package com.mad.password_generator.models;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Generic error response")
public class ErrorResponse {

    @Schema( description = "Error message", example = "La longueur du mot de passe doit être comprise entre 6 et 128 caractères.")
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
