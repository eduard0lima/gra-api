package br.com.outsera.gra.service;

import br.com.outsera.gra.dto.IntervaloPremiacao;
import br.com.outsera.gra.dto.RespostaIntervalosPremiacao;
import br.com.outsera.gra.repository.FilmeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PremiacaoService {

    private final FilmeRepository filmeRepository;

    public PremiacaoService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    @Transactional(readOnly = true)
    public RespostaIntervalosPremiacao buscarIntervalosDePremiacao() {
        List<IntervaloPremiacao> menores = new ArrayList<>();
        List<IntervaloPremiacao> maiores = new ArrayList<>();

        String produtorAtual = null;
        Integer anoAnterior = null;
        int menorIntervalo = Integer.MAX_VALUE;
        int maiorIntervalo = Integer.MIN_VALUE;

        for (FilmeRepository.VitoriaProdutor vitoria : filmeRepository.buscarVitoriasOrdenadasPorProdutor()) {
            if (!vitoria.getProdutor().equals(produtorAtual)) {
                produtorAtual = vitoria.getProdutor();
                anoAnterior = null;
            }

            int anoAtual = vitoria.getAno();

            if (anoAnterior != null && anoAtual != anoAnterior) {
                IntervaloPremiacao intervalo = new IntervaloPremiacao(
                        produtorAtual,
                        anoAtual - anoAnterior,
                        anoAnterior,
                        anoAtual
                );

                if (intervalo.intervalo() < menorIntervalo) {
                    menorIntervalo = intervalo.intervalo();
                    menores.clear();
                    menores.add(intervalo);
                } else if (intervalo.intervalo() == menorIntervalo) {
                    menores.add(intervalo);
                }

                if (intervalo.intervalo() > maiorIntervalo) {
                    maiorIntervalo = intervalo.intervalo();
                    maiores.clear();
                    maiores.add(intervalo);
                } else if (intervalo.intervalo() == maiorIntervalo) {
                    maiores.add(intervalo);
                }
            }

            anoAnterior = anoAtual;
        }

        if (menores.isEmpty()) {
            return new RespostaIntervalosPremiacao(List.of(), List.of());
        }

        return new RespostaIntervalosPremiacao(List.copyOf(menores), List.copyOf(maiores));
    }
}
