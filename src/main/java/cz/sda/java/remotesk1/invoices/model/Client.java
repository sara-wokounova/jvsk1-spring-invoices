package cz.sda.java.remotesk1.invoices.model;

import lombok.Builder;

@Builder
public record Client(String id, String name, String address) {
}
