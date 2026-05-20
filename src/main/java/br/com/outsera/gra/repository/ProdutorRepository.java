package br.com.outsera.gra.repository;

import br.com.outsera.gra.model.Produtor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutorRepository extends JpaRepository<Produtor, Long> {

    Optional<Produtor> findByNome(String nome);
}
