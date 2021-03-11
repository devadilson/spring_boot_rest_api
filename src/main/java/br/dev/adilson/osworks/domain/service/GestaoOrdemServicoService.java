package br.dev.adilson.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.adilson.osworks.domain.exception.NegocioException;
import br.dev.adilson.osworks.domain.model.Cliente;
import br.dev.adilson.osworks.domain.model.OrdemServico;
import br.dev.adilson.osworks.domain.model.StatusOrdemServico;
import br.dev.adilson.osworks.domain.repository.ClienteRepository;
import br.dev.adilson.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getcliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		
		ordemServico.setcliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);		
	}
}
