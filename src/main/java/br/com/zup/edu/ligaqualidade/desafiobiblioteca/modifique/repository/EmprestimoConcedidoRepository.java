package br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.repository;

import br.com.zup.edu.ligaqualidade.desafiobiblioteca.EmprestimoConcedido;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosExemplar;

import java.time.LocalDate;
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
        return emprestimosConcedidos;
    }

    public Set<Integer> getExemplaresComEmprestivoAtivos(Set<Integer> idsExemplares) {

        return emprestimosConcedidos.stream().filter(it ->
            !it.getMomentoDevolucao().isPresent() && idsExemplares.contains(it.idExemplar)
        ).map(it -> it.idExemplar).collect(Collectors.toSet());
    }

    public Set<Integer> getPossuiEmprestimoExpirado(LocalDate dataSolicitacaoEmprestimo){
        return emprestimosConcedidos.stream().filter( it -> dataSolicitacaoEmprestimo.isAfter(it.dataPrevistaDevolucao))
                .map(it -> it.idExemplar).collect(Collectors.toSet());
    }
}
