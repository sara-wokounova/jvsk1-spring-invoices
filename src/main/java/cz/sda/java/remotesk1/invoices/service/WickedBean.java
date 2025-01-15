package cz.sda.java.remotesk1.invoices.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
public class WickedBean implements InfoService {

    @Override
    public String getInfo() {
        return "Wicked bean";
    }
}
