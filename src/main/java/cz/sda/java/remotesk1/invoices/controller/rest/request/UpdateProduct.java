package cz.sda.java.remotesk1.invoices.controller.rest.request;

import java.math.BigDecimal;

public record UpdateProduct(
        String name,
        BigDecimal price) {
}
