package br.com.outsera.gra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RespostaIntervalosPremiacao(
        @JsonProperty("min") List<IntervaloPremiacao> menor,
        @JsonProperty("max") List<IntervaloPremiacao> maior
) {
}
