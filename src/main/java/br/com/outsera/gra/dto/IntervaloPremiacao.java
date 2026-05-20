package br.com.outsera.gra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IntervaloPremiacao(
        @JsonProperty("producer") String produtor,
        @JsonProperty("interval") int intervalo,
        @JsonProperty("previousWin") int vitoriaAnterior,
        @JsonProperty("followingWin") int vitoriaSeguinte
) {
}
