package dev.aymee.support_api.request;

import jakarta.validation.constraints.NotNull;

public class RequestUpdateDto {
    @NotNull(message = "Status cannot be null")
    private RequestStatusEntity status;

    // Getter y Setter
    public RequestStatusEntity getStatus() {
        return status;
    }

    public void setStatus(RequestStatusEntity status) {
        this.status = status;
    }
}
