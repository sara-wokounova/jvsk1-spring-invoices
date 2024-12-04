package cz.sda.java.remotesk1.invoices.controller;

import cz.sda.java.remotesk1.invoices.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class InfoController {

    private final InfoService infoService;

    @Autowired
    public InfoController(@Qualifier("infoService") InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/info")
    String info() {
        return infoService.getInfo();
    }
}
