package br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.service;

import br.com.zup.edu.ligaqualidade.desafiobiblioteca.DadosEmprestimo;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.repository.EmprestimoConcedidoRepository;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.repository.UsuarioRepository;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosUsuario;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.TipoExemplar;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.TipoUsuario;

import java.time.LocalDate;
import java.util.Set;

public class RestricoesEmprestimosService {

    private final UsuarioRepository usuarioRepository;
    private final EmprestimoConcedidoRepository emprestimoConcedidoRepository;

    public RestricoesEmprestimosService(UsuarioRepository usuarioRepository, EmprestimoConcedidoRepository emprestimoConcedidoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.emprestimoConcedidoRepository = emprestimoConcedidoRepository;
    }

    public Boolean validaRestricoesEmprestimo(DadosEmprestimo emprestimo, final LocalDate dataParaSerConsideradaNaExpiracao){

        if (emprestimo.tempo > 60) {
            return false;
        }

        DadosUsuario dadosUsuario = usuarioRepository.get(emprestimo.idUsuario);
        if (TipoUsuario.PADRAO.equals(dadosUsuario.padrao) && TipoExemplar.RESTRITO.equals(emprestimo.tipoExemplar)) {
            return false;
        }

        final Set<Integer> possuiEmprestimoExpirado = emprestimoConcedidoRepository.getPossuiEmprestimoExpirado(dataParaSerConsideradaNaExpiracao);
        if( possuiEmprestimoExpirado.size() >= 1)
            return false;

        return true;

    }
}
