package br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.repository;

import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosExemplar;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.TipoExemplar;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.TipoUsuario;

import java.util.Set;
import java.util.stream.Collectors;

public class ExemplarRepository {

    Set<DadosExemplar> exemplares;

    public ExemplarRepository(Set<DadosExemplar> exemplares) {
        this.exemplares = exemplares;
    }

    public Set<DadosExemplar> get(int idLivro, TipoExemplar tipoExemplar) {

        return exemplares.stream().filter(it -> it.tipo == tipoExemplar && it.idLivro == idLivro).collect(Collectors.toSet());
    }
}
