package cz.sda.java.remotesk1.invoices.controller.rest;

import cz.sda.java.remotesk1.invoices.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
class InfoApi {

    private final InfoService infoService;

    @Autowired
    public InfoApi(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/info")
    String info() {
        return infoService.getInfo();
    }
}
