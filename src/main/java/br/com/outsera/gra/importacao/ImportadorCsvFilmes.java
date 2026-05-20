package br.com.outsera.gra.importacao;

import br.com.outsera.gra.model.Filme;
import br.com.outsera.gra.model.Produtor;
import br.com.outsera.gra.repository.FilmeRepository;
import br.com.outsera.gra.repository.ProdutorRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
public class ImportadorCsvFilmes implements ApplicationRunner {

    private final FilmeRepository filmeRepository;
    private final ProdutorRepository produtorRepository;

    public ImportadorCsvFilmes(FilmeRepository filmeRepository, ProdutorRepository produtorRepository) {
        this.filmeRepository = filmeRepository;
        this.produtorRepository = produtorRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        ClassPathResource arquivo = new ClassPathResource("Movielist.csv");

        try (Reader leitor = new InputStreamReader(arquivo.getInputStream(), StandardCharsets.UTF_8)) {
            CSVFormat formato = CSVFormat.DEFAULT.builder()
                    .setDelimiter(';')
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setTrim(true)
                    .get();

            for (CSVRecord linha : formato.parse(leitor)) {
                Filme filme = new Filme(
                        Integer.parseInt(linha.get("year")),
                        linha.get("title"),
                        linha.get("studios"),
                        "yes".equalsIgnoreCase(linha.get("winner"))
                );

                separarProdutores(linha.get("producers"))
                        .forEach(nome -> filme.adicionarProdutor(buscarOuCriarProdutor(nome)));

                filmeRepository.save(filme);
            }
        }
    }

    private Produtor buscarOuCriarProdutor(String nome) {
        return produtorRepository.findByNome(nome)
                .orElseGet(() -> produtorRepository.save(new Produtor(nome)));
    }

    private List<String> separarProdutores(String produtores) {
        return Arrays.stream(produtores.replaceAll("\\s*,\\s*and\\s+", ", ")
                        .replaceAll("\\s+and\\s+", ", ")
                        .split("\\s*,\\s*"))
                .map(String::trim)
                .filter(nome -> !nome.isBlank())
                .toList();
    }
}
