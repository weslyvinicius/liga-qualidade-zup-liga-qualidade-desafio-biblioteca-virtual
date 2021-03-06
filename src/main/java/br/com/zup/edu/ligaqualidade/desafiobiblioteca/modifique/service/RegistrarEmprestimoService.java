package br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.service;

import br.com.zup.edu.ligaqualidade.desafiobiblioteca.DadosEmprestimo;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.EmprestimoConcedido;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.repository.EmprestimoConcedidoRepository;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.repository.ExemplarRepository;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.repository.UsuarioRepository;

import java.time.LocalDate;
import java.util.Set;

public class RegistrarEmprestimoService {

    UsuarioRepository usuarioRepository;
    EmprestimoConcedidoRepository emprestimoConcedidoRepository;
    ExemplarDisponivelService exemplarDisponivelService;
    RestricoesEmprestimosService restricoesEmprestimosService;

    public RegistrarEmprestimoService(UsuarioRepository usuarioRepository,
                                      ExemplarRepository exemplarRepository,
                                      EmprestimoConcedidoRepository emprestimoConcedidoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.emprestimoConcedidoRepository = emprestimoConcedidoRepository;
        this.exemplarDisponivelService = new ExemplarDisponivelService(exemplarRepository, emprestimoConcedidoRepository);
        this.restricoesEmprestimosService = new RestricoesEmprestimosService(usuarioRepository, emprestimoConcedidoRepository);
    }

    public void registrar(Set<DadosEmprestimo> emprestimos, final LocalDate dataParaSerConsideradaNaExpiracao) {

        for (DadosEmprestimo emprestimo : emprestimos) {

            if(restricoesEmprestimosService.validaRestricoesEmprestimo(emprestimo, dataParaSerConsideradaNaExpiracao)) {

                Integer idExemplar = exemplarDisponivelService.getId(emprestimo.idLivro, emprestimo.tipoExemplar);

                EmprestimoConcedido emprestimoConcedido = new EmprestimoConcedido(emprestimo.idPedido, emprestimo.idUsuario,
                        idExemplar,
                        LocalDate.now().plusDays(emprestimo.tempo));

                emprestimoConcedidoRepository.regitrar(emprestimoConcedido);
            }
        }
    }
}
