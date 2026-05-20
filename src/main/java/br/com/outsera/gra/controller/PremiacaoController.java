package br.com.outsera.gra.controller;

import br.com.outsera.gra.dto.RespostaIntervalosPremiacao;
import br.com.outsera.gra.service.PremiacaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/producers/award-intervals")
public class PremiacaoController {

    private final PremiacaoService premiacaoService;

    public PremiacaoController(PremiacaoService premiacaoService) {
        this.premiacaoService = premiacaoService;
    }

    @GetMapping
    public RespostaIntervalosPremiacao buscarIntervalosDePremiacao() {
        return premiacaoService.buscarIntervalosDePremiacao();
    }
}
