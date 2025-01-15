package cz.sda.java.remotesk1.invoices.service;

import cz.sda.java.remotesk1.invoices.configuration.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!local")
@Component
public class InfoServiceBean implements InfoService {

    private final ApplicationConfiguration applicationConfiguration;

    @Autowired
    public InfoServiceBean(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public String getInfo() {
        return String.format("Invoices application version: %s", applicationConfiguration.getVersion());
    }
}
