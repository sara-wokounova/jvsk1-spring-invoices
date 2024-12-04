package cz.sda.java.remotesk1.invoices.configuration;

import cz.sda.java.remotesk1.invoices.service.InfoService;
import cz.sda.java.remotesk1.invoices.service.InfoServiceBean;
import cz.sda.java.remotesk1.invoices.service.WickedBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InfoConfig {

    @Bean
    InfoService infoService() {
        return new InfoServiceBean();
    }

    @Bean
    InfoService wickedBean() {
        return new WickedBean();
    }
}
