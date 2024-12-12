package cz.sda.java.remotesk1.invoices.controller.web.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode
@Builder
public class UpdateClient {
        @NonNull
        private String id;

        @NotBlank(message="Name is required")
        private String name;

        @NotBlank(message="Address is required")
        private String address;
}
