package cz.sda.java.remotesk1.invoices.controller.rest.request;

import jakarta.validation.constraints.Min;
import lombok.NonNull;

import java.math.BigDecimal;

public record CreateProduct(
        @NonNull
        String name,
        @NonNull
        @Min(0)
        BigDecimal price) {
}
