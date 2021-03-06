package br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.repository;

import br.com.zup.edu.ligaqualidade.desafiobiblioteca.EmprestimoConcedido;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosExemplar;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EmprestimoConcedidoRepository {

    Set<EmprestimoConcedido> emprestimosConcedidos;

    public EmprestimoConcedidoRepository() {
        emprestimosConcedidos = new HashSet<>();
    }

    public void regitrar(EmprestimoConcedido emprestimoConcedido) {
        emprestimosConcedidos.add(emprestimoConcedido);
    }

    public void devolver(Integer idEmprestimo) {
        emprestimosConcedidos.stream().filter(it -> it.idEmprestimo == idEmprestimo).findFirst().get().registraDevolucao();
    }

    public Set<EmprestimoConcedido> get() {
        return emprestimosConcedidos.stream().filter(it-> it.getMomentoDevolucao().isEmpty()).collect(Collectors.toSet());
    }

    public Set<Integer> getExemplaresComEmprestivoAtivos(Set<DadosExemplar> exemplares) {

        Set<Integer> exemplaresIds = exemplares.stream().map(it1 -> it1.idExemplar).collect(Collectors.toSet());

        return emprestimosConcedidos.stream().filter(it -> {
            return !it.getMomentoDevolucao().isPresent() && exemplaresIds.contains(it.idExemplar);
        }).map(it -> it.idExemplar).collect(Collectors.toSet());
    }
}
