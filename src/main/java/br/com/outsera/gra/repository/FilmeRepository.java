package br.com.outsera.gra.repository;

import br.com.outsera.gra.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Long> {

    @Query("""
            select p.nome as produtor, f.anoLancamento as ano
            from Filme f
            join f.produtores p
            where f.vencedor = true
            """)
    List<VitoriaProdutor> buscarVitoriasPorProdutor();

    interface VitoriaProdutor {
        String getProdutor();

        int getAno();
    }
}
