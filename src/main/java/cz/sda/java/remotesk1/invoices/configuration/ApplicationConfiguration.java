package cz.sda.java.remotesk1.invoices.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "invoices")
@Data
public class ApplicationConfiguration {
    private String version = "0.1.0";
}
