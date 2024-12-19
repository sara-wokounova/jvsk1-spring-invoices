package cz.sda.java.remotesk1.invoices.controller.web.request;

import jakarta.validation.constraints.Min;
import lombok.NonNull;

import java.math.BigDecimal;

public record UpdateProduct(
        @NonNull
        String id,
        @NonNull
        String name,
        @NonNull
        @Min(0)
        BigDecimal price) {
}
