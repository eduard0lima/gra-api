package br.com.outsera.gra.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "filmes")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ano_lancamento", nullable = false)
    private int anoLancamento;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String estudios;

    @Column(nullable = false)
    private boolean vencedor;

    @ManyToMany
    @JoinTable(
            name = "filme_produtores",
            joinColumns = @JoinColumn(name = "filme_id"),
            inverseJoinColumns = @JoinColumn(name = "produtor_id")
    )
    private Set<Produtor> produtores = new LinkedHashSet<>();

    protected Filme() {
    }

    public Filme(int anoLancamento, String titulo, String estudios, boolean vencedor) {
        this.anoLancamento = anoLancamento;
        this.titulo = titulo;
        this.estudios = estudios;
        this.vencedor = vencedor;
    }

    public Long getId() {
        return id;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEstudios() {
        return estudios;
    }

    public boolean isVencedor() {
        return vencedor;
    }

    public Set<Produtor> getProdutores() {
        return produtores;
    }

    public void adicionarProdutor(Produtor produtor) {
        produtores.add(produtor);
    }
}
