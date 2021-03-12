package br.dev.adilson.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.adilson.osworks.api.model.Comentario;
import br.dev.adilson.osworks.domain.exception.EntidadeNaoEncontradaException;
import br.dev.adilson.osworks.domain.exception.NegocioException;
import br.dev.adilson.osworks.domain.model.Cliente;
import br.dev.adilson.osworks.domain.model.OrdemServico;
import br.dev.adilson.osworks.domain.model.StatusOrdemServico;
import br.dev.adilson.osworks.domain.repository.ClienteRepository;
import br.dev.adilson.osworks.domain.repository.ComentarioRepository;
import br.dev.adilson.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);		
	}
	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
	}	
	
	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
	}
		
	private OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
	}
}
