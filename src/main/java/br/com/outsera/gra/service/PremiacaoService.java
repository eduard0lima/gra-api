package br.com.outsera.gra.service;

import br.com.outsera.gra.dto.IntervaloPremiacao;
import br.com.outsera.gra.dto.RespostaIntervalosPremiacao;
import br.com.outsera.gra.repository.FilmeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class PremiacaoService {

    private static final Comparator<IntervaloPremiacao> ORDEM_PADRAO = Comparator
            .comparing(IntervaloPremiacao::produtor)
            .thenComparingInt(IntervaloPremiacao::vitoriaAnterior)
            .thenComparingInt(IntervaloPremiacao::vitoriaSeguinte);

    private final FilmeRepository filmeRepository;

    public PremiacaoService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    @Transactional(readOnly = true)
    public RespostaIntervalosPremiacao buscarIntervalosDePremiacao() {
        List<IntervaloPremiacao> intervalos = calcularIntervalos();

        if (intervalos.isEmpty()) {
            return new RespostaIntervalosPremiacao(List.of(), List.of());
        }

        int menorIntervalo = intervalos.stream()
                .mapToInt(IntervaloPremiacao::intervalo)
                .min()
                .orElseThrow();

        int maiorIntervalo = intervalos.stream()
                .mapToInt(IntervaloPremiacao::intervalo)
                .max()
                .orElseThrow();

        return new RespostaIntervalosPremiacao(
                filtrarPorIntervalo(intervalos, menorIntervalo),
                filtrarPorIntervalo(intervalos, maiorIntervalo)
        );
    }

    private List<IntervaloPremiacao> calcularIntervalos() {
        Map<String, TreeSet<Integer>> vitoriasPorProdutor = filmeRepository.buscarVitoriasPorProdutor()
                .stream()
                .collect(Collectors.groupingBy(
                        FilmeRepository.VitoriaProdutor::getProdutor,
                        Collectors.mapping(FilmeRepository.VitoriaProdutor::getAno, Collectors.toCollection(TreeSet::new))
                ));

        List<IntervaloPremiacao> intervalos = new ArrayList<>();

        vitoriasPorProdutor.forEach((produtor, anos) -> {
            if (anos.size() < 2) {
                return;
            }

            Integer anoAnterior = null;
            for (Integer anoAtual : anos) {
                if (anoAnterior != null) {
                    intervalos.add(new IntervaloPremiacao(produtor, anoAtual - anoAnterior, anoAnterior, anoAtual));
                }
                anoAnterior = anoAtual;
            }
        });

        intervalos.sort(ORDEM_PADRAO);
        return intervalos;
    }

    private List<IntervaloPremiacao> filtrarPorIntervalo(List<IntervaloPremiacao> intervalos, int intervalo) {
        return intervalos.stream()
                .filter(candidato -> candidato.intervalo() == intervalo)
                .sorted(ORDEM_PADRAO)
                .toList();
    }
}
