package cz.sda.java.remotesk1.invoices.controller.web;

import cz.sda.java.remotesk1.invoices.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class InfoController {

    private final InfoService infoService;

    @Autowired
    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/info")
    String info(Model model) {
        model.addAttribute("info", infoService.getInfo());
        return "info";
    }
}
